package com.example.myapp.core.ui.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.effects.FrostedGlassStyle
import com.example.myapp.core.ui.effects.FrostedGlassSurface
import com.example.myapp.core.ui.layouts.AppColumn
import com.example.myapp.core.ui.layouts.AppRow
import com.example.myapp.core.ui.motion.bounceClick
import com.example.myapp.core.ui.state.ErrorRetryBanner
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.spacing

/**
 * In-App Design System & Component Catalog for Developer Verification.
 */
@Composable
fun ComponentCatalogScreen(
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    var buttonLoading by remember { mutableStateOf(false) }

    AppColumn(
        spacing = spacing.medium,
        enableFontOverlayProtection = true,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium)
    ) {
        // Section Header
        Text(
            text = "ZenOS Design System Catalog",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Live preview of buttons, surfaces, layout primitives, and micro-interactions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = spacing.small))

        // 1. Buttons & Variants
        Text(
            text = "1. AppButton Variants",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        AppRow(spacing = spacing.small) {
            AppButton(
                text = "Primary Button",
                onClick = { buttonLoading = !buttonLoading },
                isLoading = buttonLoading,
                variant = AppButtonVariant.PRIMARY,
                modifier = Modifier.weight(1f)
            )
            AppButton(
                text = "Outline Button",
                onClick = { buttonLoading = !buttonLoading },
                variant = AppButtonVariant.OUTLINE,
                modifier = Modifier.weight(1f)
            )
        }

        // 2. Surface Tiers (5-Tier M3 Container System)
        Text(
            text = "2. Material 3 Surface Containers",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = spacing.small)
        )

        AppRow(spacing = spacing.small) {
            AppSurface(
                tier = SurfaceTier.LOW,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Surface Tier LOW",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(spacing.small)
                )
            }
            AppSurface(
                tier = SurfaceTier.HIGH,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Surface Tier HIGH",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(spacing.small)
                )
            }
        }

        // 3. Frosted Glass Surface
        Text(
            text = "3. Frosted Glass Surface (RenderEffect / Fallback)",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = spacing.small)
        )

        FrostedGlassSurface(
            style = FrostedGlassStyle(blurRadius = 20.dp, cornerRadius = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(spacing.medium)) {
                Text(
                    text = "Hardware-Accelerated Frosted Glass",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = "Seamlessly falls back to translucent contrast scrim on API < 31.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 4. Tactile Micro-Interactions
        Text(
            text = "4. Tactile Bounce Micro-Interaction",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = spacing.small)
        )

        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .bounceClick { /* Interactive bounce test */ }
        ) {
            Row(
                modifier = Modifier.padding(spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = "Press Me to test .bounceClick() spring physics & haptics",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )
            }
        }

        // 5. Error & Retry State Banner
        Text(
            text = "5. Resilient State Banner",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = spacing.small)
        )

        ErrorRetryBanner(
            errorMessage = "Sample network synchronization timeout",
            onRetry = { /* Retry handler */ }
        )
    }
}
