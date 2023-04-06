package com.xbot.tdlibx.extensions

import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.createBasicGroupChat
import com.xbot.tdlibx.coroutines.getBasicGroup
import com.xbot.tdlibx.coroutines.getBasicGroupFullInfo
import org.drinkless.td.libcore.telegram.TdApi

@Suppress("UNUSED")
interface BasicGroupKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which returns an existing chat corresponding to a known basic group.
     *
     * @param force If true, the chat will be created without network request. In this case all
     * information about the chat except its type, title and photo can be incorrect.
     *
     * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
     */
    suspend fun TdApi.BasicGroup.createChat(force: Boolean) = api.createBasicGroupChat(this.id, force)

    /**
     * Suspend function, which returns information about a basic group by its identifier. This is an
     * offline request if the current user is not a bot.
     *
     *
     * @return [TdApi.BasicGroup] Represents a basic group of 0-200 users (must be upgraded to a
     * supergroup to accommodate more than 200 users).
     */
    suspend fun TdApi.BasicGroup.get() = api.getBasicGroup(this.id)

    /**
     * Suspend function, which returns full information about a basic group by its identifier.
     *
     *
     * @return [TdApi.BasicGroupFullInfo] Contains full information about a basic group.
     */
    suspend fun TdApi.BasicGroup.getFullInfo() = api.getBasicGroupFullInfo(this.id)
}