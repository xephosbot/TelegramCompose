package com.xbot.telegramcompose.data

import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.checkAuthenticationCode
import com.xbot.tdlibx.coroutines.checkAuthenticationPassword
import com.xbot.tdlibx.coroutines.checkDatabaseEncryptionKey
import com.xbot.tdlibx.coroutines.downloadFile
import com.xbot.tdlibx.coroutines.loadChats
import com.xbot.tdlibx.coroutines.setAuthenticationPhoneNumber
import com.xbot.tdlibx.coroutines.setTdlibParameters
import com.xbot.tdlibx.extensions.ChatKtx
import com.xbot.tdlibx.extensions.UserKtx
import com.xbot.tdlibx.flows.authorizationStateFlow
import com.xbot.tdlibx.flows.chatLastMessageFlow
import com.xbot.tdlibx.flows.chatPositionFlow
import com.xbot.tdlibx.flows.newChatFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TelegramRepository @Inject constructor(
    private val parameters: TdApi.TdlibParameters
) : UserKtx, ChatKtx {

    override val api: TelegramFlow = TelegramFlow()

    init {
        api.attachClient()
    }

    val authStateFlow = api.authorizationStateFlow()
        .onEach {
            checkRequiredParams(it)
        }
        .map {
            when (it) {
                is TdApi.AuthorizationStateReady -> AuthState.LoggedIn
                is TdApi.AuthorizationStateWaitCode -> AuthState.EnterCode
                is TdApi.AuthorizationStateWaitPassword -> AuthState.EnterPassword(passwordHint = it.passwordHint)
                is TdApi.AuthorizationStateWaitPhoneNumber -> AuthState.EnterPhone
                else -> AuthState.Unknown
            }
        }

    val newChatFlow = api.newChatFlow()

    val chatLastMessageFlow = api.chatLastMessageFlow()

    val chatPositionFlow = api.chatPositionFlow()
        .mapNotNull {
            when (it.position.list.constructor) {
                TdApi.ChatListMain.CONSTRUCTOR -> it
                else -> null
            }
        }

    private suspend fun checkRequiredParams(state: TdApi.AuthorizationState?) {
        when (state) {
            is TdApi.AuthorizationStateWaitTdlibParameters ->
                api.setTdlibParameters(parameters)
            is TdApi.AuthorizationStateWaitEncryptionKey ->
                api.checkDatabaseEncryptionKey(null)
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

    suspend fun loadChats(limit: Int) {
        api.loadChats(TdApi.ChatListMain(), limit)
    }

    suspend fun downloadFile(fileId: Int): TdApi.File {
        return api.downloadFile(fileId, 1, 0, 0, true)
    }

    fun close() {
        api.client?.send(TdApi.Close()) {

        }
    }
}