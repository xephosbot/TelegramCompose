package com.xbot.telegramcompose.data

import android.util.Log
import com.xbot.tdlibx.core.TelegramException
import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.downloadFile
import com.xbot.tdlibx.coroutines.getChatHistory
import com.xbot.tdlibx.coroutines.loadChats
import com.xbot.tdlibx.extensions.ChatKtx
import com.xbot.tdlibx.flows.chatDefaultDisableNotificationFlow
import com.xbot.tdlibx.flows.chatDraftMessageFlow
import com.xbot.tdlibx.flows.chatFoldersFlow
import com.xbot.tdlibx.flows.chatHasScheduledMessagesFlow
import com.xbot.tdlibx.flows.chatIsBlockedFlow
import com.xbot.tdlibx.flows.chatIsMarkedAsUnreadFlow
import com.xbot.tdlibx.flows.chatLastMessageFlow
import com.xbot.tdlibx.flows.chatNotificationSettingsFlow
import com.xbot.tdlibx.flows.chatPermissionsFlow
import com.xbot.tdlibx.flows.chatPhotoFlow
import com.xbot.tdlibx.flows.chatPositionFlow
import com.xbot.tdlibx.flows.chatReadInboxFlow
import com.xbot.tdlibx.flows.chatReadOutboxFlow
import com.xbot.tdlibx.flows.chatReplyMarkupFlow
import com.xbot.tdlibx.flows.chatTitleFlow
import com.xbot.tdlibx.flows.chatUnreadMentionCountFlow
import com.xbot.tdlibx.flows.newChatFlow
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ChatFilter
import com.xbot.telegramcompose.model.Message
import com.xbot.telegramcompose.model.MessageContent
import com.xbot.telegramcompose.model.ProfilePhoto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.drinkless.tdlib.TdApi
import java.util.NavigableSet
import java.util.TreeSet
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatsRepository @Inject constructor(
    override val api: TelegramFlow,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChatKtx {

    private val chats: ConcurrentHashMap<Long, TdApi.Chat> = ConcurrentHashMap()
    private val mainChatList: NavigableSet<OrderedChat> = TreeSet()

    var selectedChatList: TdApi.ChatList = TdApi.ChatListMain()

    private val mainChatListMutex = Mutex()

    val chatFolderFlow: Flow<List<ChatFilter>> = api.chatFoldersFlow()
        .map { updateChatFilters ->
            updateChatFilters.chatFolders.map { chatFilter ->
                ChatFilter(
                    id = chatFilter.id,
                    title = chatFilter.title
                )
            }
        }

    val chatsFlow: Flow<List<Chat>> = merge(
        api.newChatFlow()
            .mapNotNull { newChat ->
                chats.put(newChat.id, newChat)?.let { existingChat ->
                    existingChat.positions = emptyArray()
                    setChatPositions(existingChat, newChat.positions)
                }
            },
        api.chatTitleFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.title = updateChat.title
                }
            },
        api.chatPhotoFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.photo = updateChat.photo
                }
            },
        api.chatLastMessageFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.lastMessage = updateChat.lastMessage
                    setChatPositions(existingChat, updateChat.positions)
                }
            },
        api.chatPositionFlow()
            .filter { it.position.list.constructor == selectedChatList.constructor }
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    val index = existingChat.positions.indexOfFirstOrLast {
                        it.list.constructor == TdApi.ChatListMain.CONSTRUCTOR
                    }
                    val size = existingChat.positions.size
                        .plus((updateChat.position.order != 0L).toInt())
                        .minus((index < existingChat.positions.size).toInt())
                    val newPositions = arrayOfNulls<TdApi.ChatPosition>(size)
                    var pos = 0
                    if (updateChat.position.order != 0L) {
                        newPositions[pos++] = updateChat.position
                    }
                    existingChat.positions.forEachIndexed { i, chatPosition ->
                        if (index != i) newPositions[pos++] = chatPosition
                    }
                    setChatPositions(existingChat, newPositions)
                }
            },
        api.chatReadInboxFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.lastReadInboxMessageId = updateChat.lastReadInboxMessageId
                    existingChat.unreadCount = updateChat.unreadCount
                }
            },
        api.chatReadOutboxFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.lastReadOutboxMessageId = updateChat.lastReadOutboxMessageId
                }
            },
        api.chatUnreadMentionCountFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.unreadMentionCount = updateChat.unreadMentionCount
                }
            },
        api.chatReplyMarkupFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.replyMarkupMessageId = updateChat.replyMarkupMessageId
                }
            },
        api.chatDraftMessageFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.draftMessage = updateChat.draftMessage
                    setChatPositions(existingChat, updateChat.positions)
                }
            },
        api.chatPermissionsFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.permissions = updateChat.permissions
                }
            },
        api.chatNotificationSettingsFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.notificationSettings = updateChat.notificationSettings
                }
            },
        api.chatDefaultDisableNotificationFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.defaultDisableNotification = updateChat.defaultDisableNotification
                }
            },
        api.chatIsMarkedAsUnreadFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.isMarkedAsUnread = updateChat.isMarkedAsUnread
                }
            },
        api.chatIsBlockedFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.isBlocked = updateChat.isBlocked
                }
            },
        api.chatHasScheduledMessagesFlow()
            .mapNotNull { updateChat ->
                chats[updateChat.chatId]?.let { existingChat ->
                    existingChat.hasScheduledMessages = updateChat.hasScheduledMessages
                }
            }
    ).map {
        mainChatListMutex.withLock {
            mainChatList.mapNotNull { orderedChat ->
                chats[orderedChat.chatId]?.let { chat ->
                    Chat(
                        id = chat.id,
                        title = chat.title.ifEmpty { "Deleted account" },
                        photo = chat.photo?.let {
                            ProfilePhoto(
                                thumbnail = it.minithumbnail!!.data,
                                file = it.small
                            )
                        },
                        isPinned = orderedChat.position.isPinned,
                        isMuted = chat.notificationSettings.useDefaultMuteFor,
                        unreadCount = chat.unreadCount,
                        lastMessage = chat.lastMessage?.let { lastMessage ->
                            val lastMessages = if (false) {
                                api.getChatHistory(chat.id, 0, 0, 10, false).messages
                                    .filter { it.mediaAlbumId == lastMessage.mediaAlbumId }
                                    .asReversed()
                            } else null

                            Message(
                                id = lastMessage.id,
                                date = lastMessage.date.toLong(),
                                content = lastMessages?.map { message ->
                                    MessageContent.create(message.content)
                                } ?: listOf(MessageContent.create(lastMessage.content))
                            )
                        }
                    )
                }
            }
        }
    }.flowOn(dispatcher)

    suspend fun loadChatList(chatList: TdApi.ChatList, limit: Int) {
        tailrec suspend fun loadChats() {
            if (limit <= mainChatList.size) return
            try {
                api.loadChats(chatList, limit - mainChatList.size)
            } catch (exception: TelegramException) {
                when (exception) {
                    is TelegramException.Error -> {
                        if (exception.code == 404) return
                        Log.e(TAG, "Code: ${exception.code}, Msg: ${exception.message}")
                    }
                    else -> {
                        Log.e(TAG, exception.message ?: "Unknown")
                    }
                }
            }
            loadChats()
        }
        loadChats()
    }

    suspend fun downloadableFile(file: TdApi.File): String? =
        file.takeIf {
            !it.local.isDownloadingCompleted
        }?.id?.let { fileId ->
            try {
                api.downloadFile(fileId, 1, 0, 0, true).local.path
            } catch (exception: TelegramException) {
                when (exception) {
                    is TelegramException.Error -> {
                        Log.e(TAG, "Code: ${exception.code}, msg: ${exception.message}")
                    }
                    else -> {
                        Log.e(TAG, "Msg: ${exception.message}")
                    }
                }
                null
            }
        } ?: file.local.path

    private suspend fun setChatPositions(chat: TdApi.Chat, positions: Array<TdApi.ChatPosition?>) {
        chat.positions.asSequence()
            .filter { it.list.constructor == TdApi.ChatListMain.CONSTRUCTOR }
            .forEach { position ->
                mainChatListMutex.withLock { mainChatList.remove(OrderedChat(chat.id, position)) }
            }
        chat.positions = positions
        chat.positions.asSequence()
            .filter { it.list.constructor == TdApi.ChatListMain.CONSTRUCTOR }
            .forEach { position ->
                mainChatListMutex.withLock { mainChatList.add(OrderedChat(chat.id, position)) }
            }
    }

    private companion object {
        const val TAG = "CHATS_REPOSITORY"
    }
}
