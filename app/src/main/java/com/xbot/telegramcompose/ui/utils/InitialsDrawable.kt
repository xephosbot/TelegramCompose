package com.xbot.telegramcompose.ui.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase


@Composable
fun rememberInitialsDrawable(text: String): Drawable {
    return remember(text) {
        val initials = text
            .split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }
            .toUpperCase(Locale.current)
        val color = calculateColorBase(text)

        InitialsDrawable(initials, color)
    }
}

private fun calculateColorBase(text: String): Int {
    val opacity = "#ff"
    val hexColor = String.format("$opacity%06X", 0xeeeeee and text.hashCode())
    return Color.parseColor(hexColor)
}

@Stable
class InitialsDrawable(
    val text: String,
    @ColorInt
    val color: Int
) : Drawable() {

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = this@InitialsDrawable.color
        style = Paint.Style.FILL
    }

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()

        val fontSize = width.coerceAtMost(height) / 3
        if (fontSize != textPaint.textSize) {
            textPaint.textSize = fontSize
        }

        canvas.apply {
            drawRect(bounds, backgroundPaint)
            drawText(text, width / 2, height / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        backgroundPaint.alpha = alpha
        textPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        backgroundPaint.colorFilter = colorFilter
        textPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}