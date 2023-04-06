package com.xbot.telegramcompose.ui.features.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xbot.telegramcompose.data.AuthState
import com.xbot.telegramcompose.data.TelegramRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: TelegramRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState(AuthState.Unknown))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val scope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        Log.e("LoginViewModel", throwable.message ?: "Unexpected exception")
    }

    init {
        viewModelScope.launch {
            repository.authStateFlow
                .collect { state ->
                    Log.e("STATUS_TAG_VIEWMODEL", state.javaClass.simpleName.toString())
                    _uiState.value = LoginUiState(authState = state)
                }
        }
    }

    fun sendPhone(phone: String) = scope.launch(Dispatchers.IO) {
        _uiState.value = uiState.value.copy(loading = true)
        repository.sendPhone(phone)
    }

    fun sendCode(code: String) = scope.launch(Dispatchers.IO) {
        _uiState.value = uiState.value.copy(loading = true)
        repository.sendCode(code)
    }

    fun sendPassword(password: String) = scope.launch(Dispatchers.IO) {
        _uiState.value = uiState.value.copy(loading = true)
        repository.sendPassword(password)
    }
}

data class LoginUiState(
    val authState: AuthState,
    val loading: Boolean = false
)