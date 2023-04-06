@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Suspend function, which deletes the account of the current user, deleting all information
 * associated with the user from the server. The phone number of the account can be used to create a
 * new account. Can be called before authorization when the current authorization state is
 * authorizationStateWaitPassword.
 *
 * @param reason The reason why the account was deleted; optional.
 */
suspend fun TelegramFlow.deleteAccount(reason: String?) =
    this.sendFunctionLaunch(TdApi.DeleteAccount(reason))

/**
 * Suspend function, which returns the period of inactivity after which the account of the current
 * user will automatically be deleted.
 *
 * @return [TdApi.AccountTtl] Contains information about the period of inactivity after which the current
 * user's account will automatically be deleted.
 */
suspend fun TelegramFlow.getAccountTtl(): TdApi.AccountTtl =
    this.sendFunctionAsync(TdApi.GetAccountTtl())

/**
 * Suspend function, which changes the period of inactivity after which the account of the current
 * user will automatically be deleted.
 *
 * @param ttl New account TTL.
 */
suspend fun TelegramFlow.setAccountTtl(ttl: TdApi.AccountTtl?) =
    this.sendFunctionLaunch(TdApi.SetAccountTtl(ttl))