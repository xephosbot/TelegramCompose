package com.xbot.telegramcompose.data

import org.drinkless.tdlib.TdApi

data class OrderedChat (
    val chatId: Long,
    val position: TdApi.ChatPosition
) : Comparable<OrderedChat> {

    override fun compareTo(other: OrderedChat): Int {
        if (position.order != other.position.order) {
            return if (other.position.order < position.order) -1 else 1
        }
        return if (chatId != other.chatId) {
            if (other.chatId < chatId) -1 else 1
        } else 0
    }

    override fun equals(other: Any?): Boolean {
        val o = other as OrderedChat
        return this.chatId == o.chatId && this.position.order == o.position.order
    }

    override fun hashCode(): Int {
        var result = chatId.hashCode()
        result = 31 * result + position.hashCode()
        return result
    }
}
