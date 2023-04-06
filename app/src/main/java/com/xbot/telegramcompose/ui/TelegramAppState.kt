package com.xbot.telegramcompose.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberTelegramAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): TelegramAppState {
    return remember(navController, coroutineScope, windowSizeClass) {
        TelegramAppState(navController, coroutineScope, windowSizeClass)
    }
}

@Stable
class TelegramAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
) {
    private val topAppBarType: TopAppBarType
        get() = when(windowSizeClass.heightSizeClass) {
            WindowHeightSizeClass.Compact -> TopAppBarType.SMALL
            WindowHeightSizeClass.Medium -> TopAppBarType.LARGE
            WindowHeightSizeClass.Expanded -> TopAppBarType.LARGE
            else -> TopAppBarType.LARGE
        }

    private val paneType: PaneType
        get() = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> PaneType.SINGLE_PANE
            WindowWidthSizeClass.Medium -> PaneType.DUAL_PANE
            WindowWidthSizeClass.Expanded -> PaneType.DUAL_PANE
            else -> PaneType.SINGLE_PANE
        }

    val contentType: TelegramContentType
        get() = TelegramContentType(paneType, topAppBarType)
}

data class TelegramContentType(
    val pane: PaneType = PaneType.SINGLE_PANE,
    val topAppBar: TopAppBarType = TopAppBarType.LARGE
)

enum class PaneType {
    SINGLE_PANE, DUAL_PANE
}

enum class TopAppBarType {
    SMALL, LARGE
}

val LocalContentType = compositionLocalOf { TelegramContentType() }