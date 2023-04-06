@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi


/**
 * Suspend function, which adds a new member to a chat. Members can't be added to private or secret
 * chats. Members will not be added until the chat state has been synchronized with the server.
 *
 * @param chatId Chat identifier.
 * @param userId Identifier of the user.
 * @param forwardLimit The number of earlier messages from the chat to be forwarded to the new
 * member; up to 100. Ignored for supergroups and channels.
 */
suspend fun TelegramFlow.addChatMember(
    chatId: Long,
    userId: Long,
    forwardLimit: Int
) = this.sendFunctionLaunch(TdApi.AddChatMember(chatId, userId, forwardLimit))

/**
 * Suspend function, which adds multiple new members to a chat. Currently this option is only
 * available for supergroups and channels. This option can't be used to join a chat. Members can't be
 * added to a channel if it has more than 200 members. Members will not be added until the chat state
 * has been synchronized with the server.
 *
 * @param chatId Chat identifier.
 * @param userIds Identifiers of the users to be added to the chat.
 */
suspend fun TelegramFlow.addChatMembers(chatId: Long, userIds: LongArray?) =
    this.sendFunctionLaunch(TdApi.AddChatMembers(chatId, userIds))

/**
 * Suspend function, which adds a chat to the list of recently found chats. The chat is added to the
 * beginning of the list. If the chat is already in the list, it will be removed from the list first.
 *
 * @param chatId Identifier of the chat to add.
 */
suspend fun TelegramFlow.addRecentlyFoundChat(chatId: Long) =
    this.sendFunctionLaunch(TdApi.AddRecentlyFoundChat(chatId))

/**
 * Suspend function, which checks the validity of an invite link for a chat and returns information
 * about the corresponding chat.
 *
 * @param inviteLink Invite link to be checked; should begin with
 * &quot;https://t.me/joinchat/&quot;, &quot;https://telegram.me/joinchat/&quot;, or
 * &quot;https://telegram.dog/joinchat/&quot;.
 *
 * @return [TdApi.ChatInviteLinkInfo] Contains information about a chat invite link.
 */
suspend fun TelegramFlow.checkChatInviteLink(inviteLink: String?): TdApi.ChatInviteLinkInfo =
    this.sendFunctionAsync(TdApi.CheckChatInviteLink(inviteLink))

/**
 * Suspend function, which checks whether a username can be set for a chat.
 *
 * @param chatId Chat identifier; should be identifier of a supergroup chat, or a channel chat, or a
 * private chat with self, or zero if chat is being created.
 * @param username Username to be checked.
 *
 * @return [TdApi.CheckChatUsernameResult] This class is an abstract base class.
 */
suspend fun TelegramFlow.checkChatUsername(chatId: Long, username: String?): TdApi.CheckChatUsernameResult =
    this.sendFunctionAsync(TdApi.CheckChatUsername(chatId, username))

/**
 * Suspend function, which checks whether the maximum number of owned public chats has been reached.
 * Returns corresponding error if the limit was reached.
 *
 * @param type Type of the public chats, for which to check the limit.
 */
suspend fun TelegramFlow.checkCreatedPublicChatsLimit(type: TdApi.PublicChatType?) =
    this.sendFunctionLaunch(TdApi.CheckCreatedPublicChatsLimit(type))

/**
 * Suspend function, which clears the list of recently found chats.
 */
suspend fun TelegramFlow.clearRecentlyFoundChats() =
    this.sendFunctionLaunch(TdApi.ClearRecentlyFoundChats())

/**
 * Suspend function, which informs TDLib that the chat is closed by the user. Many useful activities
 * depend on the chat being opened or closed.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.closeChat(chatId: Long) = this.sendFunctionLaunch(TdApi.CloseChat(chatId))

/**
 * Suspend function, which closes a secret chat, effectively transferring its state to
 * secretChatStateClosed.
 *
 * @param secretChatId Secret chat identifier.
 */
suspend fun TelegramFlow.closeSecretChat(secretChatId: Int) =
    this.sendFunctionLaunch(TdApi.CloseSecretChat(secretChatId))

/**
 * Suspend function, which returns an existing chat corresponding to a known basic group.
 *
 * @param basicGroupId Basic group identifier.
 * @param force If true, the chat will be created without network request. In this case all
 * information about the chat except its type, title and photo can be incorrect.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createBasicGroupChat(basicGroupId: Long, force: Boolean): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreateBasicGroupChat(basicGroupId, force))

/**
 * Suspend function, which creates a new basic group and sends a corresponding
 * messageBasicGroupChatCreate. Returns the newly created chat.
 *
 * @param userIds Identifiers of users to be added to the basic group.
 * @param title Title of the new basic group; 1-128 characters.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createNewBasicGroupChat(userIds: LongArray?, title: String?): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreateNewBasicGroupChat(userIds, title))

/**
 * Suspend function, which creates a new secret chat. Returns the newly created chat.
 *
 * @param userId Identifier of the target user.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createNewSecretChat(userId: Long): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreateNewSecretChat(userId))

/**
 * Suspend function, which creates a new supergroup or channel and sends a corresponding
 * messageSupergroupChatCreate. Returns the newly created chat.
 *
 * @param title Title of the new chat; 1-128 characters.
 * @param isChannel True, if a channel chat should be created.
 * @param description Chat description; 0-255 characters.
 * @param location Chat location if a location-based supergroup is being created.
 * @param forImport True, if the supergroup is created for importing messages using importMessage.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createNewSupergroupChat(
    title: String?,
    isChannel: Boolean,
    description: String?,
    location: TdApi.ChatLocation?,
    forImport: Boolean
): TdApi.Chat = this.sendFunctionAsync(TdApi.CreateNewSupergroupChat(title, isChannel, description, location, forImport))

/**
 * Suspend function, which returns an existing chat corresponding to a given user.
 *
 * @param userId User identifier.
 * @param force If true, the chat will be created without network request. In this case all
 * information about the chat except its type, title and photo can be incorrect.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createPrivateChat(userId: Long, force: Boolean): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreatePrivateChat(userId, force))

/**
 * Suspend function, which returns an existing chat corresponding to a known secret chat.
 *
 * @param secretChatId Secret chat identifier.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createSecretChat(secretChatId: Int): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreateSecretChat(secretChatId))

/**
 * Suspend function, which returns an existing chat corresponding to a known supergroup or channel.
 *
 * @param supergroupId Supergroup or channel identifier.
 * @param force If true, the chat will be created without network request. In this case all
 * information about the chat except its type, title and photo can be incorrect.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.createSupergroupChat(supergroupId: Long, force: Boolean): TdApi.Chat =
    this.sendFunctionAsync(TdApi.CreateSupergroupChat(supergroupId, force))

/**
 * Suspend function, which deletes all messages in the chat. Use Chat.canBeDeletedOnlyForSelf and
 * Chat.canBeDeletedForAllUsers fields to find whether and how the method can be applied to the chat.
 *
 * @param chatId Chat identifier.
 * @param removeFromChatList Pass true if the chat should be removed from the chat list.
 * @param revoke Pass true to try to delete chat history for all users.
 */
suspend fun TelegramFlow.deleteChatHistory(
    chatId: Long,
    removeFromChatList: Boolean,
    revoke: Boolean
) = this.sendFunctionLaunch(TdApi.DeleteChatHistory(chatId, removeFromChatList, revoke))

/**
 * Suspend function, which deletes all messages sent by the specified user to a chat. Supported only
 * for supergroups; requires canDeleteMessages administrator privileges.
 *
 * @param chatId Chat identifier.
 * @param senderId Identifier of the sender of messages to delete.
 */
suspend fun TelegramFlow.deleteChatMessagesBySender(chatId: Long, senderId: TdApi.MessageSender) =
    this.sendFunctionLaunch(TdApi.DeleteChatMessagesBySender(chatId, senderId))

/**
 * Suspend function, which deletes the default reply markup from a chat. Must be called after a
 * one-time keyboard or a ForceReply reply markup has been used. UpdateChatReplyMarkup will be sent if
 * the reply markup will be changed.
 *
 * @param chatId Chat identifier.
 * @param messageId The message identifier of the used keyboard.
 */
suspend fun TelegramFlow.deleteChatReplyMarkup(chatId: Long, messageId: Long) =
    this.sendFunctionLaunch(TdApi.DeleteChatReplyMarkup(chatId, messageId))

/**
 * Suspend function, which generates a new invite link for a chat; the previously generated link is
 * revoked. Available for basic groups, supergroups, and channels. Requires administrator privileges
 * and canInviteUsers right.
 *
 * @param chatId Chat identifier.
 *
 * @return [TdApi.ChatInviteLink] Contains a chat invite link.
 */
suspend fun TelegramFlow.replacePrimaryChatInviteLink(chatId: Long): TdApi.ChatInviteLink =
    this.sendFunctionAsync(TdApi.ReplacePrimaryChatInviteLink(chatId))

/**
 * Suspend function, which returns information about a chat by its identifier, this is an offline
 * request if the current user is not a bot.
 *
 * @param chatId Chat identifier.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.getChat(chatId: Long): TdApi.Chat = this.sendFunctionAsync(TdApi.GetChat(chatId))

/**
 * Suspend function, which returns a list of administrators of the chat with their custom titles.
 *
 * @param chatId Chat identifier.
 *
 * @return [TdApi.ChatAdministrators] Represents a list of chat administrators.
 */
suspend fun TelegramFlow.getChatAdministrators(chatId: Long): TdApi.ChatAdministrators =
    this.sendFunctionAsync(TdApi.GetChatAdministrators(chatId))

/**
 * Suspend function, which returns messages in a chat. The messages are returned in a reverse
 * chronological order (i.e., in order of decreasing messageId). For optimal performance the number of
 * returned messages is chosen by the library. This is an offline request if onlyLocal is true.
 *
 * @param chatId Chat identifier.
 * @param fromMessageId Identifier of the message starting from which history must be fetched; use 0
 * to get results from the last message.
 * @param offset Specify 0 to get results from exactly the fromMessageId or a negative offset up to
 * 99 to get additionally some newer messages.
 * @param limit The maximum number of messages to be returned; must be positive and can't be greater
 * than 100. If the offset is negative, the limit must be greater or equal to -offset. Fewer messages
 * may be returned than specified by the limit, even if the end of the message history has not been
 * reached.
 * @param onlyLocal If true, returns only messages that are available locally without sending
 * network requests.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getChatHistory(
    chatId: Long,
    fromMessageId: Long,
    offset: Int,
    limit: Int,
    onlyLocal: Boolean
): TdApi.Messages = this.sendFunctionAsync(TdApi.GetChatHistory(chatId, fromMessageId, offset, limit, onlyLocal))

/**
 * Suspend function, which returns information about a single member of a chat.
 *
 * @param chatId Chat identifier.
 * @param memberId Member identifier.
 *
 * @return [TdApi.ChatMember] A user with information about joining/leaving a chat.
 */
suspend fun TelegramFlow.getChatMember(chatId: Long, memberId: TdApi.MessageSender): TdApi.ChatMember =
    this.sendFunctionAsync(TdApi.GetChatMember(chatId, memberId))

/**
 * Suspend function, which returns the last message sent in a chat no later than the specified date.
 *
 * @param chatId Chat identifier.
 * @param date Point in time (Unix timestamp) relative to which to search for messages.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.getChatMessageByDate(chatId: Long, date: Int): TdApi.Message =
    this.sendFunctionAsync(TdApi.GetChatMessageByDate(chatId, date))

/**
 * Suspend function, which returns approximate number of messages of the specified type in the chat.
 *
 * @param chatId Identifier of the chat in which to count messages.
 * @param filter Filter for message content; searchMessagesFilterEmpty is unsupported in this
 * function.
 * @param returnLocal If true, returns count that is available locally without sending network
 * requests, returning -1 if the number of messages is unknown.
 *
 * @return [TdApi.Count] Contains a counter.
 */
suspend fun TelegramFlow.getChatMessageCount(
    chatId: Long,
    filter: TdApi.SearchMessagesFilter?,
    returnLocal: Boolean
): TdApi.Count = this.sendFunctionAsync(TdApi.GetChatMessageCount(chatId, filter, returnLocal))

/**
 * Suspend function, which returns list of chats with non-default notification settings.
 *
 * @param scope If specified, only chats from the specified scope will be returned.
 * @param compareSound If true, also chats with non-default sound will be returned.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.getChatNotificationSettingsExceptions(
    scope: TdApi.NotificationSettingsScope?,
    compareSound: Boolean
): TdApi.Chats = this.sendFunctionAsync(TdApi.GetChatNotificationSettingsExceptions(scope, compareSound))

/**
 * Suspend function, which returns information about a pinned chat message.
 *
 * @param chatId Identifier of the chat the message belongs to.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.getChatPinnedMessage(chatId: Long): TdApi.Message =
    this.sendFunctionAsync(TdApi.GetChatPinnedMessage(chatId))

/**
 * Suspend function, which returns all scheduled messages in a chat. The messages are returned in a
 * reverse chronological order (i.e., in order of decreasing messageId).
 *
 * @param chatId Chat identifier.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getChatScheduledMessages(chatId: Long): TdApi.Messages =
    this.sendFunctionAsync(TdApi.GetChatScheduledMessages(chatId))

/**
 * Suspend function, which returns an ordered list of chats in a chat list. Chats are sorted by the
 * pair (order, chatId) in decreasing order.
 * For optimal performance the number of returned chats is chosen by the library.
 *
 * @param chatList The chat list in which to return chats.
 * @param limit The maximum number of chats to be returned. It is possible that fewer chats than the
 * limit are returned even if the end of the list is not reached.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
@Deprecated(
    message = "The deprecated getChats() method is recommended to use loadChats() instead",
    level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith(
        expression = "loadChats(chatList, limit)"
    )
)
suspend fun TelegramFlow.getChats(
    chatList: TdApi.ChatList?,
    limit: Int
): TdApi.Chats = this.sendFunctionAsync(TdApi.GetChats(chatList, limit))

/**
 * Suspend function, which returns an ordered list of chats in a chat list. Chats are sorted by the
 * pair (order, chatId) in decreasing order.
 * For optimal performance the number of returned chats is chosen by the library.
 *
 * @param chatList The chat list in which to return chats.
 * @param limit The maximum number of chats to be returned. It is possible that fewer chats than the
 * limit are returned even if the end of the list is not reached.
 */
suspend fun TelegramFlow.loadChats(
    chatList: TdApi.ChatList?,
    limit: Int
) = this.sendFunctionLaunch(TdApi.LoadChats(chatList, limit))

/** @param type Type of the public chats to return.
*
* @return [TdApi.Chats] Represents a list of chats.
*/
suspend fun TelegramFlow.getCreatedPublicChats(type: TdApi.PublicChatType?): TdApi.Chats =
    this.sendFunctionAsync(TdApi.GetCreatedPublicChats(type))

/**
 * Suspend function, which returns a list of recently inactive supergroups and channels. Can be used
 * when user reaches limit on the number of joined supergroups and channels and receives
 * CHANNELSTOOMUCH error.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.getInactiveSupergroupChats(): TdApi.Chats =
    this.sendFunctionAsync(TdApi.GetInactiveSupergroupChats())

/**
 * Suspend function, which returns information about a secret chat by its identifier. This is an
 * offline request.
 *
 * @param secretChatId Secret chat identifier.
 *
 * @return [TdApi.SecretChat] Represents a secret chat.
 */
suspend fun TelegramFlow.getSecretChat(secretChatId: Int): TdApi.SecretChat =
    this.sendFunctionAsync(TdApi.GetSecretChat(secretChatId))

/**
 * Suspend function, which returns a list of basic group and supergroup chats, which can be used as
 * a discussion group for a channel. Basic group chats need to be first upgraded to supergroups before
 * they can be set as a discussion group.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.getSuitableDiscussionChats(): TdApi.Chats =
    this.sendFunctionAsync(TdApi.GetSuitableDiscussionChats())

/**
 * Suspend function, which returns a list of frequently used chats. Supported only if the chat info
 * database is enabled.
 *
 * @param category Category of chats to be returned.
 * @param limit The maximum number of chats to be returned; up to 30.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.getTopChats(category: TdApi.TopChatCategory?, limit: Int): TdApi.Chats =
    this.sendFunctionAsync(TdApi.GetTopChats(category, limit))

/**
 * Suspend function, which adds current user as a new member to a chat. Private and secret chats
 * can't be joined using this method.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.joinChat(chatId: Long) = this.sendFunctionLaunch(TdApi.JoinChat(chatId))

/**
 * Suspend function, which uses an invite link to add the current user to the chat if possible. The
 * new member will not be added until the chat state has been synchronized with the server.
 *
 * @param inviteLink Invite link to import; should begin with &quot;https://t.me/joinchat/&quot;,
 * &quot;https://telegram.me/joinchat/&quot;, or &quot;https://telegram.dog/joinchat/&quot;.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.joinChatByInviteLink(inviteLink: String?): TdApi.Chat =
    this.sendFunctionAsync(TdApi.JoinChatByInviteLink(inviteLink))

/**
 * Suspend function, which removes current user from chat members. Private and secret chats can't be
 * left using this method.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.leaveChat(chatId: Long) = this.sendFunctionLaunch(TdApi.LeaveChat(chatId))

/**
 * Suspend function, which informs TDLib that the chat is opened by the user. Many useful activities
 * depend on the chat being opened or closed (e.g., in supergroups and channels all updates are
 * received only for opened chats).
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.openChat(chatId: Long) = this.sendFunctionLaunch(TdApi.OpenChat(chatId))

/**
 * Suspend function, which pins a message in a chat; requires canPinMessages rights.
 *
 * @param chatId Identifier of the chat.
 * @param messageId Identifier of the new pinned message.
 * @param disableNotification True, if there should be no notification about the pinned message.
 * @param onlyForSelf True, if the message needs to be pinned for one side only; private chats only.
 */
suspend fun TelegramFlow.pinChatMessage(
    chatId: Long,
    messageId: Long,
    disableNotification: Boolean,
    onlyForSelf: Boolean
) = this.sendFunctionLaunch(TdApi.PinChatMessage(chatId, messageId, disableNotification, onlyForSelf))

/**
 * Suspend function, which marks all mentions in a chat as read.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.readAllChatMentions(chatId: Long) =
    this.sendFunctionLaunch(TdApi.ReadAllChatMentions(chatId))

/**
 * Suspend function, which removes a chat action bar without any other action.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.removeChatActionBar(chatId: Long) =
    this.sendFunctionLaunch(TdApi.RemoveChatActionBar(chatId))

/**
 * Suspend function, which removes a chat from the list of recently found chats.
 *
 * @param chatId Identifier of the chat to be removed.
 */
suspend fun TelegramFlow.removeRecentlyFoundChat(chatId: Long) =
    this.sendFunctionLaunch(TdApi.RemoveRecentlyFoundChat(chatId))

/**
 * Suspend function, which removes a chat from the list of frequently used chats. Supported only if
 * the chat info database is enabled.
 *
 * @param category Category of frequently used chats.
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.removeTopChat(category: TdApi.TopChatCategory?, chatId: Long) =
    this.sendFunctionLaunch(TdApi.RemoveTopChat(category, chatId))

/**
 * Suspend function, which reports a chat to the Telegram moderators. Supported only for
 * supergroups, channels, or private chats with bots, since other chats can't be checked by moderators,
 * or when the report is done from the chat action bar.
 *
 * @param chatId Chat identifier.
 * @param reason The reason for reporting the chat.
 * @param messageIds Identifiers of reported messages, if any.
 * @param text Additional report details; 0-1024 characters.
 */
suspend fun TelegramFlow.reportChat(
    chatId: Long,
    messageIds: LongArray,
    reason: TdApi.ChatReportReason?,
    text: String?
) = this.sendFunctionLaunch(TdApi.ReportChat(chatId, messageIds, reason, text))

/**
 * Suspend function, which searches for a specified query in the first name, last name and username
 * of the members of a specified chat. Requires administrator rights in channels.
 *
 * @param chatId Chat identifier.
 * @param query Query to search for.
 * @param limit The maximum number of users to be returned.
 * @param filter The type of users to return. By default, chatMembersFilterMembers.
 *
 * @return [TdApi.ChatMembers] Contains a list of chat members.
 */
suspend fun TelegramFlow.searchChatMembers(
    chatId: Long,
    query: String?,
    limit: Int,
    filter: TdApi.ChatMembersFilter?
): TdApi.ChatMembers = this.sendFunctionAsync(TdApi.SearchChatMembers(chatId, query, limit, filter))

/**
 * Suspend function, which searches for messages with given words in the chat. Returns the results
 * in reverse chronological order, i.e. in order of decreasing messageId. Cannot be used in secret
 * chats with a non-empty query (searchSecretMessages should be used instead), or without an enabled
 * message database. For optimal performance the number of returned messages is chosen by the library.
 *
 * @param chatId Identifier of the chat in which to search messages.
 * @param query Query to search for.
 * @param senderId Identifier of the sender of messages to search for; pass null to search for messages
 * from any sender. Not supported in secret chats.
 * @param fromMessageId Identifier of the message starting from which history must be fetched; use 0
 * to get results from the last message.
 * @param offset Specify 0 to get results from exactly the fromMessageId or a negative offset to get
 * the specified message and some newer messages.
 * @param limit The maximum number of messages to be returned; must be positive and can't be greater
 * than 100. If the offset is negative, the limit must be greater than -offset. Fewer messages may be
 * returned than specified by the limit, even if the end of the message history has not been reached.
 * @param filter Filter for message content in the search results.
 * @param messageThreadId If not 0, only messages in the specified thread will be returned; supergroups only.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.searchChatMessages(
    chatId: Long,
    query: String?,
    senderId: TdApi.MessageSender,
    fromMessageId: Long,
    offset: Int,
    limit: Int,
    filter: TdApi.SearchMessagesFilter?,
    messageThreadId: Long
): TdApi.Messages = this.sendFunctionAsync(TdApi.SearchChatMessages(chatId, query, senderId,
    fromMessageId, offset, limit, filter, messageThreadId))

/**
 * Suspend function, which returns information about the recent locations of chat members that were
 * sent to the chat. Returns up to 1 location message per user.
 *
 * @param chatId Chat identifier.
 * @param limit The maximum number of messages to be returned.
 *
 * @return [TdApi.Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.searchChatRecentLocationMessages(chatId: Long, limit: Int): TdApi.Messages =
    this.sendFunctionAsync(TdApi.SearchChatRecentLocationMessages(chatId, limit))

/**
 * Suspend function, which searches for the specified query in the title and username of already
 * known chats, this is an offline request. Returns chats in the order seen in the chat list.
 *
 * @param query Query to search for. If the query is empty, returns up to 20 recently found chats.
 * @param limit The maximum number of chats to be returned.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.searchChats(query: String?, limit: Int): TdApi.Chats =
    this.sendFunctionAsync(TdApi.SearchChats(query, limit))

/**
 * Suspend function, which returns a list of users and location-based supergroups nearby. The list
 * of users nearby will be updated for 60 seconds after the request by the updates updateUsersNearby.
 * The request should be sent again every 25 seconds with adjusted location to not miss new chats.
 *
 * @param location Current user location.
 *
 * @return [TdApi.ChatsNearby] Represents a list of chats located nearby.
 */
suspend fun TelegramFlow.searchChatsNearby(location: TdApi.Location?): TdApi.ChatsNearby =
    this.sendFunctionAsync(TdApi.SearchChatsNearby(location))

/**
 * Suspend function, which searches for the specified query in the title and username of already
 * known chats via request to the server. Returns chats in the order seen in the chat list.
 *
 * @param query Query to search for.
 * @param limit The maximum number of chats to be returned.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.searchChatsOnServer(query: String?, limit: Int): TdApi.Chats =
    this.sendFunctionAsync(TdApi.SearchChatsOnServer(query, limit))

/**
 * Suspend function, which searches a public chat by its username. Currently only private chats,
 * supergroups and channels can be public. Returns the chat if found; otherwise an error is returned.
 *
 * @param username Username to be resolved.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.searchPublicChat(username: String?): TdApi.Chat =
    this.sendFunctionAsync(TdApi.SearchPublicChat(username))

/**
 * Suspend function, which searches public chats by looking for specified query in their username
 * and title. Currently only private chats, supergroups and channels can be public. Returns a
 * meaningful number of results. Returns nothing if the length of the searched username prefix is less
 * than 5. Excludes private chats with contacts and chats from the chat list from the results.
 *
 * @param query Query to search for.
 *
 * @return [TdApi.Chats] Represents a list of chats.
 */
suspend fun TelegramFlow.searchPublicChats(query: String?): TdApi.Chats =
    this.sendFunctionAsync(TdApi.SearchPublicChats(query))

/**
 * Suspend function, which sends a notification about user activity in a chat.
 *
 * @param chatId Chat identifier.
 * @param messageThreadId If not 0, a message thread identifier in which the action was performed.
 * @param action The action description.
 */
suspend fun TelegramFlow.sendChatAction(
    chatId: Long,
    messageThreadId: Long,
    action: TdApi.ChatAction?
) = this.sendFunctionLaunch(TdApi.SendChatAction(chatId, messageThreadId, action))

/**
 * Suspend function, which sends a notification about a screenshot taken in a chat. Supported only
 * in private and secret chats.
 *
 * @param chatId Chat identifier.
 */
suspend fun TelegramFlow.sendChatScreenshotTakenNotification(chatId: Long) =
    this.sendFunctionLaunch(TdApi.SendChatScreenshotTakenNotification(chatId))

/**
 * Suspend function, which changes the current TTL setting (sets a new self-destruct timer) in a
 * secret chat and sends the corresponding message.
 *
 * @param chatId Chat identifier.
 * @param ttl New TTL value, in seconds.
 *
 * @return [TdApi.Message] Describes a message.
 */
suspend fun TelegramFlow.setChatMessageTtl(chatId: Long, ttl: Int): TdApi.Message =
    this.sendFunctionAsync(TdApi.SetChatMessageTtl(chatId, ttl))

/**
 * Suspend function, which moves a chat to a different chat list. Current chat list of the chat must
 * ne non-null.
 *
 * @param chatId Chat identifier.
 * @param chatList New chat list of the chat.
 */
suspend fun TelegramFlow.addChatToList(chatId: Long, chatList: TdApi.ChatList?) =
    this.sendFunctionLaunch(TdApi.AddChatToList(chatId, chatList))

/**
 * Suspend function, which changes client data associated with a chat.
 *
 * @param chatId Chat identifier.
 * @param clientData New value of clientData.
 */
suspend fun TelegramFlow.setChatClientData(chatId: Long, clientData: String?) =
    this.sendFunctionLaunch(TdApi.SetChatClientData(chatId, clientData))

/**
 * Suspend function, which changes information about a chat. Available for basic groups,
 * supergroups, and channels. Requires canChangeInfo rights.
 *
 * @param chatId Identifier of the chat.
 * @param description New chat description; 0-255 characters.
 */
suspend fun TelegramFlow.setChatDescription(chatId: Long, description: String?) =
    this.sendFunctionLaunch(TdApi.SetChatDescription(chatId, description))

/**
 * Suspend function, which changes the discussion group of a channel chat; requires canChangeInfo
 * rights in the channel if it is specified.
 *
 * @param chatId Identifier of the channel chat. Pass 0 to remove a link from the supergroup passed
 * in the second argument to a linked channel chat (requires canPinMessages rights in the supergroup).
 *
 * @param discussionChatId Identifier of a new channel's discussion group. Use 0 to remove the
 * discussion group. Use the method getSuitableDiscussionChats to find all suitable groups. Basic group
 * chats needs to be first upgraded to supergroup chats. If new chat members don't have access to old
 * messages in the supergroup, then toggleSupergroupIsAllHistoryAvailable needs to be used first to
 * change that.
 */
suspend fun TelegramFlow.setChatDiscussionGroup(chatId: Long, discussionChatId: Long) =
    this.sendFunctionLaunch(TdApi.SetChatDiscussionGroup(chatId, discussionChatId))

/**
 * Suspend function, which changes the draft message in a chat.
 *
 * @param chatId Chat identifier.
 * @param draftMessage New draft message; may be null.
 */
suspend fun TelegramFlow.setChatDraftMessage(
    chatId: Long, messageThreadId: Long,
    draftMessage: TdApi.DraftMessage? = null
) = this.sendFunctionLaunch(TdApi.SetChatDraftMessage(chatId, messageThreadId, draftMessage))

/**
 * Suspend function, which changes the location of a chat. Available only for some location-based
 * supergroups, use supergroupFullInfo.canSetLocation to check whether the method is allowed to use.
 *
 * @param chatId Chat identifier.
 * @param location New location for the chat; must be valid and not null.
 */
suspend fun TelegramFlow.setChatLocation(chatId: Long, location: TdApi.ChatLocation?) =
    this.sendFunctionLaunch(TdApi.SetChatLocation(chatId, location))

/**
 * Suspend function, which changes the status of a chat member, needs appropriate privileges. This
 * function is currently not suitable for adding new members to the chat and transferring chat
 * ownership; instead, use addChatMember or transferChatOwnership. The chat member status will not be
 * changed until it has been synchronized with the server.
 *
 * @param chatId Chat identifier.
 * @param memberId Member identifier. Chats can be only banned and unbanned in supergroups and channels.
 * @param status The new status of the member in the chat.
 */
suspend fun TelegramFlow.setChatMemberStatus(
    chatId: Long,
    memberId: TdApi.MessageSender,
    status: TdApi.ChatMemberStatus?
) = this.sendFunctionLaunch(TdApi.SetChatMemberStatus(chatId, memberId, status))

/**
 * Suspend function, which changes the notification settings of a chat. Notification settings of a
 * chat with the current user (Saved Messages) can't be changed.
 *
 * @param chatId Chat identifier.
 * @param notificationSettings New notification settings for the chat. If the chat is muted for more
 * than 1 week, it is considered to be muted forever.
 */
suspend fun TelegramFlow.setChatNotificationSettings(
    chatId: Long,
    notificationSettings: TdApi.ChatNotificationSettings?
) = this.sendFunctionLaunch(TdApi.SetChatNotificationSettings(chatId, notificationSettings))

/**
 * Suspend function, which changes the chat members permissions. Supported only for basic groups and
 * supergroups. Requires canRestrictMembers administrator right.
 *
 * @param chatId Chat identifier.
 * @param permissions New non-administrator members permissions in the chat.
 */
suspend fun TelegramFlow.setChatPermissions(
    chatId: Long,
    permissions: TdApi.ChatPermissions?
) = this.sendFunctionLaunch(TdApi.SetChatPermissions(chatId, permissions))

/**
 * Suspend function, which changes the photo of a chat. Supported only for basic groups, supergroups
 * and channels. Requires canChangeInfo rights. The photo will not be changed before request to the
 * server has been completed.
 *
 * @param chatId Chat identifier.
 * @param photo New chat photo. You can use a zero InputFileId to delete the chat photo. Files that
 * are accessible only by HTTP URL are not acceptable.
 */
suspend fun TelegramFlow.setChatPhoto(
    chatId: Long,
    photo: TdApi.InputChatPhoto?
) = this.sendFunctionLaunch(TdApi.SetChatPhoto(chatId, photo))

/**
 * Suspend function, which changes the slow mode delay of a chat. Available only for supergroups;
 * requires canRestrictMembers rights.
 *
 * @param chatId Chat identifier.
 * @param slowModeDelay New slow mode delay for the chat; must be one of 0, 10, 30, 60, 300, 900,
 * 3600.
 */
suspend fun TelegramFlow.setChatSlowModeDelay(
    chatId: Long,
    slowModeDelay: Int
) = this.sendFunctionLaunch(TdApi.SetChatSlowModeDelay(chatId, slowModeDelay))

/**
 * Suspend function, which changes the chat title. Supported only for basic groups, supergroups and
 * channels. Requires canChangeInfo rights. The title will not be changed until the request to the
 * server has been completed.
 *
 * @param chatId Chat identifier.
 * @param title New title of the chat; 1-128 characters.
 */
suspend fun TelegramFlow.setChatTitle(chatId: Long, title: String?) =
    this.sendFunctionLaunch(TdApi.SetChatTitle(chatId, title))

/**
 * Suspend function, which changes the order of pinned chats.
 *
 * @param chatList Chat list in which to change the order of pinned chats.
 * @param chatIds The new list of pinned chats.
 */
suspend fun TelegramFlow.setPinnedChats(chatList: TdApi.ChatList?, chatIds: LongArray?) =
    this.sendFunctionLaunch(TdApi.SetPinnedChats(chatList, chatIds))

/**
 * Suspend function, which changes the value of the default disableNotification parameter, used when
 * a message is sent to a chat.
 *
 * @param chatId Chat identifier.
 * @param defaultDisableNotification New value of defaultDisableNotification.
 */
suspend fun TelegramFlow.toggleChatDefaultDisableNotification(
    chatId: Long,
    defaultDisableNotification: Boolean
) = this.sendFunctionLaunch(TdApi.ToggleChatDefaultDisableNotification(chatId, defaultDisableNotification))

/**
 * Suspend function, which changes the marked as unread state of a chat.
 *
 * @param chatId Chat identifier.
 * @param isMarkedAsUnread New value of isMarkedAsUnread.
 */
suspend fun TelegramFlow.toggleChatIsMarkedAsUnread(chatId: Long, isMarkedAsUnread: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleChatIsMarkedAsUnread(chatId, isMarkedAsUnread))

/**
 * Suspend function, which changes the pinned state of a chat. You can pin up to
 * GetOption(&quot;pinned_chat_count_max&quot;)/GetOption(&quot;pinned_archived_chat_count_max&quot;)
 * non-secret chats and the same number of secret chats in the main/archive chat list.
 *
 * @param chatList Chat list in which to change the pinned state of the chat.
 * @param chatId Chat identifier.
 * @param isPinned New value of isPinned.
 */
suspend fun TelegramFlow.toggleChatIsPinned(
    chatList: TdApi.ChatList,
    chatId: Long,
    isPinned: Boolean
) = this.sendFunctionLaunch(TdApi.ToggleChatIsPinned(chatList, chatId, isPinned))

/**
 * Suspend function, which changes the owner of a chat. The current user must be a current owner of
 * the chat. Use the method canTransferOwnership to check whether the ownership can be transferred from
 * the current session. Available only for supergroups and channel chats.
 *
 * @param chatId Chat identifier.
 * @param userId Identifier of the user to which transfer the ownership. The ownership can't be
 * transferred to a bot or to a deleted user.
 * @param password The password of the current user.
 */
suspend fun TelegramFlow.transferChatOwnership(
    chatId: Long,
    userId: Long,
    password: String?
) = this.sendFunctionLaunch(TdApi.TransferChatOwnership(chatId, userId, password))

/**
 * Suspend function, which removes the pinned message from a chat; requires canPinMessages rights in
 * the group or channel.
 *
 * @param chatId Identifier of the chat.
 */
suspend fun TelegramFlow.unpinChatMessage(chatId: Long, messageId: Long) =
    this.sendFunctionLaunch(TdApi.UnpinChatMessage(chatId, messageId))

/**
 * Suspend function, which creates a new supergroup from an existing basic group and sends a
 * corresponding messageChatUpgradeTo and messageChatUpgradeFrom; requires creator privileges.
 * Deactivates the original basic group.
 *
 * @param chatId Identifier of the chat to upgrade.
 *
 * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
 */
suspend fun TelegramFlow.upgradeBasicGroupChatToSupergroupChat(chatId: Long): TdApi.Chat =
    this.sendFunctionAsync(TdApi.UpgradeBasicGroupChatToSupergroupChat(chatId))