package com.xbot.telegramcompose.data

import android.util.Log
import com.xbot.tdlibx.core.TelegramException
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ChatFilter
import com.xbot.telegramcompose.model.ProfilePhoto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.drinkless.td.libcore.telegram.TdApi
import java.util.NavigableSet
import java.util.TreeSet
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatsRepository @Inject constructor(
    private val telegramRepository: TelegramRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
)  {

    private val chats: ConcurrentHashMap<Long, TdApi.Chat> = ConcurrentHashMap()
    private val mainChatList: NavigableSet<OrderedChat> = TreeSet()

    private val mainChatListMutex = Mutex()
    private val chatsChangedFlow = MutableSharedFlow<Unit>(replay = 1)

    val chatsFlow = chatsChangedFlow.asSharedFlow()
        .map {
            mainChatListMutex.withLock {
                mainChatList.map { orderedChat ->
                    val chat = chats.getValue(orderedChat.chatId)
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
                        unreadCount = chat.unreadCount,
                        lastMessage = chat.lastMessage
                    )
                }
            }
        }

    val chatFiltersFlow = telegramRepository.chatFiltersFlow
        .map { updateChatFilters ->
            updateChatFilters.chatFilters.map { chatFilter ->
                ChatFilter(
                    id = chatFilter.id,
                    title = chatFilter.title
                )
            }
        }

    suspend fun load(limit: Int) = withContext(dispatcher) {
        awaitAll(
            async {
                telegramRepository.loadChats(limit)
            },
            async {
                telegramRepository.newChatFlow
                    .onEach { newChat ->
                        val existingChat = chats.put(newChat.id, newChat)
                        if (existingChat != null) {
                            val positions = existingChat.positions.clone()
                            existingChat.positions = emptyArray()
                            setChatPositions(existingChat, positions)
                            chatsChangedFlow.emit(Unit)
                        }
                    }.collect()
            },
            async {
                telegramRepository.chatLastMessageFlow
                    .onEach { updateChat ->
                        val existingChat = chats[updateChat.chatId]
                        if (existingChat != null) {
                            existingChat.lastMessage = updateChat.lastMessage
                            setChatPositions(existingChat, updateChat.positions)
                            chatsChangedFlow.emit(Unit)
                        }
                    }.collect()
            },
            async {
                telegramRepository.chatPositionFlow
                    .onEach { updateChat ->
                        val existingChat = chats[updateChat.chatId]
                        if (existingChat != null) {
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
                            for ((i, chatPosition) in existingChat.positions.withIndex()) {
                                if (index != i) newPositions[pos++] = chatPosition
                            }
                            assert(pos == newPositions.size)
                            setChatPositions(existingChat, newPositions)
                            chatsChangedFlow.emit(Unit)
                        }
                    }.collect()
            },
            async {
                telegramRepository.chatReadInboxFlow
                    .onEach { updateChat ->
                        val existingChat = chats[updateChat.chatId]
                        if (existingChat != null) {
                            existingChat.lastReadInboxMessageId = updateChat.lastReadInboxMessageId
                            existingChat.unreadCount = updateChat.unreadCount
                            chatsChangedFlow.emit(Unit)
                        }
                    }.collect()
            }
        )
    }

    suspend fun downloadableFile(file: TdApi.File): String? =
        file.takeIf {
            !it.local.isDownloadingCompleted
        }?.id?.let { fileId ->
            try {
                telegramRepository.downloadFile(fileId).local.path
            } catch (error: TelegramException) {
                when (error) {
                    is TelegramException.Error -> {
                        Log.e("TelegramFlowError", "Code: ${error.code}, msg: ${error.message}")
                    }
                    else -> {
                        Log.e("TelegramFlowError", "Msg: ${error.message}")
                    }
                }
                null
            }
        } ?: file.local.path

    private suspend fun setChatPositions(chat: TdApi.Chat, positions: Array<TdApi.ChatPosition?>) {
        for (position in chat.positions) {
            if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                val isRemoved = mainChatListMutex.withLock {
                    mainChatList.remove(OrderedChat(chat.id, position))
                }
                assert(isRemoved)
            }
        }
        chat.positions = positions
        for (position in chat.positions) {
            if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                val isAdded = mainChatListMutex.withLock {
                    mainChatList.add(OrderedChat(chat.id, position))
                }
                assert(isAdded)
            }
        }
    }

    data class OrderedChat (
        val chatId: Long,
        val position: TdApi.ChatPosition
    ) : Comparable<OrderedChat> {

        override fun compareTo(other: OrderedChat): Int {
            if (position.order != other.position.order) {
                return if (other.position.order < position.order) -1 else 1
            }
            return if (chatId != other.chatId) {
                if (other.chatId < chatId) -1 else 1
            } else 0
        }

        override fun equals(other: Any?): Boolean {
            val o = other as OrderedChat
            return this.chatId == o.chatId && this.position.order == o.position.order
        }

        override fun hashCode(): Int {
            var result = chatId.hashCode()
            result = 31 * result + position.hashCode()
            return result
        }
    }
}

fun Boolean.toInt() = if (this) 1 else 0

inline fun <T> Array<out T>.indexOfFirstOrLast(predicate: (T) -> Boolean): Int {
    var i = 0
    for (index in indices) {
        if (predicate(this[index])) break
        i++
    }
    return i
}