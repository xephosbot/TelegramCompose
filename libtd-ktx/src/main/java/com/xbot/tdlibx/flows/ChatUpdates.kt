@file:Suppress("UNUSED")

package com.xbot.tdlibx.flows

import com.xbot.tdlibx.core.TelegramFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.drinkless.td.libcore.telegram.TdApi

/**
 * emits [TdApi.Chat] if a new chat has been loaded/created. This update is guaranteed to come before the
 * chat identifier is returned to the client. The chat field changes will be reported through separate
 * updates.
 */
fun TelegramFlow.newChatFlow(): Flow<TdApi.Chat> = this.getUpdatesFlowOfType<TdApi.UpdateNewChat>()
    .mapNotNull { it.chat }

/**
 * emits [TdApi.UpdateChatPosition] if the position of a chat was changed.
 */
fun TelegramFlow.chatPositionFlow() = this.getUpdatesFlowOfType<TdApi.UpdateChatPosition>()

/**
 * emits [TdApi.UpdateChatTitle] if the title of a chat was changed.
 */
fun TelegramFlow.chatTitleFlow(): Flow<TdApi.UpdateChatTitle> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatPhoto] if a chat photo was changed.
 */
fun TelegramFlow.chatPhotoFlow(): Flow<TdApi.UpdateChatPhoto> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatPermissions] if chat permissions was changed.
 */
fun TelegramFlow.chatPermissionsFlow(): Flow<TdApi.UpdateChatPermissions> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatLastMessage] if the last message of a chat was changed. If lastMessage is null,
 * then the last message in the chat became unknown. Some new unknown messages might be added to the
 * chat in this case.
 */
fun TelegramFlow.chatLastMessageFlow(): Flow<TdApi.UpdateChatLastMessage> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatIsMarkedAsUnread] if a chat was marked as unread or was read.
 */
fun TelegramFlow.chatIsMarkedAsUnreadFlow(): Flow<TdApi.UpdateChatIsMarkedAsUnread> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatHasScheduledMessages] if a chat's hasScheduledMessages field has changed.
 */
fun TelegramFlow.chatHasScheduledMessagesFlow(): Flow<TdApi.UpdateChatHasScheduledMessages> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatDefaultDisableNotification] if the value of the default disableNotification
 * parameter, used when a message is sent to the chat, was changed.
 */
fun TelegramFlow.chatDefaultDisableNotificationFlow(): Flow<TdApi.UpdateChatDefaultDisableNotification> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatReadInbox] if incoming messages were read or number of unread messages has been
 * changed.
 */
fun TelegramFlow.chatReadInboxFlow(): Flow<TdApi.UpdateChatReadInbox> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatReadOutbox] if outgoing messages were read.
 */
fun TelegramFlow.chatReadOutboxFlow(): Flow<TdApi.UpdateChatReadOutbox> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatUnreadMentionCount] if the chat unreadMentionCount has changed.
 */
fun TelegramFlow.chatUnreadMentionCountFlow(): Flow<TdApi.UpdateChatUnreadMentionCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatNotificationSettings] if notification settings for a chat were changed.
 */
fun TelegramFlow.chatNotificationSettingsFlow(): Flow<TdApi.UpdateChatNotificationSettings> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatActionBar] if the chat action bar was changed.
 */
fun TelegramFlow.chatActionBarFlow(): Flow<TdApi.UpdateChatActionBar> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatReplyMarkup] if the default chat reply markup was changed. Can occur because new
 * messages with reply markup were received or because an old reply markup was hidden by the user.
 */
fun TelegramFlow.chatReplyMarkupFlow(): Flow<TdApi.UpdateChatReplyMarkup> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatDraftMessage] if a chat draft has changed. Be aware that the update may come in
 * the currently opened chat but with old content of the draft. If the user has changed the content of
 * the draft, this update shouldn't be applied.
 */
fun TelegramFlow.chatDraftMessageFlow(): Flow<TdApi.UpdateChatDraftMessage> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatOnlineMemberCount] if the number of online group members has changed. This
 * update with non-zero count is sent only for currently opened chats. There is no guarantee that it
 * will be sent just after the count has changed.
 */
fun TelegramFlow.chatOnlineMemberCountFlow(): Flow<TdApi.UpdateChatOnlineMemberCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [TdApi.UpdateChatAction] if user activity in the chat has changed.
 */
fun TelegramFlow.chatActionFlow(): Flow<TdApi.UpdateChatAction> = this.getUpdatesFlowOfType()

/**
 * emits [TdApi.SecretChat] if some data of a secret chat has changed. This update is guaranteed to come
 * before the secret chat identifier is returned to the client.
 */
fun TelegramFlow.secretChatFlow(): Flow<TdApi.SecretChat> = this.getUpdatesFlowOfType<TdApi.UpdateSecretChat>()
    .mapNotNull { it.secretChat }

/**
 * emits [TdApi.UpdateUnreadChatCount] if number of unread chats, i.e. with unread messages or marked as
 * unread, has changed. This update is sent only if the message database is used.
 */
fun TelegramFlow.unreadChatCountFlow(): Flow<TdApi.UpdateUnreadChatCount> = this.getUpdatesFlowOfType()