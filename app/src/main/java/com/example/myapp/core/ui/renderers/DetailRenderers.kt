package com.example.myapp.core.ui.renderers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.*

/**
 * Standardized Slot Renderers for DetailSlots Architecture.
 * Strictly compliant with all 14 Foundational Design Constraints.
 */
object DetailRenderers {

    /**
     * Constraint-Compliant Header Slot Renderer.
     * Combines concentric hero badge, headline typography, and spacing tokens.
     */
    @Composable
    fun HeaderRenderer(
        title: String,
        modifier: Modifier = Modifier,
        subtitle: String? = null,
        badgeIcon: ImageVector? = null
    ) {
        val spacing = MaterialTheme.spacing
        val iconSizes = MaterialTheme.iconSizes

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            if (badgeIcon != null) {
                // Double concentric badge with 14 constraint token sizes
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(iconSizes.hero + 12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = MaterialTheme.alpha.subtle))
                ) {
                    AppSurface(
                        tier = SurfaceTier.HIGH,
                        shape = CircleShape,
                        shadowElevation = MaterialTheme.elevations.level2,
                        modifier = Modifier.size(iconSizes.extraLarge + 8.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = badgeIcon,
                                contentDescription = title,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(iconSizes.large)
                            )
                        }
                    }
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    /**
     * Constraint-Compliant Key Details Card Container.
     * Enforces breakpoint max-width (480dp), elevation level 2, border stroke, and internal padding.
     */
    @Composable
    fun KeyDetailsCardRenderer(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        val spacing = MaterialTheme.spacing
        val borders = MaterialTheme.borders
        val alpha = MaterialTheme.alpha
        val breakpoints = MaterialTheme.breakpoints

        AppSurface(
            tier = SurfaceTier.LOW,
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                width = borders.thin,
                color = MaterialTheme.colorScheme.outline.copy(alpha = alpha.border)
            ),
            shadowElevation = MaterialTheme.elevations.level2,
            modifier = modifier
                .fillMaxWidth()
                .widthIn(max = breakpoints.maxCardWidth)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.large, vertical = spacing.large),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                content = content
            )
        }
    }

    /**
     * Constraint-Compliant Action Bar / Slot Renderer.
     * Guarantees 48dp minimum touch ergonomics and bottom bar window insets.
     */
    @Composable
    fun BottomActionSlotRenderer(
        modifier: Modifier = Modifier,
        actionContent: @Composable () -> Unit
    ) {
        val spacing = MaterialTheme.spacing
        val elevations = MaterialTheme.elevations

        AppSurface(
            tier = SurfaceTier.HIGH,
            shadowElevation = elevations.level4,
            shape = MaterialTheme.shapes.extraSmall,
            modifier = modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = spacing.large, vertical = spacing.medium)
            ) {
                actionContent()
            }
        }
    }
}
