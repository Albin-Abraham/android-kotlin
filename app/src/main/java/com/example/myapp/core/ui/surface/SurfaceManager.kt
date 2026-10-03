package com.example.myapp.core.ui.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Material 3 Surface Tiering for tonal elevation management.
 */
enum class SurfaceTier {
    LOWEST,
    LOW,
    BASE,
    HIGH,
    HIGHEST
}

/**
 * Reusable M3 Surface Manager that enforces tonal surface container hierarchy.
 */
@Composable
fun AppSurface(
    tier: SurfaceTier,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    shadowElevation: Dp = 0.dp,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable () -> Unit
) {
    val containerColor = when (tier) {
        SurfaceTier.LOWEST -> MaterialTheme.colorScheme.surfaceContainerLowest
        SurfaceTier.LOW -> MaterialTheme.colorScheme.surfaceContainerLow
        SurfaceTier.BASE -> MaterialTheme.colorScheme.surface
        SurfaceTier.HIGH -> MaterialTheme.colorScheme.surfaceContainerHigh
        SurfaceTier.HIGHEST -> MaterialTheme.colorScheme.surfaceContainerHighest
    }

    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        shadowElevation = shadowElevation,
        content = content
    )
}
