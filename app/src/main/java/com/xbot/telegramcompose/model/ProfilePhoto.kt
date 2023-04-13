package com.xbot.telegramcompose.model

import org.drinkless.td.libcore.telegram.TdApi

data class ProfilePhoto(
    val thumbnail: ByteArray,
    val file: TdApi.File
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfilePhoto

        if (!thumbnail.contentEquals(other.thumbnail)) return false
        if (file != other.file) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thumbnail.contentHashCode()
        result = 31 * result + file.hashCode()
        return result
    }
}
