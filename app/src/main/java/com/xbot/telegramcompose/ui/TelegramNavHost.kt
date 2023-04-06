package com.xbot.telegramcompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.ui.features.home.navigation.homeGraph
import com.xbot.telegramcompose.ui.features.login.navigation.loginGraph
import com.xbot.telegramcompose.ui.navigation.Login

@Composable
fun TelegramNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    displayFeatures: List<DisplayFeature>,
    startDestination: String = Login.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        loginGraph(
            displayFeatures = displayFeatures,
            navController = navController
        )
        homeGraph(
            displayFeatures = displayFeatures,
            navController = navController
        )
    }
}
