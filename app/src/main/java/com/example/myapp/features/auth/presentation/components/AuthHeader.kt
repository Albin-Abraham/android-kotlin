package com.example.myapp.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.alpha
import com.example.myapp.core.ui.theme.elevations
import com.example.myapp.core.ui.theme.iconSizes
import com.example.myapp.core.ui.theme.spacing

/**
 * Standardized Header component for Auth workflows (Login, SignUp, ForgotPassword).
 * Combines branded halo icon badge, semantic title, and supporting subtitle.
 */
@Composable
fun AuthHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Lock,
    iconContentDescription: String? = null
) {
    val spacing = MaterialTheme.spacing
    val iconSizes = MaterialTheme.iconSizes
    val alpha = MaterialTheme.alpha
    val elevations = MaterialTheme.elevations

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Halo Icon Badge
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
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
                        imageVector = icon,
                        contentDescription = iconContentDescription,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(iconSizes.large)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        // Titles
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(spacing.extraSmall))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
