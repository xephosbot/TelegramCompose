package com.xbot.telegramcompose.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.xbot.telegramcompose.ui.theme.DefaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TrackPreview() {
    var sliderPosition by remember { mutableStateOf(0.5f) }
    val interactionSource = remember { MutableInteractionSource() }
    val colors = SliderDefaults.colors()
    val enabled = true

    DefaultTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(12.dp),
                tonalElevation = 3.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    Text(
                        text = "In This Dark Time",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Row {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = ""
                            )
                        }

                        Slider(
                            modifier = Modifier.weight(1f),
                            value = sliderPosition,
                            onValueChange = { sliderPosition = it },
                            enabled = enabled,
                            colors = colors,
                            track = remember(colors, enabled) { { sliderPositions ->
                                Track(
                                    colors = colors,
                                    enabled = enabled,
                                    sliderPositions = sliderPositions
                                )
                            } },
                            interactionSource = interactionSource,
                            thumb = remember(interactionSource, colors, enabled) { {
                                Thumb(
                                    interactionSource = interactionSource,
                                    colors = colors,
                                    enabled = enabled
                                )
                            } }
                        )

                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun Track(
    sliderPositions: SliderPositions,
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors(),
    enabled: Boolean = true,
) {
    val inactiveTrackColor = trackColor(enabled, active = false)
    val activeTrackColor = trackColor(enabled, active = true)
    val inactiveTickColor = tickColor(enabled, active = false)
    val activeTickColor = tickColor(enabled, active = true)

    val infiniteTransition = rememberInfiniteTransition()
    val fraction by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(TrackHeight)
    ) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val trackStrokeWidth = TrackHeight.toPx()

        val sliderValueEnd = Offset(
            sliderStart.x + (sliderEnd.x - sliderStart.x) * sliderPositions.positionFraction,
            center.y
        )
        val sliderValueStart = Offset(
            sliderStart.x + (sliderEnd.x - sliderStart.x) * 0f,
            center.y
        )

        drawLine(
            color = inactiveTrackColor.value,
            start = sliderValueEnd,
            end = sliderEnd,
            strokeWidth = trackStrokeWidth,
            cap = StrokeCap.Round
        )

        clipRect(
            top = 0.0f - trackStrokeWidth,
            bottom = size.height + trackStrokeWidth,
            left = sliderStart.x,
            right = sliderValueEnd.x
        ) {
            val waveCount = (size.width / 100f).toInt()

            translate(left = (size.width / waveCount) * fraction) {
                drawPath(
                    path = trackShape(
                        size = Size(size.width, size.height * 2),
                        offset = Offset(0f, -trackStrokeWidth / 2),
                        waveCount = waveCount
                    ),
                    color = activeTrackColor.value,
                    style = Stroke(
                        width = trackStrokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun Thumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors(),
    enabled: Boolean = true,
    thumbSize: DpSize = ThumbSize
) {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val elevation = if (interactions.isNotEmpty()) {
        ThumbPressedElevation
    } else {
        ThumbDefaultElevation
    }
    val shape = RoundedCornerShape(50)

    Spacer(
        modifier
            .size(thumbSize)
            .offset(x = (20.dp - thumbSize.width) / 2)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = StateLayerSize / 2
                )
            )
            .hoverable(interactionSource = interactionSource)
            .shadow(if (enabled) elevation else 0.dp, shape, clip = false)
            .background(thumbColor(enabled).value, shape)
    )
}

@Composable
internal fun trackColor(enabled: Boolean, active: Boolean): State<Color> {
    return rememberUpdatedState(
        if (enabled) {
            if (active) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant
        } else {
            if (active) MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledActiveTrackOpacity)
            else MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledInactiveTrackOpacity)
        }
    )
}

@Composable
internal fun tickColor(enabled: Boolean, active: Boolean): State<Color> {
    return rememberUpdatedState(
        if (enabled) {
            if (active) MaterialTheme.colorScheme.onPrimary.copy(alpha = ActiveTickMarksContainerOpacity)
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = InactiveTickMarksContainerOpacity)
        } else {
            if (active) MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledTickMarksContainerOpacity)
            else MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledTickMarksContainerOpacity)
        }
    )
}

@Composable
internal fun thumbColor(enabled: Boolean): State<Color> {
    return rememberUpdatedState(if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
}

internal fun trackShape(size: Size, offset: Offset, waveCount: Int): Path = Path().apply {
    val offsetX = size.width / waveCount

    moveTo(-offsetX + offset.x, size.height * 0.5f + offset.y)
    for (i in -1 until waveCount) {
        cubicTo(
            offsetX * 0.1767f + offsetX * i + offset.x, size.height + offset.y,
            offsetX * 0.3433f + offsetX * i + offset.x, size.height + offset.y,
            offsetX * 0.5f + offsetX * i + offset.x, size.height * 0.5f + offset.y
        )
        cubicTo(
            offsetX * 0.6767f + offsetX * i + offset.x, 0f + offset.y,
            offsetX * 0.8433f + offsetX * i + offset.x, 0f + offset.y,
            offsetX + offsetX * i + offset.x, size.height * 0.5f + offset.y
        )
    }
}

private val TrackHeight = 4.0.dp
private val TickSize = 2.0.dp
private val ThumbWidth = 5.0.dp
private val ThumbHeight = 20.0.dp
private val ThumbDefaultElevation = 1.dp
private val ThumbPressedElevation = 6.dp
private val StateLayerSize = 40.0.dp
private val ThumbSize = DpSize(ThumbWidth, ThumbHeight)
const val DisabledActiveTrackOpacity = 0.38f
const val DisabledInactiveTrackOpacity = 0.12f
const val ActiveTickMarksContainerOpacity = 0.38f
const val InactiveTickMarksContainerOpacity = 0.38f
const val DisabledTickMarksContainerOpacity = 0.38f