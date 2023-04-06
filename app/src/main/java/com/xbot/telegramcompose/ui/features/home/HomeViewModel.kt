package com.xbot.telegramcompose.ui.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xbot.telegramcompose.data.ChatsRepository
import com.xbot.telegramcompose.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

@HiltViewModel
class HomeViewMode @Inject constructor(
    private val repository: ChatsRepository
) : ViewModel() {

    private val _chats: MutableStateFlow<List<Chat>> = MutableStateFlow(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            awaitAll(
                async { repository.load(LOADING_CHATS_COUNT) },
                async {
                    repository.getData().collect { chats ->
                        _chats.value = chats
                    }
                }
            )
        }
    }

    suspend fun getFilePath(file: TdApi.File): String =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.downloadableFile(file)
        }

    companion object {
        private const val LOADING_CHATS_COUNT = 100
    }
}