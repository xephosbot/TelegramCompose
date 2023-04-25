//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.flows

import com.xbot.tdlibx.core.TelegramFlow
import kotlin.Array
import kotlin.String
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.AttachmentMenuBot
import org.drinkless.tdlib.TdApi.AuthorizationState
import org.drinkless.tdlib.TdApi.ConnectionState
import org.drinkless.tdlib.TdApi.ReactionType
import org.drinkless.tdlib.TdApi.UpdateAutosaveSettings
import org.drinkless.tdlib.TdApi.UpdateForumTopicInfo
import org.drinkless.tdlib.TdApi.UpdateLanguagePackStrings
import org.drinkless.tdlib.TdApi.UpdateNewChosenInlineResult
import org.drinkless.tdlib.TdApi.UpdateNewCustomQuery
import org.drinkless.tdlib.TdApi.UpdateNewInlineQuery
import org.drinkless.tdlib.TdApi.UpdateNewPreCheckoutQuery
import org.drinkless.tdlib.TdApi.UpdateNewShippingQuery
import org.drinkless.tdlib.TdApi.UpdateOption
import org.drinkless.tdlib.TdApi.UpdateSelectedBackground
import org.drinkless.tdlib.TdApi.UpdateSuggestedActions
import org.drinkless.tdlib.TdApi.UpdateTermsOfService

/**
 * emits [AuthorizationState] if the user authorization state has changed.
 */
fun TelegramFlow.authorizationStateFlow(): Flow<AuthorizationState> =
    this.getUpdatesFlowOfType<TdApi.UpdateAuthorizationState>()
    .mapNotNull { it.authorizationState }

/**
 * emits [UpdateForumTopicInfo] if basic information about a topic in a forum chat was changed.
 */
fun TelegramFlow.forumTopicInfoFlow(): Flow<UpdateForumTopicInfo> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateOption] if an option changed its value.
 */
fun TelegramFlow.optionFlow(): Flow<UpdateOption> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateSelectedBackground] if the selected background has changed.
 */
fun TelegramFlow.selectedBackgroundFlow(): Flow<UpdateSelectedBackground> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateLanguagePackStrings] if some language pack strings have been updated.
 */
fun TelegramFlow.languagePackStringsFlow(): Flow<UpdateLanguagePackStrings> =
    this.getUpdatesFlowOfType()

/**
 * emits state [ConnectionState] if the connection state has changed. This update must be used only
 * to show a human-readable description of the connection state.
 */
fun TelegramFlow.connectionStateFlow(): Flow<ConnectionState> =
    this.getUpdatesFlowOfType<TdApi.UpdateConnectionState>()
    .mapNotNull { it.state }

/**
 * emits [UpdateTermsOfService] if new terms of service must be accepted by the user. If the terms
 * of service are declined, then the deleteAccount method must be called with the reason &quot;Decline
 * ToS update&quot;.
 */
fun TelegramFlow.termsOfServiceFlow(): Flow<UpdateTermsOfService> = this.getUpdatesFlowOfType()

/**
 * emits bots [AttachmentMenuBot[]] if the list of bots added to attachment menu has changed.
 */
fun TelegramFlow.attachmentMenuBotsFlow(): Flow<Array<AttachmentMenuBot>> =
    this.getUpdatesFlowOfType<TdApi.UpdateAttachmentMenuBots>()
    .mapNotNull { it.bots }

/**
 * emits emojis [String[]] if the list of active emoji reactions has changed.
 */
fun TelegramFlow.activeEmojiReactionsFlow(): Flow<Array<String>> =
    this.getUpdatesFlowOfType<TdApi.UpdateActiveEmojiReactions>()
    .mapNotNull { it.emojis }

/**
 * emits [ReactionType] if the type of default reaction has changed.
 */
fun TelegramFlow.defaultReactionTypeFlow(): Flow<ReactionType> =
    this.getUpdatesFlowOfType<TdApi.UpdateDefaultReactionType>()
    .mapNotNull { it.reactionType }

/**
 * emits emojis [String[]] if the list of supported dice emojis has changed.
 */
fun TelegramFlow.diceEmojisFlow(): Flow<Array<String>> =
    this.getUpdatesFlowOfType<TdApi.UpdateDiceEmojis>()
    .mapNotNull { it.emojis }

/**
 * emits [UpdateSuggestedActions] if the list of suggested to the user actions has changed.
 */
fun TelegramFlow.suggestedActionsFlow(): Flow<UpdateSuggestedActions> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateAutosaveSettings] if autosave settings for some type of chats were updated.
 */
fun TelegramFlow.autosaveSettingsFlow(): Flow<UpdateAutosaveSettings> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewInlineQuery] if a new incoming inline query; for bots only.
 */
fun TelegramFlow.newInlineQueryFlow(): Flow<UpdateNewInlineQuery> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewChosenInlineResult] if the user has chosen a result of an inline query; for bots
 * only.
 */
fun TelegramFlow.newChosenInlineResultFlow(): Flow<UpdateNewChosenInlineResult> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewShippingQuery] if a new incoming shipping query; for bots only. Only for invoices
 * with flexible price.
 */
fun TelegramFlow.newShippingQueryFlow(): Flow<UpdateNewShippingQuery> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewPreCheckoutQuery] if a new incoming pre-checkout query; for bots only. Contains
 * full information about a checkout.
 */
fun TelegramFlow.newPreCheckoutQueryFlow(): Flow<UpdateNewPreCheckoutQuery> =
    this.getUpdatesFlowOfType()

/**
 * emits event [String] if a new incoming event; for bots only.
 */
fun TelegramFlow.newCustomEventFlow(): Flow<String> =
    this.getUpdatesFlowOfType<TdApi.UpdateNewCustomEvent>()
    .mapNotNull { it.event }

/**
 * emits [UpdateNewCustomQuery] if a new incoming query; for bots only.
 */
fun TelegramFlow.newCustomQueryFlow(): Flow<UpdateNewCustomQuery> = this.getUpdatesFlowOfType()