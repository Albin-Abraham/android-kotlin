package com.example.myapp.core.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Responsive / Adaptive layout helper handling dynamic orientations and split panes.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdaptiveTwoPaneLayout(
    isTwoPane: Boolean,
    primaryContent: @Composable () -> Unit,
    secondaryContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    spacing: Dp = 16.dp
) {
    if (isTwoPane) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                primaryContent()
            }
            Box(modifier = Modifier.weight(1f)) {
                secondaryContent()
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            primaryContent()
            secondaryContent()
        }
    }
}
