package com.xbot.telegramcompose.ui.components.adaptive

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.xbot.telegramcompose.ui.components.adaptive.AdaptiveTwoPaneDefaults.calculateSplitFraction
import com.xbot.telegramcompose.ui.components.adaptive.AdaptiveTwoPaneDefaults.windowInsets

@Composable
fun AdaptiveTwoPane(
    modifier: Modifier = Modifier,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit,
    displayFeatures: List<DisplayFeature>,
    windowInsets: WindowInsets = AdaptiveTwoPaneDefaults.windowInsets,
    foldAwareConfiguration: FoldAwareConfiguration = FoldAwareConfiguration.AllFolds
) {
    val secondContainerWindowInsets = windowInsets
        .only(WindowInsetsSides.Vertical + WindowInsetsSides.End)
        .union(windowInsets(end = 16.dp))

    Surface(
        color = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            TwoPane(
                first = first,
                second = {
                    SecondContainerWrapper(
                        modifier = Modifier
                            .windowInsetsPadding(secondContainerWindowInsets)
                            .consumeWindowInsets(secondContainerWindowInsets),
                        content = second
                    )
                },
                strategy = HorizontalTwoPaneStrategy(splitFraction = calculateSplitFraction(maxWidth)),
                displayFeatures = displayFeatures,
                modifier = modifier,
                foldAwareConfiguration = foldAwareConfiguration
            )
        }
    }
}

@Composable
private fun SecondContainerWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape =  MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            content = { content() }
        )
    }
}

object AdaptiveTwoPaneDefaults {
    val windowInsets: WindowInsets
        @Composable
        get() = WindowInsets.systemBars.union(WindowInsets.displayCutout)

    val displayCutout: WindowInsets
        @Composable
        get() = WindowInsets.displayCutout

    @Composable
    fun calculateSplitFraction(fullWidth: Dp): Float {
        val layoutDirection = LocalLayoutDirection.current
        val cutoutPadding = displayCutout.asPaddingValues()

        val widthWithoutCutout = LocalConfiguration.current.screenWidthDp.dp
        val leftPaneWidth =
            widthWithoutCutout / 2 + cutoutPadding.calculateStartPadding(layoutDirection)

        return remember(fullWidth, leftPaneWidth) {
            leftPaneWidth / fullWidth
        }
    }

    @Composable
    fun windowInsets(
        start: Dp = 0.dp,
        top: Dp = 0.dp,
        end: Dp = 0.dp,
        bottom: Dp = 0.dp
    ): WindowInsets {
        val layoutDirection = LocalLayoutDirection.current
        return remember(layoutDirection) {
            WindowInsets(
                left = if (layoutDirection == LayoutDirection.Ltr) start else end,
                top = top,
                right = if (layoutDirection == LayoutDirection.Ltr) end else start,
                bottom = bottom
            )
        }
    }
}