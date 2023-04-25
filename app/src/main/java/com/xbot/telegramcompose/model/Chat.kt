package com.xbot.telegramcompose.model

data class Chat(
    val id: Long,
    val title: String,
    val photo: ProfilePhoto?,
    val isPinned: Boolean,
    val isMuted: Boolean,
    val unreadCount: Int,
    val lastMessage: Message?
)
