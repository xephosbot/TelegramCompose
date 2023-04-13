package com.xbot.telegramcompose.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xbot.telegramcompose.data.ChatsRepository
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ChatFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.drinkless.td.libcore.telegram.TdApi
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

    val chatFilters: StateFlow<List<ChatFilter>> = repository.chatFiltersFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.load(LOADING_CHATS_COUNT)
        }
    }

    suspend fun getFilePath(file: TdApi.File): String? =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.downloadableFile(file)
        }

    private companion object {
        const val LOADING_CHATS_COUNT = 100
    }
}