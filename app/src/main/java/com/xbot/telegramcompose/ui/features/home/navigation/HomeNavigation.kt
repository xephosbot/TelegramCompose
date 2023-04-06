package com.xbot.telegramcompose.ui.features.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.ui.features.home.HomeRoute
import com.xbot.telegramcompose.ui.navigation.Home

fun NavGraphBuilder.homeGraph(
    displayFeatures: List<DisplayFeature>,
    navController: NavController
) {
    navigation(
        startDestination = Home.ChatList.route,
        route = Home.route
    ) {
        composable(route = Home.ChatList.route) {
            HomeRoute()
        }
        composable(route = Home.CreateChat.route) {

        }
        composable(route = Home.Settings.route) {

        }
    }
}