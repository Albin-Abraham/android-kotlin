package com.example.myapp.core.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 14 Design System Constraint Enforcers & Layout Modifiers.
 * Guarantees mathematical precision, accessibility (a11y), responsive breakpoints,
 * and design system compliance across all UI components.
 */
object DesignConstraints {

    /**
     * Constraint 1: Grid Multiplicity Rule (Ensures Dp values align with the 4dp/8dp base grid).
     */
    fun isGridAligned(dp: Dp, baseGrid: Int = 4): Boolean {
        return (dp.value.toInt() % baseGrid) == 0
    }

    /**
     * Constraint 2: Accessible Touch Target Rule (Minimum 48x48dp per WCAG 2.5.5).
     */
    val MinTouchTargetSize: Dp = 48.dp

    /**
     * Constraint 3: Max Compact Form Width (Prevents stretched forms on tablets/desktops).
     */
    val MaxFormCardWidth: Dp = 480.dp

    /**
     * Constraint 4: Maximum Modal Width.
     */
    val MaxModalWidth: Dp = 560.dp

    /**
     * Constraint 5: Maximum Global Content Width.
     */
    val MaxGlobalContentWidth: Dp = 1200.dp
}

// ============================================================================
// MODIFIER CONSTRAINT EXTENSIONS
// ============================================================================

/**
 * Constraint Enforcer: Restricts cards/dialogs to responsive breakpoint widths.
 */
fun Modifier.responsiveFormCard(
    maxWidth: Dp = 480.dp
): Modifier = this
    .fillMaxWidth()
    .widthIn(max = maxWidth)

/**
 * Constraint Enforcer: Enforces WCAG 48x48dp minimum touch target boundary.
 */
fun Modifier.enforceMinTouchTarget(
    minSize: Dp = 48.dp
): Modifier = this.defaultMinSize(minWidth = minSize, minHeight = minSize)

/**
 * Constraint Enforcer: Applies token-driven elevation with tonal diffusion.
 */
fun Modifier.tonalElevation(
    elevation: Dp,
    shape: Shape
): Modifier = this.shadow(elevation = elevation, shape = shape, clip = false)

/**
 * Constraint Enforcer: Standardized accessible focus ring outline.
 */
fun Modifier.accessibleFocusRing(
    isFocused: Boolean,
    shape: Shape
): Modifier = composed {
    if (isFocused) {
        this.border(
            border = BorderStroke(
                width = MaterialTheme.borders.focusRing,
                color = MaterialTheme.colorScheme.primary
            ),
            shape = shape
        )
    } else {
        this
    }
}

/**
 * Constraint Enforcer: Executive ambient background lighting mesh.
 */
fun Modifier.ambientAuraBackground(): Modifier = composed {
    this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                MaterialTheme.colorScheme.surfaceContainerLowest
            )
        )
    )
}
