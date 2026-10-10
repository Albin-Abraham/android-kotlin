package com.example.myapp.core.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Window width size classifications aligned with Material 3 adaptive guidelines.
 */
enum class AdaptiveWindowSizeClass {
    COMPACT,   // Phones in portrait (< 600dp)
    MEDIUM,    // Small tablets, foldables, phones in landscape (600dp - 839dp)
    EXPANDED   // Tablets, desktop-sized windows (>= 840dp)
}

/**
 * Calculates current [AdaptiveWindowSizeClass] based on the current window configuration width.
 */
@Composable
fun calculateAdaptiveWindowSizeClass(): AdaptiveWindowSizeClass {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    return when {
        screenWidthDp < 600.dp -> AdaptiveWindowSizeClass.COMPACT
        screenWidthDp < 840.dp -> AdaptiveWindowSizeClass.MEDIUM
        else -> AdaptiveWindowSizeClass.EXPANDED
    }
}

/**
 * Adaptive Master-Detail / Multi-Pane Layout Scaffold.
 * Automatically adapts between single-pane stack navigation (Compact)
 * and two-pane side-by-side view (Medium/Expanded) while preserving selection state.
 */
@Composable
fun <T> AdaptiveMultiPaneScaffold(
    selectedItem: T?,
    onSelectItem: (T) -> Unit,
    masterContent: @Composable (selected: T?, onSelect: (T) -> Unit) -> Unit,
    detailContent: @Composable (item: T) -> Unit,
    emptyDetailContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    windowSizeClass: AdaptiveWindowSizeClass = calculateAdaptiveWindowSizeClass()
) {
    when (windowSizeClass) {
        AdaptiveWindowSizeClass.COMPACT -> {
            // Single-pane presentation
            Box(modifier = modifier.fillMaxSize()) {
                if (selectedItem != null) {
                    detailContent(selectedItem)
                } else {
                    masterContent(null, onSelectItem)
                }
            }
        }

        AdaptiveWindowSizeClass.MEDIUM, AdaptiveWindowSizeClass.EXPANDED -> {
            // Dual-pane side-by-side presentation
            Row(modifier = modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .width(if (windowSizeClass == AdaptiveWindowSizeClass.MEDIUM) 320.dp else 380.dp)
                        .fillMaxHeight()
                ) {
                    masterContent(selectedItem, onSelectItem)
                }

                VerticalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    if (selectedItem != null) {
                        detailContent(selectedItem)
                    } else {
                        emptyDetailContent()
                    }
                }
            }
        }
    }
}
