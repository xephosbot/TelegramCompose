package com.xbot.telegramcompose.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.xbot.telegramcompose.ui.animation.SineCosineEasing
import com.xbot.telegramcompose.ui.shapes.LargeFlowerShape

@Composable
fun AnimatedWelcomeScreen(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle = MaterialTheme.typography.displaySmall,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    navigationButton: @Composable () -> Unit = {},
    primaryColor: Color = MaterialTheme.colorScheme.primaryContainer,
    secondaryColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentWindowInsets: WindowInsets = AnimatedWelcomeScreenDefaults.windowInsets,
    content: @Composable (ColumnScope.(PaddingValues) -> Unit)
) {
    AnimatedWelcomeScreenContent(
        modifier = modifier,
        title = title,
        titleTextStyle = titleTextStyle,
        textColor = textColor,
        navigationButton = navigationButton,
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}

@Composable
private fun AnimatedWelcomeScreenContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    textColor: Color,
    navigationButton: @Composable () -> Unit,
    primaryColor: Color,
    secondaryColor: Color,
    contentWindowInsets: WindowInsets,
    content: @Composable (ColumnScope.(PaddingValues) -> Unit)
) {
    val layoutInsets = contentWindowInsets
        .union(WindowInsets.displayCutout)
        .only(WindowInsetsSides.Horizontal).asPaddingValues()
    val titleBottomPaddingPx: Int
    val titleHorizontalPaddingPx: Int
    val startPadding = layoutInsets.calculateStartPadding(LocalLayoutDirection.current)
    val endPadding = layoutInsets.calculateEndPadding(LocalLayoutDirection.current)

    LocalDensity.current.run {
        titleBottomPaddingPx = WelcomeScreenTitleBottomPadding.roundToPx()
        titleHorizontalPaddingPx = WelcomeScreenHorizontalPadding.roundToPx()
    }

    val contentColumn = @Composable {
        Column(modifier = Modifier.fillMaxWidth()) {
            content(layoutInsets)
        }
    }
    AnimatedBackground(
        modifier = modifier.windowInsetsPadding(contentWindowInsets),
        primaryColor = primaryColor,
        secondaryColor = secondaryColor
    )
    WelcomeScreenLayout(
        modifier = modifier.windowInsetsPadding(contentWindowInsets),
        title = title,
        titleBottomPaddingPx = titleBottomPaddingPx,
        titleHorizontalPaddingPx = titleHorizontalPaddingPx,
        titleTextStyle = titleTextStyle,
        textColor = textColor,
        navigationButton = navigationButton,
        startPadding = startPadding,
        endPadding = endPadding,
        content = contentColumn
    )
}

@Composable
private fun WelcomeScreenLayout(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleBottomPaddingPx: Int,
    titleHorizontalPaddingPx: Int,
    titleTextStyle: TextStyle,
    textColor: Color,
    navigationButton: @Composable () -> Unit,
    startPadding: Dp,
    endPadding: Dp,
    content: @Composable () -> Unit
) {
    val titleBox = @Composable {
        Box(modifier = Modifier.padding(start = startPadding)) {
            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(titleTextStyle, title)
            }
        }
    }
    val contentBox = @Composable {
        Box(content = { content() })
    }
    val navButtonBox = @Composable {
        Box(modifier = Modifier.padding(end = endPadding)) {
            navigationButton()
        }
    }

    Layout(
        contents = listOf(titleBox, contentBox, navButtonBox),
        modifier = modifier
    ) { (titleMeasurables, contentMeasurables, navButtonMeasurables), constraints: Constraints ->

        val titleMaxWidth = constraints.maxWidth - titleHorizontalPaddingPx * 2
        val titlePlaceable = titleMeasurables.first()
            .measure(constraints.copy(minWidth = 0, maxWidth = titleMaxWidth))
        val contentPlaceable = contentMeasurables.first()
            .measure(constraints.copy(minWidth = 0))
        val navButtonPlaceable = navButtonMeasurables.first()
            .measure(constraints.copy(minWidth = 0))

        val navButtonExist = navButtonPlaceable.height != 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            if (navButtonExist) {
                navButtonPlaceable.place(
                    x = constraints.maxWidth - navButtonPlaceable.width - 16.dp.roundToPx(),
                    y = constraints.maxHeight - navButtonPlaceable.height - 16.dp.roundToPx()
                )
            }
            contentPlaceable.place(
                x = 0,
                y = if (navButtonExist) {
                    constraints.maxHeight - contentPlaceable.height - navButtonPlaceable.height - 32.dp.roundToPx()
                } else {
                    constraints.maxHeight - contentPlaceable.height - 16.dp.roundToPx()
                }
            )
            titlePlaceable.place(
                x = if (layoutDirection == LayoutDirection.Rtl) {
                    constraints.maxWidth - titleHorizontalPaddingPx - titlePlaceable.width
                } else {
                    titleHorizontalPaddingPx
                },
                y = if (navButtonExist) {
                    constraints.maxHeight - navButtonPlaceable.height - contentPlaceable.height -
                            titleBottomPaddingPx - titlePlaceable.height - 32.dp.roundToPx()
                } else {
                    constraints.maxHeight - contentPlaceable.height - titleBottomPaddingPx -
                            titlePlaceable.height - 16.dp.roundToPx()
                }
            )
        }
    }
}

@Composable
private fun AnimatedBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    animationDurationMillis: Int = AnimatedWelcomeScreenDefaults.animationDurationMillis
) {
    val infiniteTransition = rememberInfiniteTransition()
    val fraction by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val movementFraction by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMillis / 2,
                easing = AnimatedWelcomeScreenDefaults.sineInterpolator
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Spacer(modifier = modifier
        .fillMaxSize()
        .drawWithCache {
            val flowerSizePx = Size(380.dp.toPx(), 380.dp.toPx())
            val flowerTranslatePx = if (layoutDirection == LayoutDirection.Ltr) {
                Offset(size.width - 300.dp.toPx(), -50.dp.toPx())
            } else {
                Offset(300.dp.toPx() - size.width, -50.dp.toPx())
            }
            val flowerOffsetPx = 100.dp.toPx()
            val circleRadiusPx = 300.dp.toPx()
            val circleTranslatePx = Offset(0f, size.height - 275.dp.toPx())
            val circleCenterPx = if (layoutDirection == LayoutDirection.Ltr) {
                Offset(circleRadiusPx / 2f, circleRadiusPx / 2f)
            } else {
                Offset(size.width - circleRadiusPx / 2f, circleRadiusPx / 2f)
            }
            val strokeWidthPx = 5.dp.toPx()

            onDrawBehind {
                withTransform({
                    translate(
                        left = flowerTranslatePx.x,
                        top = flowerTranslatePx.y + flowerOffsetPx * movementFraction
                    )
                    rotate(
                        degrees = 360f * fraction,
                        pivot = Offset(flowerSizePx.width, flowerSizePx.height) * 0.5f
                    )
                }) {
                    drawPath(
                        path = LargeFlowerShape().createPath(flowerSizePx),
                        color = primaryColor,
                        style = Stroke(
                            width = strokeWidthPx
                        )
                    )
                }

                translate(
                    left = circleTranslatePx.x,
                    top = circleTranslatePx.y
                ) {
                    drawCircle(
                        color = secondaryColor,
                        radius = circleRadiusPx,
                        center = circleCenterPx,
                        style = Stroke(
                            width = strokeWidthPx
                        )
                    )
                }
            }
        }
    )
}

object AnimatedWelcomeScreenDefaults {
    val windowInsets: WindowInsets
        @Composable
        get() = WindowInsets.systemBars

    val sineInterpolator: Easing = SineCosineEasing(1)
    const val animationDurationMillis: Int = 20000
}

private val WelcomeScreenHorizontalPadding = 16.dp
private val WelcomeScreenTitleBottomPadding = 28.dp