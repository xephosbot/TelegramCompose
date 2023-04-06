package com.xbot.tdlibx.extensions

import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.*
import org.drinkless.td.libcore.telegram.TdApi


/**
 * Interface for access [TdApi.Chat] extension functions. Can be used alongside with other extension
 * interfaces of the package. Must contain [TelegramFlow] instance field to access its functionality
 */
@Suppress("UNUSED")
interface ChatKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which adds a new member to a chat. Members can't be added to private or
     * secret chats. Members will not be added until the chat state has been synchronized with the
     * server.
     *
     * @param userId Identifier of the user.
     * @param forwardLimit The number of earlier messages from the chat to be forwarded to the new
     * member; up to 100. Ignored for supergroups and channels.
     */
    suspend fun TdApi.Chat.addMember(userId: Long, forwardLimit: Int) =
        api.addChatMember(this.id, userId, forwardLimit)

    /**
     * Suspend function, which adds multiple new members to a chat. Currently this option is only
     * available for supergroups and channels. This option can't be used to join a chat. Members can't be
     * added to a channel if it has more than 200 members. Members will not be added until the chat state
     * has been synchronized with the server.
     *
     * @param userIds Identifiers of the users to be added to the chat.
     */
    suspend fun TdApi.Chat.addMembers(userIds: LongArray?) = api.addChatMembers(this.id, userIds)

    /**
     * Suspend function, which adds a local message to a chat. The message is persistent across
     * application restarts only if the message database is used. Returns the added message.
     *
     * @param senderId Identifier of the sender who will be shown as the sender of the message; may
     * be 0 for channel posts.
     * @param replyToMessageId Identifier of the message to reply to or 0.
     * @param disableNotification Pass true to disable notification for the message.
     * @param inputMessageContent The content of the message to be added.
     *
     * @return [TdApi.Message] Describes a message.
     */
    suspend fun TdApi.Chat.addLocalMessage(
        senderId: TdApi.MessageSender,
        replyToMessageId: Long,
        disableNotification: Boolean,
        inputMessageContent: TdApi.InputMessageContent?
    ) = api.addLocalMessage(this.id, senderId, replyToMessageId, disableNotification, inputMessageContent)

    /**
     * Suspend function, which adds a chat to the list of recently found chats. The chat is added to
     * the beginning of the list. If the chat is already in the list, it will be removed from the list
     * first.
     */
    suspend fun TdApi.Chat.addRecentlyFound() = api.addRecentlyFoundChat(this.id)

    /**
     * Suspend function, which checks whether a username can be set for a chat.
     *
     * @param username Username to be checked.
     *
     * @return [TdApi.CheckChatUsernameResult] This class is an abstract base class.
     */
    suspend fun TdApi.Chat.checkUsername(username: String?) =
        api.checkChatUsername(this.id, username)

    /**
     * Suspend function, which informs TDLib that the chat is closed by the user. Many useful
     * activities depend on the chat being opened or closed.
     */
    suspend fun TdApi.Chat.close() = api.closeChat(this.id)

    /**
     * Suspend function, which deletes all messages in the chat. Use Chat.canBeDeletedOnlyForSelf and
     * Chat.canBeDeletedForAllUsers fields to find whether and how the method can be applied to the chat.
     *
     * @param removeFromChatList Pass true if the chat should be removed from the chat list.
     * @param revoke Pass true to try to delete chat history for all users.
     */
    suspend fun TdApi.Chat.deleteHistory(removeFromChatList: Boolean, revoke: Boolean) =
        api.deleteChatHistory(this.id, removeFromChatList, revoke)

    /**
     * Suspend function, which deletes all messages sent by the specified user to a chat. Supported
     * only for supergroups; requires canDeleteMessages administrator privileges.
     *
     * @param senderId Identifier of the sender of messages to delete.
     */
    suspend fun TdApi.Chat.deleteChatMessagesBySender(senderId: TdApi.MessageSender) =
        api.deleteChatMessagesBySender(this.id, senderId)

    /**
     * Suspend function, which deletes the default reply markup from a chat. Must be called after a
     * one-time keyboard or a ForceReply reply markup has been used. UpdateChatReplyMarkup will be sent
     * if the reply markup will be changed.
     *
     * @param messageId The message identifier of the used keyboard.
     */
    suspend fun TdApi.Chat.deleteReplyMarkup(messageId: Long) =
        api.deleteChatReplyMarkup(this.id, messageId)

    /**
     * Suspend function, which deletes messages.
     *
     * @param messageIds Identifiers of the messages to be deleted.
     * @param revoke Pass true to try to delete messages for all chat members. Always true for
     * supergroups, channels and secret chats.
     */
    suspend fun TdApi.Chat.deleteMessages(messageIds: LongArray?, revoke: Boolean) =
        api.deleteMessages(this.id, messageIds, revoke)

    /**
     * Suspend function, which edits the message content caption. Returns the edited message after the
     * edit is completed on the server side.
     *
     * @param messageId Identifier of the message.
     * @param replyMarkup The new message reply markup; for bots only.
     * @param caption New message content caption; 0-GetOption(&quot;message_caption_length_max&quot;)
     * characters.
     *
     * @return [TdApi.Message] Describes a message.
     */
    suspend fun TdApi.Chat.editMessageCaption(
        messageId: Long,
        replyMarkup: TdApi.ReplyMarkup?,
        caption: TdApi.FormattedText?
    ) = api.editMessageCaption(this.id, messageId, replyMarkup, caption)

    /**
     * Suspend function, which edits the message content of a live location. Messages can be edited
     * for a limited period of time specified in the live location. Returns the edited message after the
     * edit is completed on the server side.
     *
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
    suspend fun TdApi.Chat.editMessageLiveLocation(
        messageId: Long,
        replyMarkup: TdApi.ReplyMarkup?,
        location: TdApi.Location? = null,
        heading: Int,
        proximityAlertRadius: Int
    ) = api.editMessageLiveLocation(this.id, messageId, replyMarkup, location, heading, proximityAlertRadius)

    /**
     * Suspend function, which edits the content of a message with an animation, an audio, a document,
     * a photo or a video. The media in the message can't be replaced if the message was set to
     * self-destruct. Media can't be replaced by self-destructing media. Media in an album can be edited
     * only to contain a photo or a video. Returns the edited message after the edit is completed on the
     * server side.
     *
     * @param messageId Identifier of the message.
     * @param replyMarkup The new message reply markup; for bots only.
     * @param inputMessageContent New content of the message. Must be one of the following types:
     * InputMessageAnimation, InputMessageAudio, InputMessageDocument, InputMessagePhoto or
     * InputMessageVideo.
     *
     * @return [TdApi.Message] Describes a message.
     */
    suspend fun TdApi.Chat.editMessageMedia(
        messageId: Long,
        replyMarkup: TdApi.ReplyMarkup?,
        inputMessageContent: TdApi.InputMessageContent?
    ) = api.editMessageMedia(this.id, messageId, replyMarkup, inputMessageContent)
}