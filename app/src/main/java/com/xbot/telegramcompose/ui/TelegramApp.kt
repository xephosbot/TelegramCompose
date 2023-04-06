package com.xbot.telegramcompose.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.ui.animation.MaterialMotion
import com.xbot.telegramcompose.ui.animation.materialSharedAxisX
import com.xbot.telegramcompose.ui.animation.rememberSlideDistance
import com.xbot.telegramcompose.ui.features.home.TelegramChatDetailScreen
import com.xbot.telegramcompose.ui.features.home.TelegramChatListScreen

@Composable
fun TelegramApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: TelegramAppState = rememberTelegramAppState(windowSizeClass),
) {
    CompositionLocalProvider(LocalContentType provides appState.contentType) {
        TelegramNavigationWrapper(
            navController = appState.navController,
            displayFeatures = displayFeatures,
        )
    }
}

@Composable
fun TelegramNavigationWrapper(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    displayFeatures: List<DisplayFeature>
) {
    TelegramAppContent(
        modifier = modifier,
        navController = navController,
        displayFeatures = displayFeatures
    )
}

@Composable
fun TelegramAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    displayFeatures: List<DisplayFeature>
) {
    TelegramNavHost(
        modifier = modifier,
        displayFeatures = displayFeatures,
        navController = navController
    )

    /*val contentType = LocalContentType.current

    LaunchedEffect(key1 = contentType) {
        if (contentType == TelegramContentType.SINGLE_PANE && !telegramHomeUIState.isDetailOnlyOpen) {
            closeDetail()
        }
    }

    val listState = rememberLazyListState()

    if (contentType == TelegramContentType.DUAL_PANE) {
        //TODO: Dual pane layout
    } else {
        Surface {
            TelegramSinglePaneContent(
                modifier = modifier,
                telegramHomeUIState = telegramHomeUIState,
                lazyListState = listState,
                navigateToDetail = navigateToDetail,
                closeDetail = closeDetail,
                selectCharFilter = selectCharFilter
            )
        }
    }*/
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TelegramSinglePaneContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    navigateToDetail: () -> Unit,
    closeDetail: () -> Unit,
    selectCharFilter: (List<Long>, Boolean) -> Unit
) {
    val slideDistance = rememberSlideDistance()
    //val detailOpened = (telegramHomeUIState.selectedAccount != null && telegramHomeUIState.isDetailOnlyOpen)
    val detailOpened = false

    BackHandler {
        if (detailOpened) {
            closeDetail()
        }
    }

    MaterialMotion(
        targetState = detailOpened,
        transitionSpec = {
            materialSharedAxisX(forward = detailOpened, slideDistance = slideDistance)
        },
        pop = !detailOpened
    ) { forward ->
        if (forward) {
            TelegramChatDetailScreen(
                closeDetail = closeDetail
            )
        } else {
            TelegramChatListScreen(
                lazyListState = lazyListState,
                modifier = modifier,
            )
        }
    }
}