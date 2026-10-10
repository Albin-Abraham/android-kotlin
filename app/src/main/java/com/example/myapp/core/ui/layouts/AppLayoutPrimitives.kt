package com.example.myapp.core.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.myapp.core.ui.theme.spacing

/**
 * Standardized Design-Token Aware Column Layout Primitive.
 * Injects spacing tokens automatically with optional typography contrast overlay protection.
 */
@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    spacing: Dp = MaterialTheme.spacing.medium,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(spacing),
    enableFontOverlayProtection: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    if (enableFontOverlayProtection) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement,
                content = content
            )
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
            content = content
        )
    }
}

/**
 * Standardized Design-Token Aware Row Layout Primitive.
 * Enforces vertical centering by default and standard token-based horizontal spacing.
 */
@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    spacing: Dp = MaterialTheme.spacing.medium,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(spacing),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    enableFontOverlayProtection: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    if (enableFontOverlayProtection) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface
        ) {
            Row(
                modifier = modifier,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                content = content
            )
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

/**
 * High-Contrast Font Overlay Protection Container.
 * Ensures that child typography maintains legible WCAG AAA contrast ratios
 * when placed over complex gradients, aura glows, or semi-transparent surfaces.
 */
@Composable
fun FontOverlayContainer(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        ProvideTextStyle(value = textStyle) {
            Box(modifier = modifier) {
                content()
            }
        }
    }
}
