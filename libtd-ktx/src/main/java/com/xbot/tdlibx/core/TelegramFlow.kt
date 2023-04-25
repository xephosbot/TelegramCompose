package com.xbot.tdlibx.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Main class to interact with Telegram API client
 *
 * @param defaultDispatcher main dispatcher which user for suspend functions
 */
class TelegramFlow(
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    /**
     * Telegram [Client] instance.
     */
    var client: Client? = null

    /**
     * Telegram flow main update events flow
     */
    lateinit var updateEventsFlow: Flow<TdApi.Object>

    /**
     * Telegram flow [CoroutineScope] used for suspend functions.
     */
    private val flowScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Attach instance to the existing native Telegram client or create one
     * @param existingClient set an existing client to attach, null by default
     */
    fun attachClient(existingClient: Client? = null) {
        if (client != null) return

        updateEventsFlow = callbackFlow {
            val resultHandler = Client.ResultHandler { trySend(it) }

            client = existingClient
                ?.also { it.setUpdatesHandler(resultHandler) }
                ?: Client.create(
                    resultHandler,
                    null,
                    null
                )

            awaitClose {
                client?.setUpdatesHandler(null)
            }
        }.shareIn(
            scope = flowScope,
            started = SharingStarted.Lazily,
            replay = 1000
        )
    }

    /**
     * Return data flow from Telegram API of the given type [T]
     */
    inline fun <reified T : TdApi.Object> getUpdatesFlowOfType() =
        updateEventsFlow.filterIsInstance<T>()

    /**
     * Sends a request to the TDLib and expect a result.
     *
     * @param function [TdApi.Function] representing a TDLib interface function-class.
     * @param ExpectedResult result type expecting from given [function].
     * @throws TelegramException.Error if TdApi request returns an exception
     * @throws TelegramException.UnexpectedResult if TdApi request returns an unexpected result
     * @throws TelegramException.ClientNotAttached if TdApi client has not attached yet
     */
    suspend inline fun <reified ExpectedResult : TdApi.Object> sendFunctionAsync(function: TdApi.Function): ExpectedResult =
        withContext(defaultDispatcher) {
            suspendCancellableCoroutine { continuation ->
                val resultHandler: (TdApi.Object) -> Unit = { result ->
                    when (result) {
                        is ExpectedResult -> continuation.resume(result)
                        is TdApi.Error -> continuation.resumeWithException(
                            TelegramException.Error(result.message, result.code)
                        )
                        else -> continuation.resumeWithException(
                            TelegramException.UnexpectedResult(result)
                        )
                    }
                }
                client?.send(function, resultHandler) { throwable ->
                    continuation.resumeWithException(
                        TelegramException.Error(throwable?.message ?: "unknown")
                    )
                } ?: throw TelegramException.ClientNotAttached
            }
        }

    /**
     * Sends a request to the TDLib and expect [TdApi.Ok]
     *
     * @param function [TdApi.Function] representing a TDLib interface function-class.
     * @throws TelegramException.Error if TdApi request returns an exception
     * @throws TelegramException.UnexpectedResult if TdApi request returns an unexpected result
     * @throws TelegramException.ClientNotAttached if TdApi client has not attached yet
     */
    suspend fun sendFunctionLaunch(function: TdApi.Function) {
        sendFunctionAsync<TdApi.Ok>(function)
    }
}