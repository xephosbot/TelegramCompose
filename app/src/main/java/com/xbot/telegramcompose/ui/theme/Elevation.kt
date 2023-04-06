package com.xbot.telegramcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class Elevation(
    val level0: Dp = ElevationDefaults.level0,
    val level1: Dp = ElevationDefaults.level1,
    val level2: Dp = ElevationDefaults.level2,
    val level3: Dp = ElevationDefaults.level3,
    val level4: Dp = ElevationDefaults.level4,
    val level5: Dp = ElevationDefaults.level5,
) {
    /** Returns a copy of this Shapes, optionally overriding some of the values. */
    fun copy(
        level0: Dp = this.level0,
        level1: Dp = this.level1,
        level2: Dp = this.level2,
        level3: Dp = this.level3,
        level4: Dp = this.level4,
        level5: Dp = this.level5
    ): Elevation = Elevation(
        level0 = level0,
        level1 = level1,
        level2 = level2,
        level3 = level3,
        level4 = level4,
        level5 = level5
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Elevation) return false
        if (level0 != other.level0) return false
        if (level1 != other.level1) return false
        if (level2 != other.level2) return false
        if (level3 != other.level3) return false
        if (level4 != other.level4) return false
        if (level5 != other.level5) return false
        return true
    }

    override fun hashCode(): Int {
        var result = level0.hashCode()
        result = 31 * result + level1.hashCode()
        result = 31 * result + level2.hashCode()
        result = 31 * result + level3.hashCode()
        result = 31 * result + level4.hashCode()
        result = 31 * result + level5.hashCode()
        return result
    }

    override fun toString(): String {
        return "Elevation(" +
                "level0=$level0, " +
                "level1=$level1, " +
                "level2=$level2, " +
                "level3=$level3, " +
                "level4=$level4, " +
                "level5=$level5)"
    }
}

/**
 * Contains the default values used by [Elevation]
 */
object ElevationDefaults {
    val level0: Dp = 0.dp
    val level1: Dp = 1.dp
    val level2: Dp = 3.dp
    val level3: Dp = 6.dp
    val level4: Dp = 8.dp
    val level5: Dp = 12.dp
}

val LocalElevation = staticCompositionLocalOf { Elevation() }

val MaterialTheme.elevation: Elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current

@Composable
fun rememberElevation(): Elevation {
    return remember { Elevation() }
}