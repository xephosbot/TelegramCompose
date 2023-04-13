package com.xbot.telegramcompose.model

import org.drinkless.td.libcore.telegram.TdApi

data class Chat(
    val id: Long,
    val title: String,
    val photo: ProfilePhoto?,
    val isPinned: Boolean,
    val unreadCount: Int,
    val lastMessage: TdApi.Message?
)
