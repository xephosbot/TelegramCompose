//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.AttachmentMenuBot
import org.drinkless.tdlib.TdApi.AuthorizationState
import org.drinkless.tdlib.TdApi.BankCardInfo
import org.drinkless.tdlib.TdApi.BotCommand
import org.drinkless.tdlib.TdApi.BotCommandScope
import org.drinkless.tdlib.TdApi.BotCommands
import org.drinkless.tdlib.TdApi.BotMenuButton
import org.drinkless.tdlib.TdApi.CanTransferOwnershipResult
import org.drinkless.tdlib.TdApi.ChatAdministratorRights
import org.drinkless.tdlib.TdApi.Chats
import org.drinkless.tdlib.TdApi.ConnectedWebsites
import org.drinkless.tdlib.TdApi.CustomRequestResult
import org.drinkless.tdlib.TdApi.DeepLinkInfo
import org.drinkless.tdlib.TdApi.DeviceToken
import org.drinkless.tdlib.TdApi.Error
import org.drinkless.tdlib.TdApi.FileType
import org.drinkless.tdlib.TdApi.FormattedText
import org.drinkless.tdlib.TdApi.Hashtags
import org.drinkless.tdlib.TdApi.HttpUrl
import org.drinkless.tdlib.TdApi.InputMessageContent
import org.drinkless.tdlib.TdApi.InternalLinkType
import org.drinkless.tdlib.TdApi.JsonValue
import org.drinkless.tdlib.TdApi.LocalizationTargetInfo
import org.drinkless.tdlib.TdApi.Location
import org.drinkless.tdlib.TdApi.LoginUrlInfo
import org.drinkless.tdlib.TdApi.OptionValue
import org.drinkless.tdlib.TdApi.PremiumLimit
import org.drinkless.tdlib.TdApi.PremiumLimitType
import org.drinkless.tdlib.TdApi.PremiumState
import org.drinkless.tdlib.TdApi.Proxies
import org.drinkless.tdlib.TdApi.PushReceiverId
import org.drinkless.tdlib.TdApi.ReactionType
import org.drinkless.tdlib.TdApi.StatisticalGraph
import org.drinkless.tdlib.TdApi.StorageStatistics
import org.drinkless.tdlib.TdApi.StorePaymentPurpose
import org.drinkless.tdlib.TdApi.SuggestedAction
import org.drinkless.tdlib.TdApi.TMeUrls
import org.drinkless.tdlib.TdApi.TestInt
import org.drinkless.tdlib.TdApi.Text
import org.drinkless.tdlib.TdApi.ThemeParameters
import org.drinkless.tdlib.TdApi.Updates
import org.drinkless.tdlib.TdApi.User
import org.drinkless.tdlib.TdApi.Users

/**
 * Suspend function, which accepts Telegram terms of services.
 *
 * @param termsOfServiceId Terms of service identifier.
 */
suspend fun TelegramFlow.acceptTermsOfService(termsOfServiceId: String?) =
    this.sendFunctionLaunch(TdApi.AcceptTermsOfService(termsOfServiceId))

/**
 * Suspend function, which adds server-provided application changelog as messages to the chat 777000
 * (Telegram); for official applications only. Returns a 404 error if nothing changed.
 *
 * @param previousApplicationVersion The previous application version.
 */
suspend fun TelegramFlow.addApplicationChangelog(previousApplicationVersion: String?) =
    this.sendFunctionLaunch(TdApi.AddApplicationChangelog(previousApplicationVersion))

/**
 * Suspend function, which informs server about a purchase through App Store. For official
 * applications only.
 *
 * @param receipt App Store receipt.  
 * @param purpose Transaction purpose.
 */
suspend fun TelegramFlow.assignAppStoreTransaction(receipt: ByteArray?,
    purpose: StorePaymentPurpose?) =
    this.sendFunctionLaunch(TdApi.AssignAppStoreTransaction(receipt, purpose))

/**
 * Suspend function, which informs server about a purchase through Google Play. For official
 * applications only.
 *
 * @param packageName Application package name.  
 * @param storeProductId Identifier of the purchased store product.  
 * @param purchaseToken Google Play purchase token.  
 * @param purpose Transaction purpose.
 */
suspend fun TelegramFlow.assignGooglePlayTransaction(
  packageName: String?,
  storeProductId: String?,
  purchaseToken: String?,
  purpose: StorePaymentPurpose?
) = this.sendFunctionLaunch(TdApi.AssignGooglePlayTransaction(packageName, storeProductId,
    purchaseToken, purpose))

/**
 * Suspend function, which checks whether Telegram Premium purchase is possible. Must be called
 * before in-store Premium purchase.
 *
 * @param purpose Transaction purpose.
 */
suspend fun TelegramFlow.canPurchasePremium(purpose: StorePaymentPurpose?) =
    this.sendFunctionLaunch(TdApi.CanPurchasePremium(purpose))

/**
 * Suspend function, which checks whether the current session can be used to transfer a chat
 * ownership to another user.
 *
 * @return [CanTransferOwnershipResult] This class is an abstract base class.
 */
suspend fun TelegramFlow.canTransferOwnership(): CanTransferOwnershipResult =
    this.sendFunctionAsync(TdApi.CanTransferOwnership())

/**
 * Suspend function, which clears the list of recently used reactions.
 */
suspend fun TelegramFlow.clearRecentReactions() =
    this.sendFunctionLaunch(TdApi.ClearRecentReactions())

/**
 * Suspend function, which informs TDLib that the user clicked Premium subscription button on the
 * Premium features screen.
 */
suspend fun TelegramFlow.clickPremiumSubscriptionButton() =
    this.sendFunctionLaunch(TdApi.ClickPremiumSubscriptionButton())

/**
 * Suspend function, which closes the TDLib instance. All databases will be flushed to disk and
 * properly closed. After the close completes, updateAuthorizationState with authorizationStateClosed
 * will be sent. Can be called before initialization.
 */
suspend fun TelegramFlow.close() = this.sendFunctionLaunch(TdApi.Close())

/**
 * Suspend function, which creates a link for the given invoice; for bots only.
 *
 * @param invoice Information about the invoice of the type inputMessageInvoice.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.createInvoiceLink(invoice: InputMessageContent?): HttpUrl =
    this.sendFunctionAsync(TdApi.CreateInvoiceLink(invoice))

/**
 * Suspend function, which deletes commands supported by the bot for the given user scope and
 * language; for bots only.
 *
 * @param scope The scope to which the commands are relevant; pass null to delete commands in the
 * default bot command scope.  
 * @param languageCode A two-letter ISO 639-1 language code or an empty string.
 */
suspend fun TelegramFlow.deleteCommands(scope: BotCommandScope?, languageCode: String?) =
    this.sendFunctionLaunch(TdApi.DeleteCommands(scope, languageCode))

/**
 * Suspend function, which deletes saved credentials for all payment provider bots.
 */
suspend fun TelegramFlow.deleteSavedCredentials() =
    this.sendFunctionLaunch(TdApi.DeleteSavedCredentials())

/**
 * Suspend function, which closes the TDLib instance, destroying all local data without a proper
 * logout. The current user session will remain in the list of all active sessions. All local data will
 * be destroyed. After the destruction completes updateAuthorizationState with authorizationStateClosed
 * will be sent. Can be called before authorization.
 */
suspend fun TelegramFlow.destroy() = this.sendFunctionLaunch(TdApi.Destroy())

/**
 * Suspend function, which disconnects all websites from the current user&#039;s Telegram account.
 */
suspend fun TelegramFlow.disconnectAllWebsites() =
    this.sendFunctionLaunch(TdApi.DisconnectAllWebsites())

/**
 * Suspend function, which disconnects website from the current user&#039;s Telegram account.
 *
 * @param websiteId Website identifier.
 */
suspend fun TelegramFlow.disconnectWebsite(websiteId: Long) =
    this.sendFunctionLaunch(TdApi.DisconnectWebsite(websiteId))

/**
 * Suspend function, which returns application config, provided by the server. Can be called before
 * authorization.
 *
 * @return [JsonValue] This class is an abstract base class.
 */
suspend fun TelegramFlow.getApplicationConfig(): JsonValue =
    this.sendFunctionAsync(TdApi.GetApplicationConfig())

/**
 * Suspend function, which returns the link for downloading official Telegram application to be used
 * when the current user invites friends to Telegram.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getApplicationDownloadLink(): HttpUrl =
    this.sendFunctionAsync(TdApi.GetApplicationDownloadLink())

/**
 * Suspend function, which returns information about a bot that can be added to attachment menu.
 *
 * @param botUserId Bot&#039;s user identifier.
 *
 * @return [AttachmentMenuBot] Represents a bot, which can be added to attachment menu.
 */
suspend fun TelegramFlow.getAttachmentMenuBot(botUserId: Long): AttachmentMenuBot =
    this.sendFunctionAsync(TdApi.GetAttachmentMenuBot(botUserId))

/**
 * Suspend function, which returns the current authorization state; this is an offline request. For
 * informational purposes only. Use updateAuthorizationState instead to maintain the current
 * authorization state. Can be called before initialization.
 *
 * @return [AuthorizationState] This class is an abstract base class.
 */
suspend fun TelegramFlow.getAuthorizationState(): AuthorizationState =
    this.sendFunctionAsync(TdApi.GetAuthorizationState())

/**
 * Suspend function, which returns information about a bank card.
 *
 * @param bankCardNumber The bank card number.
 *
 * @return [BankCardInfo] Information about a bank card.
 */
suspend fun TelegramFlow.getBankCardInfo(bankCardNumber: String?): BankCardInfo =
    this.sendFunctionAsync(TdApi.GetBankCardInfo(bankCardNumber))

/**
 * Suspend function, which returns the name of a bot in the given language. Can be called only if
 * userTypeBot.canBeEdited == true.
 *
 * @param botUserId Identifier of the target bot.  
 * @param languageCode A two-letter ISO 639-1 language code or an empty string.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getBotName(botUserId: Long, languageCode: String?): Text =
    this.sendFunctionAsync(TdApi.GetBotName(botUserId, languageCode))

/**
 * Suspend function, which returns list of commands supported by the bot for the given user scope
 * and language; for bots only.
 *
 * @param scope The scope to which the commands are relevant; pass null to get commands in the
 * default bot command scope.  
 * @param languageCode A two-letter ISO 639-1 language code or an empty string.
 *
 * @return [BotCommands] Contains a list of bot commands.
 */
suspend fun TelegramFlow.getCommands(scope: BotCommandScope?, languageCode: String?): BotCommands =
    this.sendFunctionAsync(TdApi.GetCommands(scope, languageCode))

/**
 * Suspend function, which returns all website where the current user used Telegram to log in.
 *
 * @return [ConnectedWebsites] Contains a list of websites the current user is logged in with
 * Telegram.
 */
suspend fun TelegramFlow.getConnectedWebsites(): ConnectedWebsites =
    this.sendFunctionAsync(TdApi.GetConnectedWebsites())

/**
 * Suspend function, which returns all updates needed to restore current TDLib state, i.e. all
 * actual updateAuthorizationState/updateUser/updateNewChat and others. This is especially useful if
 * TDLib is run in a separate process. Can be called before initialization.
 *
 * @return [Updates] Contains a list of updates.
 */
suspend fun TelegramFlow.getCurrentState(): Updates =
    this.sendFunctionAsync(TdApi.GetCurrentState())

/**
 * Suspend function, which returns information about a tg:// deep link. Use
 * &quot;tg://need_update_for_some_feature&quot; or &quot;tg:someUnsupportedFeature&quot; for testing.
 * Returns a 404 error for unknown links. Can be called before authorization.
 *
 * @param link The link.
 *
 * @return [DeepLinkInfo] Contains information about a tg: deep link.
 */
suspend fun TelegramFlow.getDeepLinkInfo(link: String?): DeepLinkInfo =
    this.sendFunctionAsync(TdApi.GetDeepLinkInfo(link))

/**
 * Suspend function, which returns an HTTP URL which can be used to automatically authorize the
 * current user on a website after clicking an HTTP link. Use the method getExternalLinkInfo to find
 * whether a prior user confirmation is needed.
 *
 * @param link The HTTP link.  
 * @param allowWriteAccess Pass true if the current user allowed the bot, returned in
 * getExternalLinkInfo, to send them messages.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getExternalLink(link: String?, allowWriteAccess: Boolean): HttpUrl =
    this.sendFunctionAsync(TdApi.GetExternalLink(link, allowWriteAccess))

/**
 * Suspend function, which returns information about an action to be done when the current user
 * clicks an external link. Don&#039;t use this method for links from secret chats if web page preview
 * is disabled in secret chats.
 *
 * @param link The link.
 *
 * @return [LoginUrlInfo] This class is an abstract base class.
 */
suspend fun TelegramFlow.getExternalLinkInfo(link: String?): LoginUrlInfo =
    this.sendFunctionAsync(TdApi.GetExternalLinkInfo(link))

/**
 * Suspend function, which returns a list of common group chats with a given user. Chats are sorted
 * by their type and creation date.
 *
 * @param userId User identifier.  
 * @param offsetChatId Chat identifier starting from which to return chats; use 0 for the first
 * request.  
 * @param limit The maximum number of chats to be returned; up to 100.
 *
 * @return [Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.getGroupsInCommon(
  userId: Long,
  offsetChatId: Long,
  limit: Int
): Chats = this.sendFunctionAsync(TdApi.GetGroupsInCommon(userId, offsetChatId, limit))

/**
 * Suspend function, which returns an HTTPS or a tg: link with the given type. Can be called before
 * authorization.
 *
 * @param type Expected type of the link.  
 * @param isHttp Pass true to create an HTTPS link (only available for some link types); pass false
 * to create a tg: link.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getInternalLink(type: InternalLinkType?, isHttp: Boolean): HttpUrl =
    this.sendFunctionAsync(TdApi.GetInternalLink(type, isHttp))

/**
 * Suspend function, which returns information about the type of an internal link. Returns a 404
 * error if the link is not internal. Can be called before authorization.
 *
 * @param link The link.
 *
 * @return [InternalLinkType] This class is an abstract base class.
 */
suspend fun TelegramFlow.getInternalLinkType(link: String?): InternalLinkType =
    this.sendFunctionAsync(TdApi.GetInternalLinkType(link))

/**
 * Suspend function, which converts a JsonValue object to corresponding JSON-serialized string. Can
 * be called synchronously.
 *
 * @param jsonValue The JsonValue object.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getJsonString(jsonValue: JsonValue?): Text =
    this.sendFunctionAsync(TdApi.GetJsonString(jsonValue))

/**
 * Suspend function, which converts a JSON-serialized string to corresponding JsonValue object. Can
 * be called synchronously.
 *
 * @param json The JSON-serialized string.
 *
 * @return [JsonValue] This class is an abstract base class.
 */
suspend fun TelegramFlow.getJsonValue(json: String?): JsonValue =
    this.sendFunctionAsync(TdApi.GetJsonValue(json))

/**
 * Suspend function, which returns information about the current localization target. This is an
 * offline request if onlyLocal is true. Can be called before authorization.
 *
 * @param onlyLocal Pass true to get only locally available information without sending network
 * requests.
 *
 * @return [LocalizationTargetInfo] Contains information about the current localization target.
 */
suspend fun TelegramFlow.getLocalizationTargetInfo(onlyLocal: Boolean): LocalizationTargetInfo =
    this.sendFunctionAsync(TdApi.GetLocalizationTargetInfo(onlyLocal))

/**
 * Suspend function, which replaces text entities with Markdown formatting in a human-friendly
 * format. Entities that can&#039;t be represented in Markdown unambiguously are kept as is. Can be
 * called synchronously.
 *
 * @param text The text.
 *
 * @return [FormattedText] A text with some entities.
 */
suspend fun TelegramFlow.getMarkdownText(text: FormattedText?): FormattedText =
    this.sendFunctionAsync(TdApi.GetMarkdownText(text))

/**
 * Suspend function, which returns the current user.
 *
 * @return [User] Represents a user.
 */
suspend fun TelegramFlow.getMe(): User = this.sendFunctionAsync(TdApi.GetMe())

/**
 * Suspend function, which returns menu button set by the bot for the given user; for bots only.
 *
 * @param userId Identifier of the user or 0 to get the default menu button.
 *
 * @return [BotMenuButton] Describes a button to be shown instead of bot commands menu button.
 */
suspend fun TelegramFlow.getMenuButton(userId: Long): BotMenuButton =
    this.sendFunctionAsync(TdApi.GetMenuButton(userId))

/**
 * Suspend function, which returns the value of an option by its name. (Check the list of available
 * options on https://core.telegram.org/tdlib/options.) Can be called before authorization. Can be
 * called synchronously for options &quot;version&quot; and &quot;commit_hash&quot;.
 *
 * @param name The name of the option.
 *
 * @return [OptionValue] This class is an abstract base class.
 */
suspend fun TelegramFlow.getOption(name: String?): OptionValue =
    this.sendFunctionAsync(TdApi.GetOption(name))

/**
 * Suspend function, which returns information about a limit, increased for Premium users. Returns a
 * 404 error if the limit is unknown.
 *
 * @param limitType Type of the limit.
 *
 * @return [PremiumLimit] Contains information about a limit, increased for Premium users.
 */
suspend fun TelegramFlow.getPremiumLimit(limitType: PremiumLimitType?): PremiumLimit =
    this.sendFunctionAsync(TdApi.GetPremiumLimit(limitType))

/**
 * Suspend function, which returns state of Telegram Premium subscription and promotion videos for
 * Premium features.
 *
 * @return [PremiumState] Contains state of Telegram Premium subscription and promotion videos for
 * Premium features.
 */
suspend fun TelegramFlow.getPremiumState(): PremiumState =
    this.sendFunctionAsync(TdApi.GetPremiumState())

/**
 * Suspend function, which returns list of proxies that are currently set up. Can be called before
 * authorization.
 *
 * @return [Proxies] Represents a list of proxy servers.
 */
suspend fun TelegramFlow.getProxies(): Proxies = this.sendFunctionAsync(TdApi.GetProxies())

/**
 * Suspend function, which returns a globally unique push notification subscription identifier for
 * identification of an account, which has received a push notification. Can be called synchronously.
 *
 * @param payload JSON-encoded push notification payload.
 *
 * @return [PushReceiverId] Contains a globally unique push receiver identifier, which can be used
 * to identify which account has received a push notification.
 */
suspend fun TelegramFlow.getPushReceiverId(payload: String?): PushReceiverId =
    this.sendFunctionAsync(TdApi.GetPushReceiverId(payload))

/**
 * Suspend function, which returns up to 20 recently used inline bots in the order of their last
 * usage.
 *
 * @return [Users] Represents a list of users.
 */
suspend fun TelegramFlow.getRecentInlineBots(): Users =
    this.sendFunctionAsync(TdApi.GetRecentInlineBots())

/**
 * Suspend function, which returns t.me URLs recently visited by a newly registered user.
 *
 * @param referrer Google Play referrer to identify the user.
 *
 * @return [TMeUrls] Contains a list of t.me URLs.
 */
suspend fun TelegramFlow.getRecentlyVisitedTMeUrls(referrer: String?): TMeUrls =
    this.sendFunctionAsync(TdApi.GetRecentlyVisitedTMeUrls(referrer))

/**
 * Suspend function, which loads an asynchronous or a zoomed in statistical graph.
 *
 * @param chatId Chat identifier.  
 * @param token The token for graph loading.  
 * @param x X-value for zoomed in graph or 0 otherwise.
 *
 * @return [StatisticalGraph] This class is an abstract base class.
 */
suspend fun TelegramFlow.getStatisticalGraph(
  chatId: Long,
  token: String?,
  x: Long
): StatisticalGraph = this.sendFunctionAsync(TdApi.GetStatisticalGraph(chatId, token, x))

/**
 * Suspend function, which returns localized name of the Telegram support user; for Telegram support
 * only.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getSupportName(): Text = this.sendFunctionAsync(TdApi.GetSupportName())

/**
 * Suspend function, which converts a themeParameters object to corresponding JSON-serialized
 * string. Can be called synchronously.
 *
 * @param theme Theme parameters to convert to JSON.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getThemeParametersJsonString(theme: ThemeParameters?): Text =
    this.sendFunctionAsync(TdApi.GetThemeParametersJsonString(theme))

/**
 * Suspend function, which hides a suggested action.
 *
 * @param action Suggested action to hide.
 */
suspend fun TelegramFlow.hideSuggestedAction(action: SuggestedAction?) =
    this.sendFunctionLaunch(TdApi.HideSuggestedAction(action))

/**
 * Suspend function, which closes the TDLib instance after a proper logout. Requires an available
 * network connection. All local data will be destroyed. After the logout completes,
 * updateAuthorizationState with authorizationStateClosed will be sent.
 */
suspend fun TelegramFlow.logOut() = this.sendFunctionLaunch(TdApi.LogOut())

/**
 * Suspend function, which optimizes storage usage, i.e. deletes some files and returns new storage
 * usage statistics. Secret thumbnails can&#039;t be deleted.
 *
 * @param size Limit on the total size of files after deletion, in bytes. Pass -1 to use the default
 * limit.  
 * @param ttl Limit on the time that has passed since the last time a file was accessed (or creation
 * time for some filesystems). Pass -1 to use the default limit.  
 * @param count Limit on the total number of files after deletion. Pass -1 to use the default limit.
 *  
 * @param immunityDelay The amount of time after the creation of a file during which it can&#039;t
 * be deleted, in seconds. Pass -1 to use the default value.  
 * @param fileTypes If non-empty, only files with the given types are considered. By default, all
 * types except thumbnails, profile photos, stickers and wallpapers are deleted.  
 * @param chatIds If non-empty, only files from the given chats are considered. Use 0 as chat
 * identifier to delete files not belonging to any chat (e.g., profile photos).  
 * @param excludeChatIds If non-empty, files from the given chats are excluded. Use 0 as chat
 * identifier to exclude all files not belonging to any chat (e.g., profile photos).  
 * @param returnDeletedFileStatistics Pass true if statistics about the files that were deleted must
 * be returned instead of the whole storage usage statistics. Affects only returned statistics.  
 * @param chatLimit Same as in getStorageStatistics. Affects only returned statistics.
 *
 * @return [StorageStatistics] Contains the exact storage usage statistics split by chats and file
 * type.
 */
suspend fun TelegramFlow.optimizeStorage(
  size: Long,
  ttl: Int,
  count: Int,
  immunityDelay: Int,
  fileTypes: Array<FileType>?,
  chatIds: LongArray?,
  excludeChatIds: LongArray?,
  returnDeletedFileStatistics: Boolean,
  chatLimit: Int
): StorageStatistics = this.sendFunctionAsync(TdApi.OptimizeStorage(size, ttl, count, immunityDelay,
    fileTypes, chatIds, excludeChatIds, returnDeletedFileStatistics, chatLimit))

/**
 * Suspend function, which parses Markdown entities in a human-friendly format, ignoring markup
 * errors. Can be called synchronously.
 *
 * @param text The text to parse. For example, &quot;__italic__ ~~strikethrough~~ ||spoiler||
 * **bold** `code` ```pre``` __[italic__ textUrl](telegram.org) _Italic**bold italic_Bold**&quot;.
 *
 * @return [FormattedText] A text with some entities.
 */
suspend fun TelegramFlow.parseMarkdown(text: FormattedText?): FormattedText =
    this.sendFunctionAsync(TdApi.ParseMarkdown(text))

/**
 * Suspend function, which rates recognized speech in a video note or a voice note message.
 *
 * @param chatId Identifier of the chat to which the message belongs.  
 * @param messageId Identifier of the message.  
 * @param isGood Pass true if the speech recognition is good.
 */
suspend fun TelegramFlow.rateSpeechRecognition(
  chatId: Long,
  messageId: Long,
  isGood: Boolean
) = this.sendFunctionLaunch(TdApi.RateSpeechRecognition(chatId, messageId, isGood))

/**
 * Suspend function, which recognizes speech in a video note or a voice note message. The message
 * must be successfully sent and must not be scheduled. May return an error with a message
 * &quot;MSG_VOICE_TOO_LONG&quot; if media duration is too big to be recognized.
 *
 * @param chatId Identifier of the chat to which the message belongs.  
 * @param messageId Identifier of the message.
 */
suspend fun TelegramFlow.recognizeSpeech(chatId: Long, messageId: Long) =
    this.sendFunctionLaunch(TdApi.RecognizeSpeech(chatId, messageId))

/**
 * Suspend function, which registers the currently used device for receiving push notifications.
 * Returns a globally unique identifier of the push notification subscription.
 *
 * @param deviceToken Device token.  
 * @param otherUserIds List of user identifiers of other users currently using the application.
 *
 * @return [PushReceiverId] Contains a globally unique push receiver identifier, which can be used
 * to identify which account has received a push notification.
 */
suspend fun TelegramFlow.registerDevice(deviceToken: DeviceToken?, otherUserIds: LongArray?):
    PushReceiverId = this.sendFunctionAsync(TdApi.RegisterDevice(deviceToken, otherUserIds))

/**
 * Suspend function, which removes a hashtag from the list of recently used hashtags.
 *
 * @param hashtag Hashtag to delete.
 */
suspend fun TelegramFlow.removeRecentHashtag(hashtag: String?) =
    this.sendFunctionLaunch(TdApi.RemoveRecentHashtag(hashtag))

/**
 * Suspend function, which searches for recently used hashtags by their prefix.
 *
 * @param prefix Hashtag prefix to search for.  
 * @param limit The maximum number of hashtags to be returned.
 *
 * @return [Hashtags] Contains a list of hashtags.
 */
suspend fun TelegramFlow.searchHashtags(prefix: String?, limit: Int): Hashtags =
    this.sendFunctionAsync(TdApi.SearchHashtags(prefix, limit))

/**
 * Suspend function, which sends a custom request; for bots only.
 *
 * @param method The method name.  
 * @param parameters JSON-serialized method parameters.
 *
 * @return [CustomRequestResult] Contains the result of a custom request.
 */
suspend fun TelegramFlow.sendCustomRequest(method: String?, parameters: String?):
    CustomRequestResult = this.sendFunctionAsync(TdApi.SendCustomRequest(method, parameters))

/**
 * Suspend function, which succeeds after a specified amount of time has passed. Can be called
 * before initialization.
 *
 * @param seconds Number of seconds before the function returns.
 */
suspend fun TelegramFlow.setAlarm(seconds: Double) =
    this.sendFunctionLaunch(TdApi.SetAlarm(seconds))

/**
 * Suspend function, which changes the bio of the current user.
 *
 * @param bio The new value of the user bio; 0-getOption(&quot;bio_length_max&quot;) characters
 * without line feeds.
 */
suspend fun TelegramFlow.setBio(bio: String?) = this.sendFunctionLaunch(TdApi.SetBio(bio))

/**
 * Suspend function, which sets the name of a bot. Can be called only if userTypeBot.canBeEdited ==
 * true.
 *
 * @param botUserId Identifier of the target bot.  
 * @param languageCode A two-letter ISO 639-1 language code. If empty, the description will be shown
 * to all users, for which language there are no dedicated description.  
 * @param name New bot&#039;s name on the specified language; 0-64 characters; must be non-empty if
 * language code is empty.
 */
suspend fun TelegramFlow.setBotName(
  botUserId: Long,
  languageCode: String?,
  name: String?
) = this.sendFunctionLaunch(TdApi.SetBotName(botUserId, languageCode, name))

/**
 * Suspend function, which sets the list of commands supported by the bot for the given user scope
 * and language; for bots only.
 *
 * @param scope The scope to which the commands are relevant; pass null to change commands in the
 * default bot command scope.  
 * @param languageCode A two-letter ISO 639-1 language code. If empty, the commands will be applied
 * to all users from the given scope, for which language there are no dedicated commands.  
 * @param commands List of the bot&#039;s commands.
 */
suspend fun TelegramFlow.setCommands(
  scope: BotCommandScope?,
  languageCode: String?,
  commands: Array<BotCommand>?
) = this.sendFunctionLaunch(TdApi.SetCommands(scope, languageCode, commands))

/**
 * Suspend function, which sets default administrator rights for adding the bot to channel chats;
 * for bots only.
 *
 * @param defaultChannelAdministratorRights Default administrator rights for adding the bot to
 * channels; may be null.
 */
suspend
    fun TelegramFlow.setDefaultChannelAdministratorRights(defaultChannelAdministratorRights: ChatAdministratorRights?
    = null) =
    this.sendFunctionLaunch(TdApi.SetDefaultChannelAdministratorRights(defaultChannelAdministratorRights))

/**
 * Suspend function, which sets default administrator rights for adding the bot to basic group and
 * supergroup chats; for bots only.
 *
 * @param defaultGroupAdministratorRights Default administrator rights for adding the bot to basic
 * group and supergroup chats; may be null.
 */
suspend
    fun TelegramFlow.setDefaultGroupAdministratorRights(defaultGroupAdministratorRights: ChatAdministratorRights?
    = null) =
    this.sendFunctionLaunch(TdApi.SetDefaultGroupAdministratorRights(defaultGroupAdministratorRights))

/**
 * Suspend function, which changes type of default reaction for the current user.
 *
 * @param reactionType New type of the default reaction.
 */
suspend fun TelegramFlow.setDefaultReactionType(reactionType: ReactionType?) =
    this.sendFunctionLaunch(TdApi.SetDefaultReactionType(reactionType))

/**
 * Suspend function, which changes the location of the current user. Needs to be called if
 * getOption(&quot;is_location_visible&quot;) is true and location changes for more than 1 kilometer.
 *
 * @param location The new location of the user.
 */
suspend fun TelegramFlow.setLocation(location: Location?) =
    this.sendFunctionLaunch(TdApi.SetLocation(location))

/**
 * Suspend function, which sets menu button for the given user or for all users; for bots only.
 *
 * @param userId Identifier of the user or 0 to set menu button for all users.  
 * @param menuButton New menu button.
 */
suspend fun TelegramFlow.setMenuButton(userId: Long, menuButton: BotMenuButton?) =
    this.sendFunctionLaunch(TdApi.SetMenuButton(userId, menuButton))

/**
 * Suspend function, which changes the first and last name of the current user.
 *
 * @param firstName The new value of the first name for the current user; 1-64 characters.  
 * @param lastName The new value of the optional last name for the current user; 0-64 characters.
 */
suspend fun TelegramFlow.setName(firstName: String?, lastName: String?) =
    this.sendFunctionLaunch(TdApi.SetName(firstName, lastName))

/**
 * Suspend function, which sets the value of an option. (Check the list of available options on
 * https://core.telegram.org/tdlib/options.) Only writable options can be set. Can be called before
 * authorization.
 *
 * @param name The name of the option.  
 * @param value The new value of the option; pass null to reset option value to a default value.
 */
suspend fun TelegramFlow.setOption(name: String?, value: OptionValue?) =
    this.sendFunctionLaunch(TdApi.SetOption(name, value))

/**
 * Suspend function, which sets the parameters for TDLib initialization. Works only when the current
 * authorization state is authorizationStateWaitTdlibParameters.
 *
 * @param useTestDc Pass true to use Telegram test environment instead of the production
 * environment.  
 * @param databaseDirectory The path to the directory for the persistent database; if empty, the
 * current working directory will be used.  
 * @param filesDirectory The path to the directory for storing files; if empty, databaseDirectory
 * will be used.  
 * @param databaseEncryptionKey Encryption key for the database. If the encryption key is invalid,
 * then an error with code 401 will be returned.  
 * @param useFileDatabase Pass true to keep information about downloaded and uploaded files between
 * application restarts.  
 * @param useChatInfoDatabase Pass true to keep cache of users, basic groups, supergroups, channels
 * and secret chats between restarts. Implies useFileDatabase.  
 * @param useMessageDatabase Pass true to keep cache of chats and messages between restarts. Implies
 * useChatInfoDatabase.  
 * @param useSecretChats Pass true to enable support for secret chats.  
 * @param apiId Application identifier for Telegram API access, which can be obtained at
 * https://my.telegram.org.  
 * @param apiHash Application identifier hash for Telegram API access, which can be obtained at
 * https://my.telegram.org.  
 * @param systemLanguageCode IETF language tag of the user&#039;s operating system language; must be
 * non-empty.  
 * @param deviceModel Model of the device the application is being run on; must be non-empty.  
 * @param systemVersion Version of the operating system the application is being run on. If empty,
 * the version is automatically detected by TDLib.  
 * @param applicationVersion Application version; must be non-empty.  
 * @param enableStorageOptimizer Pass true to automatically delete old files in background.  
 * @param ignoreFileNames Pass true to ignore original file names for downloaded files. Otherwise,
 * downloaded files are saved under names as close as possible to the original name.
 */
suspend fun TelegramFlow.setTdlibParameters(
  useTestDc: Boolean,
  databaseDirectory: String?,
  filesDirectory: String?,
  databaseEncryptionKey: ByteArray?,
  useFileDatabase: Boolean,
  useChatInfoDatabase: Boolean,
  useMessageDatabase: Boolean,
  useSecretChats: Boolean,
  apiId: Int,
  apiHash: String?,
  systemLanguageCode: String?,
  deviceModel: String?,
  systemVersion: String?,
  applicationVersion: String?,
  enableStorageOptimizer: Boolean,
  ignoreFileNames: Boolean
) = this.sendFunctionLaunch(TdApi.SetTdlibParameters(useTestDc, databaseDirectory, filesDirectory,
    databaseEncryptionKey, useFileDatabase, useChatInfoDatabase, useMessageDatabase, useSecretChats,
    apiId, apiHash, systemLanguageCode, deviceModel, systemVersion, applicationVersion,
    enableStorageOptimizer, ignoreFileNames))

/**
 * Suspend function, which forces an updates.getDifference call to the Telegram servers; for testing
 * only.
 */
suspend fun TelegramFlow.testGetDifference() = this.sendFunctionLaunch(TdApi.TestGetDifference())

/**
 * Suspend function, which returns the specified error and ensures that the Error object is used;
 * for testing only. Can be called synchronously.
 *
 * @param error The error to be returned.
 *
 * @return [Error] An object of this type can be returned on every function call, in case of an
 * error.
 */
suspend fun TelegramFlow.testReturnError(error: Error?): Error =
    this.sendFunctionAsync(TdApi.TestReturnError(error))

/**
 * Suspend function, which returns the squared received number; for testing only. This is an offline
 * method. Can be called before authorization.
 *
 * @param x Number to square.
 *
 * @return [TestInt] A simple object containing a number; for testing only.
 */
suspend fun TelegramFlow.testSquareInt(x: Int): TestInt =
    this.sendFunctionAsync(TdApi.TestSquareInt(x))

/**
 * Suspend function, which changes pause state of all files in the file download list.
 *
 * @param arePaused Pass true to pause all downloads; pass false to unpause them.
 */
suspend fun TelegramFlow.toggleAllDownloadsArePaused(arePaused: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleAllDownloadsArePaused(arePaused))

/**
 * Suspend function, which adds or removes a bot to attachment menu. Bot can be added to attachment
 * menu, only if userTypeBot.canBeAddedToAttachmentMenu == true.
 *
 * @param botUserId Bot&#039;s user identifier.  
 * @param isAdded Pass true to add the bot to attachment menu; pass false to remove the bot from
 * attachment menu.  
 * @param allowWriteAccess Pass true if the current user allowed the bot to send them messages.
 * Ignored if isAdded is false.
 */
suspend fun TelegramFlow.toggleBotIsAddedToAttachmentMenu(
  botUserId: Long,
  isAdded: Boolean,
  allowWriteAccess: Boolean
) = this.sendFunctionLaunch(TdApi.ToggleBotIsAddedToAttachmentMenu(botUserId, isAdded,
    allowWriteAccess))

/**
 * Suspend function, which changes pause state of a file in the file download list.
 *
 * @param fileId Identifier of the downloaded file.  
 * @param isPaused Pass true if the download is paused.
 */
suspend fun TelegramFlow.toggleDownloadIsPaused(fileId: Int, isPaused: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleDownloadIsPaused(fileId, isPaused))

/**
 * Suspend function, which translates a text to the given language. If the current user is a
 * Telegram Premium user, then text formatting is preserved.
 *
 * @param text Text to translate.  
 * @param toLanguageCode Language code of the language to which the message is translated. Must be
 * one of &quot;af&quot;, &quot;sq&quot;, &quot;am&quot;, &quot;ar&quot;, &quot;hy&quot;,
 * &quot;az&quot;, &quot;eu&quot;, &quot;be&quot;, &quot;bn&quot;, &quot;bs&quot;, &quot;bg&quot;,
 * &quot;ca&quot;, &quot;ceb&quot;, &quot;zh-CN&quot;, &quot;zh&quot;, &quot;zh-Hans&quot;,
 * &quot;zh-TW&quot;, &quot;zh-Hant&quot;, &quot;co&quot;, &quot;hr&quot;, &quot;cs&quot;,
 * &quot;da&quot;, &quot;nl&quot;, &quot;en&quot;, &quot;eo&quot;, &quot;et&quot;, &quot;fi&quot;,
 * &quot;fr&quot;, &quot;fy&quot;, &quot;gl&quot;, &quot;ka&quot;, &quot;de&quot;, &quot;el&quot;,
 * &quot;gu&quot;, &quot;ht&quot;, &quot;ha&quot;, &quot;haw&quot;, &quot;he&quot;, &quot;iw&quot;,
 * &quot;hi&quot;, &quot;hmn&quot;, &quot;hu&quot;, &quot;is&quot;, &quot;ig&quot;, &quot;id&quot;,
 * &quot;in&quot;, &quot;ga&quot;, &quot;it&quot;, &quot;ja&quot;, &quot;jv&quot;, &quot;kn&quot;,
 * &quot;kk&quot;, &quot;km&quot;, &quot;rw&quot;, &quot;ko&quot;, &quot;ku&quot;, &quot;ky&quot;,
 * &quot;lo&quot;, &quot;la&quot;, &quot;lv&quot;, &quot;lt&quot;, &quot;lb&quot;, &quot;mk&quot;,
 * &quot;mg&quot;, &quot;ms&quot;, &quot;ml&quot;, &quot;mt&quot;, &quot;mi&quot;, &quot;mr&quot;,
 * &quot;mn&quot;, &quot;my&quot;, &quot;ne&quot;, &quot;no&quot;, &quot;ny&quot;, &quot;or&quot;,
 * &quot;ps&quot;, &quot;fa&quot;, &quot;pl&quot;, &quot;pt&quot;, &quot;pa&quot;, &quot;ro&quot;,
 * &quot;ru&quot;, &quot;sm&quot;, &quot;gd&quot;, &quot;sr&quot;, &quot;st&quot;, &quot;sn&quot;,
 * &quot;sd&quot;, &quot;si&quot;, &quot;sk&quot;, &quot;sl&quot;, &quot;so&quot;, &quot;es&quot;,
 * &quot;su&quot;, &quot;sw&quot;, &quot;sv&quot;, &quot;tl&quot;, &quot;tg&quot;, &quot;ta&quot;,
 * &quot;tt&quot;, &quot;te&quot;, &quot;th&quot;, &quot;tr&quot;, &quot;tk&quot;, &quot;uk&quot;,
 * &quot;ur&quot;, &quot;ug&quot;, &quot;uz&quot;, &quot;vi&quot;, &quot;cy&quot;, &quot;xh&quot;,
 * &quot;yi&quot;, &quot;ji&quot;, &quot;yo&quot;, &quot;zu&quot;.
 *
 * @return [FormattedText] A text with some entities.
 */
suspend fun TelegramFlow.translateText(text: FormattedText?, toLanguageCode: String?): FormattedText
    = this.sendFunctionAsync(TdApi.TranslateText(text, toLanguageCode))
