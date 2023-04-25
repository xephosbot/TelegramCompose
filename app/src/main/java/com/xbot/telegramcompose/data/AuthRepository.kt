package com.xbot.telegramcompose.data

import android.content.Context
import android.os.Build
import androidx.compose.ui.text.intl.Locale
import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.checkAuthenticationCode
import com.xbot.tdlibx.coroutines.checkAuthenticationPassword
import com.xbot.tdlibx.coroutines.setAuthenticationPhoneNumber
import com.xbot.tdlibx.coroutines.setTdlibParameters
import com.xbot.tdlibx.flows.authorizationStateFlow
import com.xbot.telegramcompose.BuildConfig
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.drinkless.tdlib.TdApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: TelegramFlow,
    private val context: Context
) {
    val authStateFlow = api.authorizationStateFlow()
        .onEach {
            checkRequiredParams(it)
        }
        .map {
            when (it) {
                is TdApi.AuthorizationStateReady -> AuthState.LoggedIn
                is TdApi.AuthorizationStateWaitCode -> AuthState.EnterCode
                is TdApi.AuthorizationStateWaitPassword -> AuthState.EnterPassword(it.passwordHint)
                is TdApi.AuthorizationStateWaitPhoneNumber -> AuthState.EnterPhone
                else -> AuthState.Unknown
            }
        }

    private suspend fun checkRequiredParams(state: TdApi.AuthorizationState?) {
        if (state is TdApi.AuthorizationStateWaitTdlibParameters) {
            api.setTdlibParameters(
                useTestDc = false,
                databaseDirectory = context.filesDir.absolutePath,
                filesDirectory = context.filesDir.absolutePath,
                databaseEncryptionKey = null,
                useFileDatabase = true,
                useChatInfoDatabase = true,
                useMessageDatabase = true,
                useSecretChats = false,
                apiId = BuildConfig.API_ID,
                apiHash = BuildConfig.API_HASH,
                systemLanguageCode = Locale.current.language,
                deviceModel = Build.MODEL,
                systemVersion = Build.VERSION.RELEASE,
                applicationVersion = BuildConfig.VERSION_NAME,
                enableStorageOptimizer = true,
                ignoreFileNames = true
            )
        }
    }

    suspend fun sendPhone(phone: String) {
        api.setAuthenticationPhoneNumber(phone, null)
    }

    suspend fun sendCode(code: String) {
        api.checkAuthenticationCode(code)
    }

    suspend fun sendPassword(password: String) {
        api.checkAuthenticationPassword(password)
    }
}