package com.example.myapp.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.alpha
import com.example.myapp.core.ui.theme.elevations
import com.example.myapp.core.ui.theme.iconSizes

/**
 * Concentric halo security badge with decorative a11y optimization.
 */
@Composable
fun SecurityBadge(
    modifier: Modifier = Modifier
) {
    val iconSizes = MaterialTheme.iconSizes
    val alpha = MaterialTheme.alpha
    val elevations = MaterialTheme.elevations

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(iconSizes.hero + 12.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha.subtle))
    ) {
        AppSurface(
            tier = SurfaceTier.HIGH,
            shape = CircleShape,
            shadowElevation = elevations.level2,
            modifier = Modifier.size(iconSizes.extraLarge + 8.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null, // Decorative icon - context is conveyed by adjacent heading
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(iconSizes.large)
                )
            }
        }
    }
}
