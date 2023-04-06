package com.xbot.telegramcompose.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween

@ExperimentalAnimationApi
fun materialFadeIn(
    animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = MaterialMotionDefaults.motionDurationLong2,
        easing = MaterialMotionDefaults.FastOutExtraSlowInInterpolator
    ),
    initialAlpha: Float = 0f,
    initialScale: Float = 0.4f
): EnterTransition = fadeIn(
    animationSpec = animationSpec,
    initialAlpha = initialAlpha
) + scaleIn(
    animationSpec = animationSpec,
    initialScale = initialScale
)

@ExperimentalAnimationApi
fun materialFadeOut(
    animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = MaterialMotionDefaults.motionDurationShort2,
        easing = MaterialMotionDefaults.FastOutLinearInInterpolator
    ),
    targetAlpha: Float = 0f,
    targetScale: Float = 0.8f
): ExitTransition = fadeOut(
    animationSpec = animationSpec,
    targetAlpha = targetAlpha
) + scaleOut(
    animationSpec = animationSpec,
    targetScale = targetScale
)