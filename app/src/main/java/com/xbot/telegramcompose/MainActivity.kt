package com.xbot.telegramcompose

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.WindowCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.xbot.telegramcompose.ui.TelegramApp
import com.xbot.telegramcompose.ui.components.*
import com.xbot.telegramcompose.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var appReady: Boolean = false

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DefaultTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)

                TelegramApp(
                    windowSizeClass = windowSize,
                    displayFeatures = displayFeatures,
                )
            }
        }

        splashScreen.setKeepOnScreenCondition { !appReady }
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            onSplashScreenExit(splashScreenViewProvider)
        }

        lifecycleScope.launch {
            delay(MOCK_DELAY.toLong())
            appReady = true
        }
    }

    private fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        val accelerateInterpolator = FastOutLinearInInterpolator()
        val splashScreenView = splashScreenViewProvider.view
        val iconView = splashScreenViewProvider.iconView

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f),
            ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)
        )
        animatorSet.duration = SPLASHSCREEN_ALPHA_ANIMATION_DURATION.toLong()
        animatorSet.interpolator = accelerateInterpolator

        animatorSet.doOnEnd { splashScreenViewProvider.remove() }

        if (WAIT_FOR_AVD_TO_FINISH) {
            waitForAnimatedIconToFinish(splashScreenViewProvider, splashScreenView, animatorSet)
        } else {
            animatorSet.start()
        }
    }

    private fun waitForAnimatedIconToFinish(
        splashScreenViewProvider: SplashScreenViewProvider,
        view: View,
        animatorSet: AnimatorSet
    ) {
        val delayMillis: Long = (splashScreenViewProvider.iconAnimationStartMillis +
            splashScreenViewProvider.iconAnimationDurationMillis) - System.currentTimeMillis()
        view.postDelayed({ animatorSet.start() }, delayMillis)
    }

    private companion object {
        const val MOCK_DELAY = 200
        const val SPLASHSCREEN_ALPHA_ANIMATION_DURATION = 500
        const val WAIT_FOR_AVD_TO_FINISH = false
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(name = "DefaultLightTheme", showBackground = true)
@Preview(name = "DefaultDarkTheme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    DefaultTheme (dynamicColor = false) {
        TelegramApp(
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
            displayFeatures = emptyList()
        )
    }
}