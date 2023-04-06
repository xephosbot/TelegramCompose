package com.xbot.tdlibx.extensions

import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.tdlibx.coroutines.*
import org.drinkless.td.libcore.telegram.TdApi


@Suppress("UNUSED")
interface UserKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which adds a new member to a chat. Members can't be added to private or
     * secret chats. Members will not be added until the chat state has been synchronized with the
     * server.
     *
     * @param chatId Chat identifier.
     * @param forwardLimit The number of earlier messages from the chat to be forwarded to the new
     * member; up to 100. Ignored for supergroups and channels.
     */
    suspend fun TdApi.User.addChatMember(chatId: Long, forwardLimit: Int) =
        api.addChatMember(chatId, this.id, forwardLimit)

    /**
     * Suspend function, which adds a message sender to the blacklist.
     *
     * @param isBlocked New value of isBlocked.
     */
    suspend fun TdApi.MessageSender.toggleIsBlocked(isBlocked: Boolean) =
        api.toggleMessageSenderIsBlocked(this, isBlocked)

    /**
     * Suspend function, which creates a new secret chat. Returns the newly created chat.
     *
     * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
     */
    suspend fun TdApi.User.createNewSecretChat() = api.createNewSecretChat(this.id)

    /**
     * Suspend function, which returns an existing chat corresponding to a given user.
     *
     * @param force If true, the chat will be created without network request. In this case all
     * information about the chat except its type, title and photo can be incorrect.
     *
     * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
     */
    suspend fun TdApi.User.createPrivateChat(force: Boolean) = api.createPrivateChat(this.id, force)

    /**
     * Suspend function, which deletes all messages sent by the specified user to a chat. Supported
     * only for supergroups; requires canDeleteMessages administrator privileges.
     *
     * @param chatId Chat identifier.
     */
    suspend fun TdApi.MessageSender.deleteChatMessagesBySender(chatId: Long) =
        api.deleteChatMessagesBySender(chatId, this)

    /**
     * Suspend function, which returns information about a single member of a chat.
     *
     * @param chatId Chat identifier.
     *
     * @return [TdApi.ChatMember] A user with information about joining/leaving a chat.
     */
    suspend fun TdApi.MessageSender.getChatMember(chatId: Long) =
        api.getChatMember(chatId, this)

    /**
     * Suspend function, which returns information about a user by their identifier. This is an
     * offline request if the current user is not a bot.
     *
     *
     * @return [TdApi.User] Represents a user.
     */
    suspend fun TdApi.User.get() = api.getUser(this.id)

    /**
     * Suspend function, which returns full information about a user by their identifier.
     *
     * @return [TdApi.UserFullInfo] Contains full information about a user (except the full list of
     * profile photos).
     */
    suspend fun TdApi.User.getFullInfo() = api.getUserFullInfo(this.id)

    /**
     * Suspend function, which returns the profile photos of a user. The result of this query may be
     * outdated: some photos might have been deleted already.
     *
     * @param offset The number of photos to skip; must be non-negative.
     * @param limit The maximum number of photos to be returned; up to 100.
     *
     * @return [TdApi.ChatPhotos] Contains part of the list of user photos.
     */
    suspend fun TdApi.User.getProfilePhotos(offset: Int, limit: Int) =
        api.getUserProfilePhotos(this.id, offset, limit)

    /**
     * Suspend function, which changes the status of a chat member, needs appropriate privileges. This
     * function is currently not suitable for adding new members to the chat and transferring chat
     * ownership; instead, use addChatMember or transferChatOwnership. The chat member status will not be
     * changed until it has been synchronized with the server.
     *
     * @param chatId Chat identifier.
     * @param status The new status of the member in the chat.
     */
    suspend fun TdApi.MessageSender.setChatMemberStatus(chatId: Long, status: TdApi.ChatMemberStatus?) =
        api.setChatMemberStatus(chatId, this, status)

    /**
     * Suspend function, which shares the phone number of the current user with a mutual contact.
     * Supposed to be called when the user clicks on chatActionBarSharePhoneNumber.
     */
    suspend fun TdApi.User.sharePhoneNumber() = api.sharePhoneNumber(this.id)

    /**
     * Suspend function, which changes the owner of a chat. The current user must be a current owner
     * of the chat. Use the method canTransferOwnership to check whether the ownership can be transferred
     * from the current session. Available only for supergroups and channel chats.
     *
     * @param chatId Chat identifier.
     * @param password The password of the current user.
     */
    suspend fun TdApi.User.transferChatOwnership(chatId: Long, password: String?) =
        api.transferChatOwnership(chatId, this.id, password)
}