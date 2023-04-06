@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Suspend function, which adds a local message to a chat. The message is persistent across
 * application restarts only if the message database is used. Returns the added message.
 *
 * @param chatId Target chat.
 * @param senderId Identifier of the sender of the message.
 * @param replyToMessageId Identifier of the message to reply to or 0.
 * @param disableNotification Pass true to disable notification for the message.
 * @param inputMessageContent The content of the message to be added.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.addLocalMessage(
    chatId: Long,
    senderId: TdApi.MessageSender,
    replyToMessageId: Long,
    disableNotification: Boolean,
    inputMessageContent: TdApi.InputMessageContent?
): TdApi.Message = this.sendFunctionAsync(TdApi.AddLocalMessage(chatId, senderId, replyToMessageId, disableNotification, inputMessageContent))

/**
 * Suspend function, which clears draft messages in all chats.
 *
 * @param excludeSecretChats If true, local draft messages in secret chats will not be cleared.
 */
suspend fun TelegramFlow.clearAllDraftMessages(excludeSecretChats: Boolean) =
    this.sendFunctionLaunch(TdApi.ClearAllDraftMessages(excludeSecretChats))

/**
 * Suspend function, which deletes messages.
 *
 * @param chatId Chat identifier.
 * @param messageIds Identifiers of the messages to be deleted.
 * @param revoke Pass true to try to delete messages for all chat members. Always true for
 * supergroups, channels and secret chats.
 */
suspend fun TelegramFlow.deleteMessages(
    chatId: Long,
    messageIds: LongArray?,
    revoke: Boolean
) = this.sendFunctionLaunch(TdApi.DeleteMessages(chatId, messageIds, revoke))

/**
 * Suspend function, which edits the caption of an inline message sent via a bot; for bots only.
 *
 * @param inlineMessageId Inline message identifier.
 * @param replyMarkup The new message reply markup.
 * @param caption New message content caption; 0-GetOption(&quot;message_caption_length_max&quot;)
 * characters.
 */
suspend fun TelegramFlow.editInlineMessageCaption(
    inlineMessageId: String?,
    replyMarkup: TdApi.ReplyMarkup?,
    caption: TdApi.FormattedText?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageCaption(inlineMessageId, replyMarkup, caption))

/**
 * Suspend function, which edits the content of a live location in an inline message sent via a bot;
 * for bots only.
 *
 * @param inlineMessageId Inline message identifier.
 * @param replyMarkup The new message reply markup.
 * @param location New location content of the message; may be null. Pass null to stop sharing the
 * live location.
 * @param heading The new direction in which the location moves, in degrees; 1-360. Pass 0 if unknown.
 * @param proximityAlertRadius The new maximum distance for proximity alerts, in meters (0-100000).
 * Pass 0 if the notification is disabled.
 */
suspend fun TelegramFlow.editInlineMessageLiveLocation(
    inlineMessageId: String?,
    replyMarkup: TdApi.ReplyMarkup?,
    location: TdApi.Location? = null,
    heading: Int,
    proximityAlertRadius: Int
) = this.sendFunctionLaunch(TdApi.EditInlineMessageLiveLocation(inlineMessageId, replyMarkup, location, heading, proximityAlertRadius))

/**
 * Suspend function, which edits the content of a message with an animation, an audio, a document, a
 * photo or a video in an inline message sent via a bot; for bots only.
 *
 * @param inlineMessageId Inline message identifier.
 * @param replyMarkup The new message reply markup; for bots only.
 * @param inputMessageContent New content of the message. Must be one of the following types:
 * InputMessageAnimation, InputMessageAudio, InputMessageDocument, InputMessagePhoto or
 * InputMessageVideo.
 */
suspend fun TelegramFlow.editInlineMessageMedia(
    inlineMessageId: String?,
    replyMarkup: TdApi.ReplyMarkup?,
    inputMessageContent: TdApi.InputMessageContent?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageMedia(inlineMessageId, replyMarkup, inputMessageContent))

/**
 * Suspend function, which edits the reply markup of an inline message sent via a bot; for bots
 * only.
 *
 * @param inlineMessageId Inline message identifier.
 * @param replyMarkup The new message reply markup.
 */
suspend fun TelegramFlow.editInlineMessageReplyMarkup(
    inlineMessageId: String?,
    replyMarkup: TdApi.ReplyMarkup?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageReplyMarkup(inlineMessageId, replyMarkup))

/**
 * Suspend function, which edits the text of an inline text or game message sent via a bot; for bots
 * only.
 *
 * @param inlineMessageId Inline message identifier.
 * @param replyMarkup The new message reply markup.
 * @param inputMessageContent New text content of the message. Should be of type InputMessageText.
 */
suspend fun TelegramFlow.editInlineMessageText(
    inlineMessageId: String?,
    replyMarkup: TdApi.ReplyMarkup?,
    inputMessageContent: TdApi.InputMessageContent?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageText(inlineMessageId, replyMarkup, inputMessageContent))

/**
 * Suspend function, which edits the message content caption. Returns the edited message after the
 * edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param replyMarkup The new message reply markup; for bots only.
 * @param caption New message content caption; 0-GetOption(&quot;message_caption_length_max&quot;)
 * characters.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageCaption(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup?,
    caption: TdApi.FormattedText?
): TdApi.Message = this.sendFunctionAsync(TdApi.EditMessageCaption(chatId, messageId, replyMarkup, caption))

/**
 * Suspend function, which edits the message content of a live location. Messages can be edited for
 * a limited period of time specified in the live location. Returns the edited message after the edit
 * is completed on the server side.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param replyMarkup The new message reply markup; for bots only.
 * @param location New location content of the message; may be null. Pass null to stop sharing the
 * live location.
 * @param heading The new direction in which the location moves, in degrees; 1-360. Pass 0 if unknown.
 * @param proximityAlertRadius The new maximum distance for proximity alerts, in meters (0-100000).
 * Pass 0 if the notification is disabled.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageLiveLocation(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup?,
    location: TdApi.Location? = null,
    heading: Int,
    proximityAlertRadius: Int
): TdApi.Message = this.sendFunctionAsync(TdApi.EditMessageLiveLocation(chatId, messageId, replyMarkup, location, heading, proximityAlertRadius))

/**
 * Suspend function, which edits the content of a message with an animation, an audio, a document, a
 * photo or a video. The media in the message can't be replaced if the message was set to
 * self-destruct. Media can't be replaced by self-destructing media. Media in an album can be edited
 * only to contain a photo or a video. Returns the edited message after the edit is completed on the
 * server side.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param replyMarkup The new message reply markup; for bots only.
 * @param inputMessageContent New content of the message. Must be one of the following types:
 * InputMessageAnimation, InputMessageAudio, InputMessageDocument, InputMessagePhoto or
 * InputMessageVideo.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageMedia(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup?,
    inputMessageContent: TdApi.InputMessageContent?
): TdApi.Message = this.sendFunctionAsync(TdApi.EditMessageMedia(chatId, messageId, replyMarkup, inputMessageContent))

/**
 * Suspend function, which edits the message reply markup; for bots only. Returns the edited message
 * after the edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param replyMarkup The new message reply markup.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageReplyMarkup(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup?
): TdApi.Message = this.sendFunctionAsync(TdApi.EditMessageReplyMarkup(chatId, messageId, replyMarkup))

/**
 * Suspend function, which edits the time when a scheduled message will be sent. Scheduling state of
 * all messages in the same album or forwarded together with the message will be also changed.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param schedulingState The new message scheduling state. Pass null to send the message
 * immediately.
 */
suspend fun TelegramFlow.editMessageSchedulingState(
    chatId: Long,
    messageId: Long,
    schedulingState: TdApi.MessageSchedulingState?
) = this.sendFunctionLaunch(TdApi.EditMessageSchedulingState(chatId, messageId, schedulingState))

/**
 * Suspend function, which edits the text of a message (or a text of a game message). Returns the
 * edited message after the edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.
 * @param messageId Identifier of the message.
 * @param replyMarkup The new message reply markup; for bots only.
 * @param inputMessageContent New text content of the message. Should be of type InputMessageText.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageText(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup?,
    inputMessageContent: TdApi.InputMessageContent?
): TdApi.Message = this.sendFunctionAsync(TdApi.EditMessageText(chatId, messageId, replyMarkup, inputMessageContent))

/**
 * Suspend function, which forwards previously sent messages. Returns the forwarded messages in the
 * same order as the message identifiers passed in messageIds. If a message can't be forwarded, null
 * will be returned instead of the message.
 *
 * @param chatId Identifier of the chat to which to forward messages.
 * @param fromChatId Identifier of the chat from which to forward messages.
 * @param messageIds Identifiers of the messages to forward.
 * @param options Options to be used to send the messages.
 * @param asAlbum True, if the messages should be grouped into an album after forwarding. For this
 * to work, no more than 10 messages may be forwarded, and all of them must be photo or video messages.
 * @param sendCopy True, if content of the messages needs to be copied without links to the original
 * messages. Always true if the messages are forwarded to a secret chat.
 * @param removeCaption True, if media captions of message copies needs to be removed. Ignored if
 * sendCopy is false.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.forwardMessages(
    chatId: Long,
    fromChatId: Long,
    messageIds: LongArray?,
    options: TdApi.MessageSendOptions?,
    asAlbum: Boolean,
    sendCopy: Boolean,
    removeCaption: Boolean
): TdApi.Messages = this.sendFunctionAsync(TdApi.ForwardMessages(chatId, fromChatId, messageIds, options, asAlbum, sendCopy, removeCaption))

/**
 * Suspend function, which returns all active live locations that should be updated by the client.
 * The list is persistent across application restarts only if the message database is used.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getActiveLiveLocationMessages(): TdApi.Messages =
    this.sendFunctionAsync(TdApi.GetActiveLiveLocationMessages())

/**
 * Suspend function, which returns information about a message.
 *
 * @param chatId Identifier of the chat the message belongs to.
 * @param messageId Identifier of the message to get.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.getMessage(chatId: Long, messageId: Long): TdApi.Message =
    this.sendFunctionAsync(TdApi.GetMessage(chatId, messageId))

/**
 * @param chatId Identifier of the chat to which the message belongs.
 * @param messageId Identifier of the message.
 * @param mediaTimestamp If not 0, timestamp from which the video/audio/video note/voice note playing must start, in seconds. The media can be in the message content or in its web page preview.
 * @param forAlbum Pass true to create a link for the whole media album.
 * @param forComment Pass true to create a link to the message as a channel post comment, or from a message thread.
*
* @return [TdApi.MessageLink] Contains an HTTP URL.
*/
suspend fun TelegramFlow.getMessageLink(
    chatId: Long,
    messageId: Long,
    mediaTimestamp: Int,
    forAlbum: Boolean,
    forComment: Boolean
): TdApi.MessageLink = this.sendFunctionAsync(TdApi.GetMessageLink(chatId, messageId, mediaTimestamp, forAlbum, forComment))

/**
 * Suspend function, which returns information about a public or private message link.
 *
 * @param url The message link in the format &quot;https://t.me/c/...&quot;, or
 * &quot;tg://privatepost?...&quot;, or &quot;https://t.me/username/...&quot;, or
 * &quot;tg://resolve?...&quot;.
 *
 * @return [TdApi.MessageLinkInfo] Contains information about a link to a message in a chat.
 */
suspend fun TelegramFlow.getMessageLinkInfo(url: String?): TdApi.MessageLinkInfo =
    this.sendFunctionAsync(TdApi.GetMessageLinkInfo(url))

/**
 * Suspend function, which returns information about a message, if it is available locally without
 * sending network request. This is an offline request.
 *
 * @param chatId Identifier of the chat the message belongs to.
 * @param messageId Identifier of the message to get.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.getMessageLocally(chatId: Long, messageId: Long): TdApi.Message =
    this.sendFunctionAsync(TdApi.GetMessageLocally(chatId, messageId))

/**
 * Suspend function, which returns information about messages. If a message is not found, returns
 * null on the corresponding position of the result.
 *
 * @param chatId Identifier of the chat the messages belong to.
 * @param messageIds Identifiers of the messages to get.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getMessages(chatId: Long, messageIds: LongArray?): TdApi.Messages =
    this.sendFunctionAsync(TdApi.GetMessages(chatId, messageIds))

/**
 * Suspend function, which returns information about a message that is replied by given message.
 *
 * @param chatId Identifier of the chat the message belongs to.
 * @param messageId Identifier of the message reply to which get.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.getRepliedMessage(chatId: Long, messageId: Long): TdApi.Message =
    this.sendFunctionAsync(TdApi.GetRepliedMessage(chatId, messageId))

/**
 * Suspend function, which informs TDLib that the message content has been opened (e.g., the user
 * has opened a photo, video, document, location or venue, or has listened to an audio file or voice
 * note message). An updateMessageContentOpened update will be generated if something has changed.
 *
 * @param chatId Chat identifier of the message.
 * @param messageId Identifier of the message with the opened content.
 */
suspend fun TelegramFlow.openMessageContent(chatId: Long, messageId: Long) =
    this.sendFunctionLaunch(TdApi.OpenMessageContent(chatId, messageId))

/**
 * Suspend function, which resends messages which failed to send. Can be called only for messages
 * for which messageSendingStateFailed.canRetry is true and after specified in
 * messageSendingStateFailed.retryAfter time passed. If a message is re-sent, the corresponding failed
 * to send message is deleted. Returns the sent messages in the same order as the message identifiers
 * passed in messageIds. If a message can't be re-sent, null will be returned instead of the message.
 *
 * @param chatId Identifier of the chat to send messages.
 * @param messageIds Identifiers of the messages to resend. Message identifiers must be in a
 * strictly increasing order.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.resendMessages(chatId: Long, messageIds: LongArray?): TdApi.Messages =
    this.sendFunctionAsync(TdApi.ResendMessages(chatId, messageIds))

/**
 * Suspend function, which searches for messages in all chats except secret chats. Returns the
 * results in reverse chronological order (i.e., in order of decreasing (date, chatId, messageId)). For
 * optimal performance the number of returned messages is chosen by the library.
 *
 * @param chatList Chat list in which to search messages; pass null to search in all chats regardless of their chat list. Only Main and Archive chat lists are supported.
 * @param query Query to search for.
 * @param offsetDate The date of the message starting from which the results need to be fetched. Use 0 or any date in the future to get results from the last message.
 * @param offsetChatId The chat identifier of the last found message, or 0 for the first request.
 * @param offsetMessageId The message identifier of the last found message, or 0 for the first request.
 * @param limit The maximum number of messages to be returned; up to 100. For optimal performance, the number of returned messages is chosen by TDLib and can be smaller than the specified limit.
 * @param filter Additional filter for messages to search; pass null to search for all messages. Filters searchMessagesFilterMention, searchMessagesFilterUnreadMention, searchMessagesFilterFailedToSend and searchMessagesFilterPinned are unsupported in this function.
 * @param minDate If not 0, the minimum date of the messages to return.
 * @param maxDate If not 0, the maximum date of the messages to return.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.searchMessages(
    chatList: TdApi.ChatList?,
    query: String?,
    offsetDate: Int,
    offsetChatId: Long,
    offsetMessageId: Long,
    limit: Int,
    filter: TdApi.SearchMessagesFilter,
    minDate: Int,
    maxDate: Int
): TdApi.Messages = this.sendFunctionAsync(TdApi.SearchMessages(chatList, query, offsetDate, offsetChatId, offsetMessageId, limit, filter, minDate, maxDate))

/**
 * Suspend function, which searches for messages in secret chats. Returns the results in reverse
 * chronological order. For optimal performance the number of returned messages is chosen by the
 * library.
 *
 * @param chatId Identifier of the chat in which to search. Specify 0 to search in all secret chats.
 *
 * @param query Query to search for. If empty, searchChatMessages should be used instead.
 * @param offset Offset of the first entry to return as received from the previous request;
 * use empty string to get first chunk of results.
 * @param limit The maximum number of messages to be returned; up to 100. Fewer messages may be
 * returned than specified by the limit, even if the end of the message history has not been reached.
 * @param filter A filter for the content of messages in the search results.
 *
 * @return [TdApi.FoundMessages] Contains a list of messages found by a search.
 */
suspend fun TelegramFlow.searchSecretMessages(
    chatId: Long,
    query: String?,
    offset: String,
    limit: Int,
    filter: TdApi.SearchMessagesFilter?
): TdApi.FoundMessages = this.sendFunctionAsync(TdApi.SearchSecretMessages(chatId, query, offset, limit, filter))

/**
 * Suspend function, which invites a bot to a chat (if it is not yet a member) and sends it the
 * /start command. Bots can't be invited to a private chat other than the chat with the bot. Bots can't
 * be invited to channels (although they can be added as admins) and secret chats. Returns the sent
 * message.
 *
 * @param botUserId Identifier of the bot.
 * @param chatId Identifier of the target chat.
 * @param parameter A hidden parameter sent to the bot for deep linking purposes
 * (https://core.telegram.org/bots#deep-linking).
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.sendBotStartMessage(
    botUserId: Long,
    chatId: Long,
    parameter: String?
): TdApi.Message = this.sendFunctionAsync(TdApi.SendBotStartMessage(botUserId, chatId, parameter))

/**
 * Suspend function, which sends a message. Returns the sent message.
 *
 * @param chatId Target chat.
 * @param messageThreadId If not 0, a message thread identifier in which the message will be sent.
 * @param replyToMessageId Identifier of the message to reply to or 0.
 * @param options Options to be used to send the message.
 * @param replyMarkup Markup for replying to the message; for bots only.
 * @param inputMessageContent The content of the message to be sent.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.sendMessage(
    chatId: Long,
    messageThreadId: Long,
    replyToMessageId: Long,
    options: TdApi.MessageSendOptions?,
    replyMarkup: TdApi.ReplyMarkup?,
    inputMessageContent: TdApi.InputMessageContent?
): TdApi.Message = this.sendFunctionAsync(TdApi.SendMessage(chatId, messageThreadId, replyToMessageId, options, replyMarkup, inputMessageContent))

/**
 * Suspend function, which sends messages grouped together into an album. Currently only photo and
 * video messages can be grouped into an album. Returns sent messages.
 *
 * @param chatId Target chat.
 * @param messageThreadId If not 0, a message thread identifier in which the messages will be sent.
 * @param replyToMessageId Identifier of a message to reply to or 0.
 * @param options Options to be used to send the messages.
 * @param inputMessageContents Contents of messages to be sent.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.sendMessageAlbum(
    chatId: Long,
    messageThreadId: Long,
    replyToMessageId: Long,
    options: TdApi.MessageSendOptions?,
    inputMessageContents: Array<TdApi.InputMessageContent>?
): TdApi.Messages = this.sendFunctionAsync(TdApi.SendMessageAlbum(chatId, messageThreadId, replyToMessageId, options, inputMessageContents))

/**
 * Suspend function, which toggles sender signatures messages sent in a channel; requires
 * canChangeInfo rights.
 *
 * @param supergroupId Identifier of the channel.
 * @param signMessages New value of signMessages.
 */
suspend fun TelegramFlow.toggleSupergroupSignMessages(supergroupId: Long, signMessages: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleSupergroupSignMessages(supergroupId, signMessages))

/**
 * Suspend function, which informs TDLib that messages are being viewed by the user. Many useful
 * activities depend on whether the messages are currently being viewed or not (e.g., marking messages
 * as read, incrementing a view counter, updating a view counter, removing deleted messages in
 * supergroups and channels).
 *
 * @param chatId Chat identifier.
 * @param messageThreadId If not 0, a message thread identifier in which the messages are being viewed.
 * @param messageIds The identifiers of the messages being viewed.
 * @param forceRead True, if messages in closed chats should be marked as read.
 */
suspend fun TelegramFlow.viewMessages(
    chatId: Long,
    messageThreadId: Long,
    messageIds: LongArray?,
    forceRead: Boolean
) = this.sendFunctionLaunch(TdApi.ViewMessages(chatId, messageThreadId, messageIds, forceRead))