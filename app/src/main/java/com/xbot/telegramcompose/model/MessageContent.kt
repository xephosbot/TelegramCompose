package com.xbot.telegramcompose.model

import org.drinkless.tdlib.TdApi

data class MessageContent(
    val text: String,
    val thumbnail: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageContent

        if (text != other.text) return false
        if (thumbnail != null) {
            if (other.thumbnail == null) return false
            if (!thumbnail.contentEquals(other.thumbnail)) return false
        } else if (other.thumbnail != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + (thumbnail?.contentHashCode() ?: 0)
        return result
    }

    companion object {
        fun create(content: TdApi.MessageContent): MessageContent {
            val text: String
            val thumbnail: ByteArray?

            when (content.constructor) {
                TdApi.MessageText.CONSTRUCTOR -> {
                    (content as TdApi.MessageText).also {
                        text = it.text.text
                        thumbnail = null
                    }
                }
                TdApi.MessagePhoto.CONSTRUCTOR -> {
                    (content as TdApi.MessagePhoto).also {
                        text = it.caption.text
                        thumbnail = it.photo.minithumbnail?.data
                    }
                }
                TdApi.MessageVideo.CONSTRUCTOR -> {
                    (content as TdApi.MessageVideo).also {
                        text = it.caption.text
                        thumbnail = it.video.minithumbnail?.data
                    }
                }
                TdApi.MessageCall.CONSTRUCTOR -> {
                    (content as TdApi.MessageCall).also {
                        text = "Call message ${it.duration.toTime()}"
                        thumbnail = null
                    }
                }
                TdApi.MessageAudio.CONSTRUCTOR -> {
                    (content as TdApi.MessageAudio).also {
                        text = it.caption.text
                        thumbnail = null
                    }
                }
                TdApi.MessageSticker.CONSTRUCTOR -> {
                    (content as TdApi.MessageSticker).also {
                        text = it.sticker.emoji
                        thumbnail = null
                    }
                }
                TdApi.MessageAnimation.CONSTRUCTOR -> {
                    (content as TdApi.MessageAnimation).also {
                        text = it.caption.text
                        thumbnail = it.animation.minithumbnail?.data
                    }
                }
                TdApi.MessageLocation.CONSTRUCTOR -> {
                    (content as TdApi.MessageLocation).also {
                        text = "Location message"
                        thumbnail = null
                    }
                }
                TdApi.MessageVoiceNote.CONSTRUCTOR -> {
                    (content as TdApi.MessageVoiceNote).also {
                        text = "${it.caption.text} ${it.voiceNote.duration.toTime()}"
                        thumbnail = null
                    }
                }
                TdApi.MessageVideoNote.CONSTRUCTOR -> {
                    (content as TdApi.MessageVideoNote).also {
                        text = "Video note message"
                        thumbnail = it.videoNote.minithumbnail?.data
                    }
                }
                TdApi.MessageContactRegistered.CONSTRUCTOR -> {
                    (content as TdApi.MessageContactRegistered).also {
                        text = "Joined to Telegram!"
                        thumbnail = null
                    }
                }
                TdApi.MessageChatDeleteMember.CONSTRUCTOR -> {
                    (content as TdApi.MessageChatDeleteMember).also {
                        text = "${it.userId} left the chat"
                        thumbnail = null
                    }
                }
                else -> {
                    text = "Unsupported message"
                    thumbnail = null
                }
            }

            return MessageContent(
                text = text,
                thumbnail = thumbnail
            )
        }
    }
}

private fun Int.toTime(): String {
    val duration = this.toLong()
    val hours: Long = (duration / (60 * 60))
    val minutes = (duration % (60 * 60) / (60))
    val seconds = (duration % (60 * 60) % (60))
    return when {
        minutes == 0L && hours == 0L -> String.format("0:%02d", seconds)
        hours == 0L -> String.format("%02d:%02d", minutes, seconds)
        else -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
