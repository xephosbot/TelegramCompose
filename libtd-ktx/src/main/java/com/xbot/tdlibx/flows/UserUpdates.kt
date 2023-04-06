@file:Suppress("UNUSED")

package com.xbot.tdlibx.flows

import com.xbot.tdlibx.core.TelegramFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.td.libcore.telegram.TdApi

/**
 * emits [TdApi.UpdateUserStatus] if the user went online or offline.
 */
fun TelegramFlow.userStatusFlow(): Flow<TdApi.UpdateUserStatus> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.User] if some data of a user has changed. This update is guaranteed to come before the
 * user identifier is returned to the client.
 */
fun TelegramFlow.userFlow(): Flow<TdApi.User> = this.getUpdatesFlowOfType<TdApi.UpdateUser>()
    .mapNotNull { it.user }

/**
 * emits [TdApi.UpdateUserFullInfo] if some data from userFullInfo has been changed.
 */
fun TelegramFlow.userFullInfoFlow(): Flow<TdApi.UpdateUserFullInfo> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateUserPrivacySettingRules] if some privacy setting rules have been changed.
 */
fun TelegramFlow.userPrivacySettingRulesFlow(): Flow<TdApi.UpdateUserPrivacySettingRules> =
    this.getUpdatesFlowOfType()

/**
 * emits usersNearby [ChatNearby[]] if list of users nearby has changed. The update is sent only 60
 * seconds after a successful searchChatsNearby request.
 */
fun TelegramFlow.usersNearbyFlow(): Flow<Array<TdApi.ChatNearby>> = this.getUpdatesFlowOfType<TdApi.UpdateUsersNearby>()
    .mapNotNull { it.usersNearby }