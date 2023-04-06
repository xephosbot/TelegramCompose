package com.xbot.telegramcompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChipGroupPreview(
    labels: List<String> = listOf("Friends", "Personal", "Work"),
    isMultipleSelected: Set<String> = emptySet()
) {
    Surface {
        var selectedFilters by remember { mutableStateOf(isMultipleSelected) }
        
        ChipGroup(
            modifier = Modifier.padding(16.dp),
            alignment = Alignment.CenterHorizontally,
            spacing = 8.dp
        ) {
            if (labels.size > 1) {
                val selected = selectedFilters.containsAll(labels)

                AnimatedFilterChip(
                    selected = selected,
                    onClick = {
                        selectedFilters = if (selected) selectedFilters - labels.toSet()
                        else selectedFilters + labels.toSet()
                    },
                    label = { Text(text = "All") },
                    selectedColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            labels.forEach { label ->
                val selected = selectedFilters.contains(label)

                AnimatedFilterChip(
                    selected = selected,
                    onClick = {
                        selectedFilters = if (selected) selectedFilters - label
                        else selectedFilters + label
                    },
                    label = { Text(text = label) }
                )
            }
        }
    }
}

@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
    spacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    val measurePolicy = chipGroupMeasurePolicy(alignment, spacing)
    Layout(
        content = content,
        measurePolicy = measurePolicy,
        modifier = modifier
    )
}

fun chipGroupMeasurePolicy(
    alignment: Alignment.Horizontal,
    spacing: Dp
) = MeasurePolicy { measurables, constraints ->
    val spacingPx = spacing.roundToPx()
    val rows = mutableListOf<MeasuredRow>()
    val itemConstraints = constraints.copy(minWidth = 0)

    for (measurable in measurables) {
        val lastRow = rows.lastOrNull()
        val placeable = measurable.measure(itemConstraints)

        if (lastRow != null && lastRow.width + spacingPx + placeable.width <= constraints.maxWidth) {
            lastRow.items.add(placeable)
            lastRow.width += spacingPx + placeable.width
            lastRow.height = max(lastRow.height, placeable.height)
        } else {
            val nextRow = MeasuredRow(
                items = mutableListOf(placeable),
                width = placeable.width,
                height = placeable.height
            )
            rows.add(nextRow)
        }
    }

    val width = rows.maxOfOrNull { row -> row.width } ?: 0
    val height = rows.sumOf { row -> row.height } + max(spacingPx.times(rows.size - 1), 0)

    val coercedWidth = width.coerceIn(constraints.minWidth, constraints.maxWidth)
    val coercedHeight = height.coerceIn(constraints.minHeight, constraints.maxHeight)

    layout(coercedWidth, coercedHeight) {
        var y = 0
        for (row in rows) {
            var x = when(alignment) {
                Alignment.Start -> 0
                Alignment.CenterHorizontally -> (coercedWidth - row.width) / 2
                Alignment.End -> coercedWidth - row.width

                else -> throw Exception("unsupported alignment")
            }

            for (item in row.items) {
                item.place(x, y)
                x += item.width + spacingPx
            }

            y += row.height + spacingPx
        }
    }
}

private data class MeasuredRow(
    val items: MutableList<Placeable>,
    var width: Int,
    var height: Int
)