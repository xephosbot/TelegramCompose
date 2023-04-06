package com.xbot.telegramcompose.ui.components.adaptive

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.ui.LocalContentType
import com.xbot.telegramcompose.ui.PaneType
import com.xbot.telegramcompose.ui.features.login.InsertNumberScreen
import com.xbot.telegramcompose.ui.features.login.WelcomeScreen

@Composable
fun AdaptivePane(
    modifier: Modifier = Modifier,
    detailOpened: Boolean,
    onDetailOpen: (Boolean) -> Unit,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit,
    displayFeatures: List<DisplayFeature>
) {
    val contentType = checkNotNull(LocalContentType.current) {
        "AdaptivePane requires a [TelegramContentType] to be provided via LocalContentType"
    }

    if (contentType.pane == PaneType.DUAL_PANE) {
        AdaptiveTwoPane(
            modifier = modifier,
            first = first,
            second = second,
            displayFeatures = displayFeatures
        )
    } else {
        AdaptiveSinglePane(
            modifier = modifier,
            detailOpened = detailOpened,
            onDetailOpen = onDetailOpen,
            first = first,
            second = second
        )
    }
}

@Composable
fun PreviewAdaptivePane() {
    var detailOpened by remember { mutableStateOf(false) }

    AdaptivePane(
        detailOpened = detailOpened,
        onDetailOpen = { detailOpened = it },
        first = {
            WelcomeScreen(
                openDetail = {
                    detailOpened = true
                }
            )
        },
        second = {
            InsertNumberScreen(
                navigateNext = {},
                navigateBack = {
                    detailOpened = false
                }
            )
        },
        displayFeatures = emptyList()
    )
}
