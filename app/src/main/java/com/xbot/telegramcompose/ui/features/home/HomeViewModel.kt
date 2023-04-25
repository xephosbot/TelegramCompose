package com.xbot.telegramcompose.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xbot.telegramcompose.data.ChatsRepository
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ChatFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.drinkless.tdlib.TdApi
import javax.inject.Inject

@HiltViewModel
class HomeViewMode @Inject constructor(
    private val repository: ChatsRepository
) : ViewModel() {

    val chats: StateFlow<List<Chat>> = repository.chatsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val chatFilters: StateFlow<List<ChatFilter>> = repository.chatFolderFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _selectedChatFilter: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedChatFilter = _selectedChatFilter.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch { repository.loadChatList(
                chatList = when(_selectedChatFilter.value) {
                    0 -> TdApi.ChatListMain()
                    else -> TdApi.ChatListFolder(_selectedChatFilter.value)
                },
                limit = LOADING_CHATS_COUNT
            ) }
        }
    }

    fun setSelectedFilter(id: Int) {
        _selectedChatFilter.value = id
    }

    suspend fun getFilePath(file: TdApi.File): String? =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.downloadableFile(file)
        }

    private companion object {
        const val LOADING_CHATS_COUNT = 100
    }
}
