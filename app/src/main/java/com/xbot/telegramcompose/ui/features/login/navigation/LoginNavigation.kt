package com.xbot.telegramcompose.ui.features.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.ui.features.login.LoginRoute
import com.xbot.telegramcompose.ui.navigation.Home
import com.xbot.telegramcompose.ui.navigation.Login

fun NavGraphBuilder.loginGraph(
    displayFeatures: List<DisplayFeature>,
    navController: NavController
) {
    navigation(
        startDestination = Login.InsertNumber.route,
        route = Login.route
    ) {
        composable(route = Login.InsertNumber.route) {
            LoginRoute(
                displayFeatures = displayFeatures,
                onLoggedIn = {
                    navController.navigate(Home.route) {
                        popUpTo(Login.InsertNumber.route) { inclusive = true }
                    }
                }
            )
        }
    }
}