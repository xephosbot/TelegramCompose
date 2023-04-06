@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Suspend function, which returns information about a basic group by its identifier. This is an
 * offline request if the current user is not a bot.
 *
 * @param basicGroupId Basic group identifier.
 *
 * @return [TdApi.BasicGroup] Represents a basic group of 0-200 users (must be upgraded to a supergroup to
 * accommodate more than 200 users).
 */
suspend fun TelegramFlow.getBasicGroup(basicGroupId: Long): TdApi.BasicGroup =
    this.sendFunctionAsync(TdApi.GetBasicGroup(basicGroupId))

/**
 * Suspend function, which returns full information about a basic group by its identifier.
 *
 * @param basicGroupId Basic group identifier.
 *
 * @return [TdApi.BasicGroupFullInfo] Contains full information about a basic group.
 */
suspend fun TelegramFlow.getBasicGroupFullInfo(basicGroupId: Long): TdApi.BasicGroupFullInfo =
    this.sendFunctionAsync(TdApi.GetBasicGroupFullInfo(basicGroupId))