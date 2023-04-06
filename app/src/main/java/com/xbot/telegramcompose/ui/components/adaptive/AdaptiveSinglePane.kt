package com.xbot.telegramcompose.ui.components.adaptive

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AdaptiveSinglePane(
    modifier: Modifier = Modifier,
    detailOpened: Boolean,
    onDetailOpen: (Boolean) -> Unit,
    first: @Composable () -> Unit,
    second: @Composable () -> Unit
) {
    //val slideDistance = rememberSlideDistance()

    BackHandler(enabled = detailOpened) {
        if (detailOpened) onDetailOpen(false)
    }

    Surface {
        Crossfade(targetState = detailOpened) { forward ->
            if (forward) {
                second()
            } else {
                first()
            }
        }

        //TODO: Fix material motion
        /*MaterialMotion(
            modifier = modifier,
            targetState = detailOpened,
            transitionSpec = {
                materialSharedAxisX(forward = detailOpened, slideDistance = slideDistance)
            },
            pop = !detailOpened
        ) { forward ->
            if (forward) {
                second()
            } else {
                first()
            }
        }*/
    }
}

