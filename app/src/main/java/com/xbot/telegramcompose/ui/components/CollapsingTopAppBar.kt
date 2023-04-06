package com.xbot.telegramcompose.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

@ExperimentalMaterial3Api
@Composable
fun CollapsingTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    centeredTitle: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    CollapsingTopAppBarLayout(
        title = title,
        centeredTitle = centeredTitle,
        smallTitle = title,
        centeredSmallTitle = false,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        maxHeight = LargeTopAppBarMaxContainerHeight,
        pinnedHeight = LargeTopAppBarPinnedContainerHeight,
        titleBottomPadding = LargeTitleBottomPadding,
        scrollBehavior = scrollBehavior,
        content = content
    )
}

/**
 * A collapsing top app bar layout that is designed to be called by the Collapsing top app bar composable.
 *
 * @throws [IllegalArgumentException] if the given [maxHeight] is equal or smaller than the
 * [pinnedHeight]
 */
@ExperimentalMaterial3Api
@Composable
private fun CollapsingTopAppBarLayout(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    centeredTitle: Boolean,
    smallTitle: @Composable () -> Unit,
    centeredSmallTitle: Boolean,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    windowInsets: WindowInsets,
    maxHeight: Dp,
    pinnedHeight: Dp,
    titleBottomPadding: Dp,
    scrollBehavior: TopAppBarScrollBehavior?,
    content: @Composable () -> Unit
) {
    if (maxHeight <= pinnedHeight) {
        throw IllegalArgumentException(
            "A TwoRowsTopAppBar max height should be greater than its pinned height"
        )
    }

    val pinnedHeightPx: Float
    val maxHeightPx: Float
    val titleBottomPaddingPx: Int

    LocalDensity.current.run {
        pinnedHeightPx = pinnedHeight.toPx()
        maxHeightPx = maxHeight.toPx()
        titleBottomPaddingPx = titleBottomPadding.roundToPx()
    }

    // Wrap the given actions in a Row.
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }

    DimensionSubcomposeLayout(
        mainContent = content
    ) { size: Size ->
        // Sets the app bar's height offset limit to hide just the bottom title area and keep top title
        // visible when collapsed.
        val scrollMaxDistance = pinnedHeightPx - maxHeightPx - size.height
        SideEffect {
            if (scrollBehavior?.state?.heightOffsetLimit != scrollMaxDistance) {
                scrollBehavior?.state?.heightOffsetLimit = scrollMaxDistance
            }
        }

        // Obtain the container Color from the TopAppBarColors using the `collapsedFraction`, as the
        // bottom part of this TwoRowsTopAppBar changes color at the same rate the app bar expands or
        // collapse.
        // This will potentially animate or interpolate a transition between the container color and the
        // container's scrolled color according to the app bar's scroll state.
        val transitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
        val appBarContainerColor by rememberUpdatedState(
            lerp(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surfaceColorAtElevation(ScrolledContainerElevation),
                FastOutLinearInEasing.transform(transitionFraction)
            )
        )

        // Hide the top row title semantics when its alpha value goes below 0.5 threshold.
        // Hide the bottom row title semantics when the top title semantics are active.
        val hideTopRowSemantics = transitionFraction < 0.5f
        val hideBottomRowSemantics = !hideTopRowSemantics

        // Set up support for resizing the top app bar when vertically dragging the bar itself.
        val appBarDragModifier = if ((scrollBehavior != null) && !scrollBehavior.isPinned) {
            Modifier.draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
                },
                onDragStopped = { velocity ->
                    settleAppBar(
                        scrollBehavior.state,
                        velocity,
                        scrollBehavior.flingAnimationSpec,
                        scrollBehavior.snapAnimationSpec
                    )
                }
            )
        } else {
            Modifier
        }

        Surface(modifier = modifier.then(appBarDragModifier)) {
            Column {
                TopAppBarLayout(
                    modifier = Modifier
                        .drawBehind { drawRect(color = appBarContainerColor) }
                        .windowInsetsPadding(windowInsets)
                        .graphicsLayer { clip = true },
                    heightPx = pinnedHeightPx,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    title = smallTitle,
                    titleTextStyle = MaterialTheme.typography.titleLarge,
                    titleAlphaProvider = { TopTitleAlphaEasing.transform(transitionFraction) },
                    titleVerticalArrangement = Arrangement.Center,
                    titleHorizontalArrangement = if (centeredSmallTitle) Arrangement.Center else Arrangement.Start,
                    titleBottomPadding = 0,
                    hideTitleSemantics = hideTopRowSemantics,
                    navigation = navigationIcon,
                    actions = actionsRow,
                    content = {}
                )
                TopAppBarLayout(
                    modifier = Modifier
                        .windowInsetsPadding(windowInsets.only(WindowInsetsSides.Horizontal))
                        .graphicsLayer { clip = true },
                    heightPx = maxHeightPx - pinnedHeightPx + size.height
                            + (scrollBehavior?.state?.heightOffset ?: 0f),
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    title = title,
                    titleTextStyle = MaterialTheme.typography.headlineMedium,
                    titleAlphaProvider = { 1f - transitionFraction },
                    titleVerticalArrangement = Arrangement.Bottom,
                    titleHorizontalArrangement = if (centeredTitle) Arrangement.Center else Arrangement.Start,
                    titleBottomPadding = titleBottomPaddingPx,
                    hideTitleSemantics = hideBottomRowSemantics,
                    navigation = {},
                    actions = {},
                    content = content
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TopAppBarLayout(
    modifier: Modifier,
    heightPx: Float,
    navigationIconContentColor: Color,
    titleContentColor: Color,
    actionIconContentColor: Color,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    titleAlphaProvider: () -> Float,
    titleVerticalArrangement: Arrangement.Vertical,
    titleHorizontalArrangement: Arrangement.Horizontal,
    titleBottomPadding: Int,
    hideTitleSemantics: Boolean,
    navigation: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val navigationIcon = @Composable {
        Box(
            modifier = Modifier
                .padding(start = TopAppBarHorizontalPadding)
        ) {
            CompositionLocalProvider(LocalContentColor provides navigationIconContentColor) {
                navigation()
            }
        }
    }
    val titleText = @Composable {
        Box(
            modifier = Modifier
                .padding(horizontal = TopAppBarHorizontalPadding)
                .conditional(hideTitleSemantics) {
                    Modifier.clearAndSetSemantics { }
                }
                .graphicsLayer { alpha = titleAlphaProvider() }
        ) {
            CompositionLocalProvider(LocalContentColor provides titleContentColor){
                ProvideTextStyle(titleTextStyle, title)
            }
        }
    }
    val actionIcons = @Composable {
        Box(
            modifier = Modifier.padding(end = TopAppBarHorizontalPadding)
        ) {
            CompositionLocalProvider(LocalContentColor provides actionIconContentColor) {
                actions()
            }
        }
    }
    val contentBox = @Composable {
        Column(
            modifier = Modifier
                .padding(horizontal = TopAppBarHorizontalPadding)
                .graphicsLayer { alpha = titleAlphaProvider() },
        ) {
            content()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    Layout(
        contents = listOf(navigationIcon, titleText, actionIcons, contentBox),
        modifier = modifier
    ) { (navigationIconMeasurables, titleTextMeasurables, actionIconsMeasurables, contentMeasurables),
        constraints ->
        val navigationIconPlaceable = navigationIconMeasurables.first()
            .measure(constraints.copy(minWidth = 0))
        val actionIconsPlaceable = actionIconsMeasurables.first()
            .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.maxWidth
        } else {
            (constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width)
                .coerceAtLeast(0)
        }
        val titlePlaceable = titleTextMeasurables.first()
            .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))
        val contentPlaceable = contentMeasurables.first()
            .measure(constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth - TopAppBarTitleInset.roundToPx() * 2))

        val titleBaseline =
            if (titlePlaceable[LastBaseline] != AlignmentLine.Unspecified) {
                titlePlaceable[LastBaseline]
            } else {
                0
            }

        val layoutHeight = heightPx.roundToInt()

        layout(constraints.maxWidth, layoutHeight) {
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            titlePlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> (constraints.maxWidth - titlePlaceable.width) / 2
                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width - actionIconsPlaceable.width
                    else -> max(TopAppBarTitleInset.roundToPx(), navigationIconPlaceable.width)
                },
                y = when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    Arrangement.Bottom ->
                        if (titleBottomPadding + contentPlaceable.height == 0)
                            layoutHeight - titlePlaceable.height
                        else layoutHeight - titlePlaceable.height - max(
                            0,
                            titleBottomPadding + contentPlaceable.height - titlePlaceable.height + titleBaseline
                        )
                    else -> 0
                }
            )

            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )

            contentPlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> (constraints.maxWidth - contentPlaceable.width) / 2
                    Arrangement.End -> constraints.maxWidth - contentPlaceable.width
                    else -> TopAppBarTitleInset.roundToPx()
                },
                y = layoutHeight - contentPlaceable.height - max(
                    0,
                    titleBottomPadding - contentPlaceable.height
                )
            )
        }
    }
}

/**
 * SubcomposeLayout that [SubcomposeMeasureScope.subcompose] [mainContent]
 * and gets total size of [mainContent] and passes this size to [dependentContent].
 * This layout passes exact size of content unlike
 * BoxWithConstraints which returns [Constraints] that doesn't match Composable dimensions under
 * some circumstances
 *
 * @param mainContent Composable is used for calculating size and pass it
 * to Composables that depend on it
 *
 * @param dependentContent Composable requires dimensions of [mainContent] to set its size.
 * One example for this is overlay over Composable that should match [dependentContent] size.
 *
 */
@Composable
internal fun DimensionSubcomposeLayout(
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (Size) -> Unit
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints: Constraints ->

        // Subcompose(compose only a section) main content and get Placeable
        val mainPlaceable = subcompose(SlotsEnum.Main, mainContent).map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }.maxByOrNull { it.height }

        val dependentContentSize = if (mainPlaceable == null) {
            Size(0f, 0f)
        } else {
            Size(mainPlaceable.width.toFloat(), mainPlaceable.height.toFloat())
        }

        val dependentPlaceable: Placeable = subcompose(SlotsEnum.Dependent) {
            dependentContent(dependentContentSize)
        }.map { measurable: Measurable ->
            measurable.measure(constraints)
        }.first()

        layout(dependentPlaceable.width, dependentPlaceable.height) {
            dependentPlaceable.placeRelative(0, 0)
        }
    }
}

internal enum class SlotsEnum { Main, Dependent }

@ExperimentalMaterial3Api
suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec,
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}

private val TopTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)

private val ScrolledContainerElevation = 3.dp

private val LargeTitleBottomPadding = 28.dp
private val TopAppBarHorizontalPadding = 4.dp

// A title inset when the App-Bar is a Medium or Large one. Also used to size a spacer when the
// navigation icon is missing.
private val TopAppBarTitleInset = 16.dp - TopAppBarHorizontalPadding

val LargeTopAppBarMaxContainerHeight = 152.dp
val LargeTopAppBarPinnedContainerHeight = 64.dp