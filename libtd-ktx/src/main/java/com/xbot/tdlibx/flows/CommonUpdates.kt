@file:Suppress("UNUSED")

package com.xbot.tdlibx.flows

import com.xbot.tdlibx.core.TelegramFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.td.libcore.telegram.TdApi

/**
 * emits [TdApi.AuthorizationState] if the user authorization state has changed.
 */
fun TelegramFlow.authorizationStateFlow(): Flow<TdApi.AuthorizationState> = this.getUpdatesFlowOfType<TdApi.UpdateAuthorizationState>()
    .mapNotNull { it.authorizationState }

/**
 * emits [TdApi.UpdateOption] if an option changed its value.
 */
fun TelegramFlow.optionFlow(): Flow<TdApi.UpdateOption> = this.getUpdatesFlowOfType()

/**
 * emits animationIds [Int[]] if the list of saved animations was updated.
 */
fun TelegramFlow.savedAnimationsFlow(): Flow<IntArray> = this.getUpdatesFlowOfType<TdApi.UpdateSavedAnimations>()
    .mapNotNull { it.animationIds }

/**
 * emits [TdApi.UpdateSelectedBackground] if the selected background has changed.
 */
fun TelegramFlow.selectedBackgroundFlow(): Flow<TdApi.UpdateSelectedBackground> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateLanguagePackStrings] if some language pack strings have been updated.
 */
fun TelegramFlow.languagePackStringsFlow(): Flow<TdApi.UpdateLanguagePackStrings> =
    this.getUpdatesFlowOfType()

/**
 * emits state [TdApi.ConnectionState] if the connection state has changed.
 */
fun TelegramFlow.connectionStateFlow(): Flow<TdApi.ConnectionState> = this.getUpdatesFlowOfType<TdApi.UpdateConnectionState>()
    .mapNotNull { it.state }

/**
 * emits [TdApi.UpdateTermsOfService] if new terms of service must be accepted by the user. If the terms
 * of service are declined, then the deleteAccount method should be called with the reason
 * &quot;Decline ToS update&quot;.
 */
fun TelegramFlow.termsOfServiceFlow(): Flow<TdApi.UpdateTermsOfService> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateNewInlineQuery] if a new incoming inline query; for bots only.
 */
fun TelegramFlow.newInlineQueryFlow(): Flow<TdApi.UpdateNewInlineQuery> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateNewChosenInlineResult] if the user has chosen a result of an inline query; for bots
 * only.
 */
fun TelegramFlow.newChosenInlineResultFlow(): Flow<TdApi.UpdateNewChosenInlineResult> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateNewShippingQuery] if a new incoming shipping query; for bots only. Only for invoices
 * with flexible price.
 */
fun TelegramFlow.newShippingQueryFlow(): Flow<TdApi.UpdateNewShippingQuery> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateNewPreCheckoutQuery] if a new incoming pre-checkout query; for bots only. Contains
 * full information about a checkout.
 */
fun TelegramFlow.newPreCheckoutQueryFlow(): Flow<TdApi.UpdateNewPreCheckoutQuery> =
    this.getUpdatesFlowOfType()

/**
 * emits event [String] if a new incoming event; for bots only.
 */
fun TelegramFlow.newCustomEventFlow(): Flow<String> = this.getUpdatesFlowOfType<TdApi.UpdateNewCustomEvent>()
    .mapNotNull { it.event }

/**
 * emits [TdApi.UpdateNewCustomQuery] if a new incoming query; for bots only.
 */
fun TelegramFlow.newCustomQueryFlow(): Flow<TdApi.UpdateNewCustomQuery> = this.getUpdatesFlowOfType()
