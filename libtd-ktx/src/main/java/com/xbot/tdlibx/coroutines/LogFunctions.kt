@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Suspend function, which adds a message to TDLib internal log. This is an offline method. Can be
 * called before authorization. Can be called synchronously.
 *
 * @param verbosityLevel The minimum verbosity level needed for the message to be logged, 0-1023.
 * @param text Text of a message to log.
 */
suspend fun TelegramFlow.addLogMessage(verbosityLevel: Int, text: String?) =
    this.sendFunctionLaunch(TdApi.AddLogMessage(verbosityLevel, text))

/**
 * Suspend function, which returns a list of service actions taken by chat members and
 * administrators in the last 48 hours. Available only for supergroups and channels. Requires
 * administrator rights. Returns results in reverse chronological order (i. e., in order of decreasing
 * eventId).
 *
 * @param chatId Chat identifier.
 * @param query Search query by which to filter events.
 * @param fromEventId Identifier of an event from which to return results. Use 0 to get results from
 * the latest events.
 * @param limit The maximum number of events to return; up to 100.
 * @param filters The types of events to return. By default, all types will be returned.
 * @param userIds User identifiers by which to filter events. By default, events relating to all
 * users will be returned.
 *
 * @return [TdApi.ChatEvents] Contains a list of chat events.
 */
suspend fun TelegramFlow.getChatEventLog(
    chatId: Long,
    query: String?,
    fromEventId: Long,
    limit: Int,
    filters: TdApi.ChatEventLogFilters?,
    userIds: LongArray?
): TdApi.ChatEvents = this.sendFunctionAsync(TdApi.GetChatEventLog(chatId, query, fromEventId, limit, filters, userIds))

/**
 * Suspend function, which returns information about currently used log stream for internal logging
 * of TDLib. This is an offline method. Can be called before authorization. Can be called
 * synchronously.
 *
 * @return [TdApi.LogStream] This class is an abstract base class.
 */
suspend fun TelegramFlow.getLogStream(): TdApi.LogStream = this.sendFunctionAsync(TdApi.GetLogStream())

/**
 * Suspend function, which returns current verbosity level for a specified TDLib internal log tag.
 * This is an offline method. Can be called before authorization. Can be called synchronously.
 *
 * @param tag Logging tag to change verbosity level.
 *
 * @return [TdApi.LogVerbosityLevel] Contains a TDLib internal log verbosity level.
 */
suspend fun TelegramFlow.getLogTagVerbosityLevel(tag: String?): TdApi.LogVerbosityLevel =
    this.sendFunctionAsync(TdApi.GetLogTagVerbosityLevel(tag))

/**
 * Suspend function, which returns list of available TDLib internal log tags, for example,
 * [&quot;actor&quot;, &quot;binlog&quot;, &quot;connections&quot;, &quot;notifications&quot;,
 * &quot;proxy&quot;]. This is an offline method. Can be called before authorization. Can be called
 * synchronously.
 *
 * @return [TdApi.LogTags] Contains a list of available TDLib internal log tags.
 */
suspend fun TelegramFlow.getLogTags(): TdApi.LogTags = this.sendFunctionAsync(TdApi.GetLogTags())

/**
 * Suspend function, which returns current verbosity level of the internal logging of TDLib. This is
 * an offline method. Can be called before authorization. Can be called synchronously.
 *
 * @return [TdApi.LogVerbosityLevel] Contains a TDLib internal log verbosity level.
 */
suspend fun TelegramFlow.getLogVerbosityLevel(): TdApi.LogVerbosityLevel =
    this.sendFunctionAsync(TdApi.GetLogVerbosityLevel())

/**
 * Suspend function, which returns an HTTP URL which can be used to automatically authorize the user
 * on a website after clicking an inline button of type inlineKeyboardButtonTypeLoginUrl. Use the
 * method getLoginUrlInfo to find whether a prior user confirmation is needed. If an error is returned,
 * then the button must be handled as an ordinary URL button.
 *
 * @param chatId Chat identifier of the message with the button.
 * @param messageId Message identifier of the message with the button.
 * @param buttonId Button identifier.
 * @param allowWriteAccess True, if the user allowed the bot to send them messages.
 *
 * @return [TdApi.HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getLoginUrl(
    chatId: Long,
    messageId: Long,
    buttonId: Long,
    allowWriteAccess: Boolean
): TdApi.HttpUrl = this.sendFunctionAsync(TdApi.GetLoginUrl(chatId, messageId, buttonId,
    allowWriteAccess))

/**
 * Suspend function, which returns information about a button of type
 * inlineKeyboardButtonTypeLoginUrl. The method needs to be called when the user presses the button.
 *
 * @param chatId Chat identifier of the message with the button.
 * @param messageId Message identifier of the message with the button.
 * @param buttonId Button identifier.
 *
 * @return [TdApi.LoginUrlInfo] This class is an abstract base class.
 */
suspend fun TelegramFlow.getLoginUrlInfo(
    chatId: Long,
    messageId: Long,
    buttonId: Long
): TdApi.LoginUrlInfo = this.sendFunctionAsync(TdApi.GetLoginUrlInfo(chatId, messageId, buttonId))

/**
 * Suspend function, which saves application log event on the server. Can be called before
 * authorization.
 *
 * @param type Event type.
 * @param chatId Optional chat identifier, associated with the event.
 * @param data The log event data.
 */
suspend fun TelegramFlow.saveApplicationLogEvent(
    type: String?,
    chatId: Long,
    data: TdApi.JsonValue?
) = this.sendFunctionLaunch(TdApi.SaveApplicationLogEvent(type, chatId, data))

/**
 * Suspend function, which sets new log stream for internal logging of TDLib. This is an offline
 * method. Can be called before authorization. Can be called synchronously.
 *
 * @param logStream New log stream.
 */
suspend fun TelegramFlow.setLogStream(logStream: TdApi.LogStream?) =
    this.sendFunctionLaunch(TdApi.SetLogStream(logStream))

/**
 * Suspend function, which sets the verbosity level for a specified TDLib internal log tag. This is
 * an offline method. Can be called before authorization. Can be called synchronously.
 *
 * @param tag Logging tag to change verbosity level.
 * @param newVerbosityLevel New verbosity level; 1-1024.
 */
suspend fun TelegramFlow.setLogTagVerbosityLevel(tag: String?, newVerbosityLevel: Int) =
    this.sendFunctionLaunch(TdApi.SetLogTagVerbosityLevel(tag, newVerbosityLevel))

/**
 * Suspend function, which sets the verbosity level of the internal logging of TDLib. This is an
 * offline method. Can be called before authorization. Can be called synchronously.
 *
 * @param newVerbosityLevel New value of the verbosity level for logging. Value 0 corresponds to
 * fatal errors, value 1 corresponds to errors, value 2 corresponds to warnings and debug warnings,
 * value 3 corresponds to informational, value 4 corresponds to debug, value 5 corresponds to verbose
 * debug, value greater than 5 and up to 1023 can be used to enable even more logging.
 */
suspend fun TelegramFlow.setLogVerbosityLevel(newVerbosityLevel: Int) =
    this.sendFunctionLaunch(TdApi.SetLogVerbosityLevel(newVerbosityLevel))