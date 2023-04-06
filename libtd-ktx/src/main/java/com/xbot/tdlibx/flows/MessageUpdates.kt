@file:Suppress("UNUSED")

package com.xbot.tdlibx.flows

import com.xbot.tdlibx.core.TelegramFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.td.libcore.telegram.TdApi

/**
 * emits [TdApi.Message] if a new message was received; can also be an outgoing message.
 */
fun TelegramFlow.newMessageFlow(): Flow<TdApi.Message> = this.getUpdatesFlowOfType<TdApi.UpdateNewMessage>()
    .mapNotNull { it.message }

/**
 * emits [TdApi.UpdateMessageSendAcknowledged] if a request to send a message has reached the Telegram
 * server. This doesn't mean that the message will be sent successfully or even that the send message
 * request will be processed. This update will be sent only if the option &quot;use_quick_ack&quot; is
 * set to true. This update may be sent multiple times for the same message.
 */
fun TelegramFlow.messageSendAcknowledgedFlow(): Flow<TdApi.UpdateMessageSendAcknowledged> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageSendSucceeded] if a message has been successfully sent.
 */
fun TelegramFlow.messageSendSucceededFlow(): Flow<TdApi.UpdateMessageSendSucceeded> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageSendFailed] if a message failed to send. Be aware that some messages being
 * sent can be irrecoverably deleted, in which case updateDeleteMessages will be received instead of
 * this update.
 */
fun TelegramFlow.messageSendFailedFlow(): Flow<TdApi.UpdateMessageSendFailed> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageContent] if the message content has changed.
 */
fun TelegramFlow.messageContentFlow(): Flow<TdApi.UpdateMessageContent> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageEdited] if a message was edited. Changes in the message content will come in
 * a separate updateMessageContent.
 */
fun TelegramFlow.messageEditedFlow(): Flow<TdApi.UpdateMessageEdited> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageContentOpened] if the message content was opened. Updates voice note messages
 * to &quot;listened&quot;, video note messages to &quot;viewed&quot; and starts the TTL timer for
 * self-destructing messages.
 */
fun TelegramFlow.messageContentOpenedFlow(): Flow<TdApi.UpdateMessageContentOpened> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageMentionRead] if a message with an unread mention was read.
 */
fun TelegramFlow.messageMentionReadFlow(): Flow<TdApi.UpdateMessageMentionRead> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateMessageLiveLocationViewed] if a message with a live location was viewed. When the
 * update is received, the client is supposed to update the live location.
 */
fun TelegramFlow.messageLiveLocationViewedFlow(): Flow<TdApi.UpdateMessageLiveLocationViewed> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateDeleteMessages] if some messages were deleted.
 */
fun TelegramFlow.deleteMessagesFlow(): Flow<TdApi.UpdateDeleteMessages> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateUnreadMessageCount] if number of unread messages in a chat list has changed. This
 * update is sent only if the message database is used.
 */
fun TelegramFlow.unreadMessageCountFlow(): Flow<TdApi.UpdateUnreadMessageCount> =
    this.getUpdatesFlowOfType()