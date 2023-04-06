package com.xbot.telegramcompose.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun AnimatedFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 3.dp,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation),
    selectedColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    enabled: Boolean = true
) {
    val transition = updateTransitionData(unselectedColor, selectedColor, selected)

    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(transition.cornerRadius),
        color = transition.color,
        tonalElevation = tonalElevation
    ) {
        ChipContent(
            label = label,
            labelTextStyle = MaterialTheme.typography.labelLarge,
            labelColor = MaterialTheme.colorScheme.onSurface,
            minHeight = 40.dp,
            paddingValues = SuggestionChipPadding
        )
    }
}

@Composable
private fun ChipContent(
    label: @Composable () -> Unit,
    labelTextStyle: TextStyle,
    labelColor: Color,
    minHeight: Dp,
    paddingValues: PaddingValues
) {
    CompositionLocalProvider(
        LocalContentColor provides labelColor,
        LocalTextStyle provides labelTextStyle
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = minHeight)
                .padding(paddingValues),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(HorizontalElementsPadding))
            label()
            Spacer(Modifier.width(HorizontalElementsPadding))
        }
    }
}

private class TransitionData(
    color: State<Color>,
    cornerRadius: State<Dp>
) {
    val color by color
    val cornerRadius by cornerRadius
}

@Composable
private fun updateTransitionData(unselectedColor: Color, selectedColor: Color, selected: Boolean): TransitionData {
    val transition = updateTransition(selected, label = "chipMorphing")
    val color = transition.animateColor(label = "colorTransition") { state ->
        when(state) {
            false -> unselectedColor
            true -> selectedColor
        }
    }
    val size = transition.animateDp(label = "cornerRadiusTransition") { state ->
        when (state) {
            false -> 12.dp
            true -> 20.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}

private val HorizontalElementsPadding = 8.dp
private val SuggestionChipPadding = PaddingValues(horizontal = HorizontalElementsPadding)