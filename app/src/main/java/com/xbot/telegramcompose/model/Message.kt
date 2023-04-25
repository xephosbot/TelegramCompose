package com.xbot.telegramcompose.model

data class Message(
    val id: Long,
    val date: Long,
    val content: List<MessageContent>
)
