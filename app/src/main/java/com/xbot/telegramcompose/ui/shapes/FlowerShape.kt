package com.xbot.telegramcompose.ui.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class FlowerShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = createPath(size)
        return Outline.Generic(path)
    }

    fun createPath(size: Size): Path = Path().apply {
        reset()
        moveTo(size.width * .9643f, size.height * .5f)
        cubicTo(
            size.width * 1.0451f, size.height * .6505f,
            size.width * .9841f, size.height * .7868f,
            size.width * .8283f, size.height * .8283f
        )
        cubicTo(
            size.width * .7868f, size.height * .9841f,
            size.width * .6505f, size.height * 1.0451f,
            size.width * .5f, size.height * .9643f
        )
        cubicTo(
            size.width * .3495f, size.height * 1.0451f,
            size.width * .2132f, size.height * .9841f,
            size.width * .1717f, size.height * .8283f
        )
        cubicTo(
            size.width * .0159f, size.height * .7868f,
            size.width * -.0451f, size.height * .6505f,
            size.width * .0357f, size.height * .5f
        )
        cubicTo(
            size.width * -.0451f, size.height * .3495f,
            size.width * .0159f, size.height * .2132f,
            size.width * .1717f, size.height * .1717f
        )
        cubicTo(
            size.width * .2132f, size.height * .0159f,
            size.width * .3495f, size.height * -.0451f,
            size.width * .5f, size.height * .0357f
        )
        cubicTo(
            size.width * .6505f, size.height * -.0451f,
            size.width * .7868f, size.height * .0159f,
            size.width * .8283f, size.height * .1717f
        )
        cubicTo(
            size.width * .9841f, size.height * .2132f,
            size.width * 1.0451f, size.height * .3495f,
            size.width * .9643f, size.height * .5f
        )
        close()
    }

    override fun hashCode(): Int = ("Flower shape").hashCode()

    override fun toString(): String = "Flower shape"

    override fun equals(other: Any?): Boolean = false
}