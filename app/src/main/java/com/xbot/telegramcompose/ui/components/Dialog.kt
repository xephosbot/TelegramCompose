package com.xbot.telegramcompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogWithAppbar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    bottomActions: @Composable RowScope.() -> Unit = {},
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: LazyListScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        val listState = rememberLazyListState()

        val bottomActionsRow = @Composable {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = bottomActions
            )
        }

        val contentLazyColumn = @Composable {
            LazyColumn(
                state = listState,
                content = content
            )
        }

        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 3.dp
        ) {
            DialogWithAppbarContent(
                modifier = modifier,
                title = title,
                bottomActions = bottomActionsRow,
                listState = listState,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                titleTextStyle = MaterialTheme.typography.headlineSmall,
                layoutHorizontalPadding = DialogHorizontalPadding,
                layoutVerticalPadding = DialogVerticalPadding,
                dividerContentColor = MaterialTheme.colorScheme.outlineVariant,
                content = contentLazyColumn
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogWithAppbarContent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    bottomActions: @Composable () -> Unit,
    listState: LazyListState,
    titleContentColor: Color,
    titleTextStyle: TextStyle,
    layoutHorizontalPadding: Dp,
    layoutVerticalPadding: Dp,
    dividerContentColor: Color,
    content: @Composable () -> Unit
) {
    val titleBox = @Composable {
        Box {
            CompositionLocalProvider(LocalContentColor provides titleContentColor) {
                ProvideTextStyle(titleTextStyle, title)
            }
        }
    }
    val contentBox = @Composable {
        Box (content = { content() })
    }
    val bottomActionsBox = @Composable {
        Box (content = { bottomActions() })
    }
    val divider = @Composable {
        Divider(color = dividerContentColor)
    }
    Layout(
        contents = listOf(titleBox, contentBox, bottomActionsBox, divider, divider),
        modifier = modifier
    ) { (titleMeasurables, contentMeasurables, bottomActionsMeasurables, dividerTopMeasurables, dividerBottomMeasurables),
        constraints: Constraints ->

        val titlePlaceable = titleMeasurables.first()
            .measure(constraints.copy(minWidth = 0))
        val bottomActionsPlaceable = bottomActionsMeasurables.first()
            .measure(constraints.copy(minWidth = 0))

        val titleExists = titlePlaceable.height != 0
        val bottomActionsExists = bottomActionsPlaceable.height != 0

        val verticalPadding = layoutVerticalPadding.roundToPx()
        val horizontalPadding = layoutHorizontalPadding.roundToPx()

        val paddingHeight = if (titleExists && bottomActionsExists) {
            verticalPadding * 4
        } else if (titleExists || bottomActionsExists) {
            verticalPadding * 3
        } else {
            verticalPadding * 2
        }
        val contentMaxHeight = DialogMaxHeight.roundToPx() - titlePlaceable.height -
                bottomActionsPlaceable.height - paddingHeight
        val contentPlaceable = contentMeasurables.first()
            .measure(constraints.copy(
                minWidth = 0, maxWidth = constraints.maxWidth,
                minHeight = 0, maxHeight = contentMaxHeight
            ))
        val dividerTopPlaceable = dividerTopMeasurables.first()
            .measure(constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth))
        val dividerBottomPlaceable = dividerBottomMeasurables.first()
            .measure(constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth))

        val layoutHeight = titlePlaceable.height + bottomActionsPlaceable.height +
                contentPlaceable.height

        layout(constraints.maxWidth, layoutHeight + paddingHeight) {
            if (titleExists) {
                titlePlaceable.placeRelative(
                    x = horizontalPadding,
                    y = verticalPadding
                )
                if (listState.firstVisibleItemIndex != 0) {
                    dividerTopPlaceable.placeRelative(
                        x = 0,
                        y = titlePlaceable.height + verticalPadding * 2 - dividerTopPlaceable.height
                    )
                }
            }
            contentPlaceable.placeRelative(
                x = 0,
                y = if (titleExists) {
                    titlePlaceable.height + verticalPadding * 2
                } else {
                    verticalPadding
                }
            )
            if (bottomActionsExists) {
                dividerBottomPlaceable.placeRelative(
                    x = 0,
                    y = if (titleExists) {
                        titlePlaceable.height + contentPlaceable.height + verticalPadding * 2
                    } else {
                        contentPlaceable.height + verticalPadding
                    }
                )
                bottomActionsPlaceable.placeRelative(
                    x = constraints.maxWidth - bottomActionsPlaceable.width - horizontalPadding,
                    y = if (titleExists) {
                        titlePlaceable.height + contentPlaceable.height + verticalPadding * 3
                    } else {
                        contentPlaceable.height + verticalPadding * 2
                    }
                )
            }
        }
    }
}

private val DialogVerticalPadding = 16.dp
private val DialogHorizontalPadding = 24.dp
private val DialogMaxHeight = 560.dp