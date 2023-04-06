package com.xbot.telegramcompose.data

import android.util.Log
import com.xbot.tdlibx.core.TelegramException
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ProfilePhoto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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

    suspend fun load(limit: Int) = withContext(dispatcher) {
        try {
            //TODO: Load chat list first and then update it
            awaitAll(
                async {
                    telegramRepository.loadChats(limit)
                },
                async {
                    telegramRepository.newChatFlow
                        .onEach { chat ->
                            //Log.e("ChatsRepository", "Update new chat")
                            chats[chat.id] = chat
                            val positions = chat.positions.clone()
                            chat.positions = arrayOfNulls(0)
                            //TODO: Update position
                            setChatPositions(chat, positions)
                        }.collect()
                },
                async {
                    telegramRepository.chatLastMessageFlow
                        .onEach { updateChat ->
                            //Log.e("ChatsRepository", "Update last message")
                            val chat = chats[updateChat.chatId]
                            if (chat != null) {
                                chat.lastMessage = updateChat.lastMessage
                                //TODO: Update position
                                setChatPositions(chat, updateChat.positions)
                            }
                        }.collect()
                },
                async {
                    telegramRepository.chatPositionFlow
                        .onEach { updateChat ->
                            //Log.e("ChatsRepository", "Update chat position")
                            val chat = chats[updateChat.chatId]
                            if (chat != null) {
                                var i = 0
                                for (k in chat.positions.indices) {
                                    if (chat.positions[k].list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                                        break
                                    }
                                    i++
                                }
                                val newPositions = arrayOfNulls<TdApi.ChatPosition>(
                                    size = chat.positions.size + (if (updateChat.position.order == 0L) 0 else 1)
                                            - (if (i < chat.positions.size) 1 else 0)
                                )
                                var pos = 0
                                if (updateChat.position.order != 0L) {
                                    newPositions[pos++] = updateChat.position
                                }
                                for (j in chat.positions.indices) {
                                    if (i != j) {
                                        newPositions[pos++] = chat.positions[j]
                                    }
                                }
                                assert(pos == newPositions.size)
                                //TODO: Update position
                                setChatPositions(chat, newPositions)
                            }
                        }.collect()
                }
            )
        } catch (error: TelegramException) {
            when (error) {
                is TelegramException.Error -> {
                    Log.e("TelegramFlowError", "Code: ${error.code}, msg: ${error.message}")
                }
                else -> {
                    Log.e("TelegramFlowError", "Msg: ${error.message}")
                }
            }
        }
    }

    fun getData() = flow {
        //TODO: Concurrency exception
        while (true) {
            val chats = mainChatList.map { orderedChat ->
                val chat = chats[orderedChat.chatId]!!
                Chat(
                    id = chat.id,
                    title = chat.title.ifEmpty { "Deleted account" },
                    photo = chat.photo?.let {
                        ProfilePhoto(
                            thumbnail = chat.photo!!.minithumbnail!!.data,
                            file = chat.photo!!.small
                        )
                    },
                    isPinned = orderedChat.position.isPinned,
                    unreadCount = chat.unreadCount,
                    lastMessage = chat.lastMessage
                )
            }.toList()
            emit(chats)
            delay(1000L)
        }
    }

    suspend fun downloadableFile(file: TdApi.File): String =
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
                ""
            }
        } ?: file.local.path

    private fun setChatPositions(chat: TdApi.Chat, positions: Array<TdApi.ChatPosition?>) {
        //TODO: Concurrency exception
        for (position in chat.positions) {
            if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                val isRemoved = mainChatList.remove(OrderedChat(chat.id, position))
                assert(isRemoved)
            }
        }
        chat.positions = positions
        for (position in chat.positions) {
            if (position.list.constructor == TdApi.ChatListMain.CONSTRUCTOR) {
                val isAdded: Boolean = mainChatList.add(OrderedChat(chat.id, position))
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