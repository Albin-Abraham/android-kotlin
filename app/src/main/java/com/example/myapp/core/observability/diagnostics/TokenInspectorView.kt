package com.example.myapp.core.observability.diagnostics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.spacing

/**
 * Live Design Token Resolution Inspector.
 * Displays the token resolution path (Primitive Token -> Semantic Token -> Component Token).
 */
@Composable
fun TokenInspectorView(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    AppSurface(
        tier = SurfaceTier.HIGHEST,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 16.dp,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    Icon(
                        imageVector = Icons.Default.Layers,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "ZenOS Token Resolution Inspector",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Inspector")
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = spacing.small))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                item {
                    TokenResolutionCard(
                        category = "Spacing Tokens",
                        primitive = "16.dp",
                        semantic = "MaterialTheme.spacing.medium",
                        component = "AppColumn.contentPadding"
                    )
                }
                item {
                    TokenResolutionCard(
                        category = "Elevation Tokens",
                        primitive = "3.dp",
                        semantic = "SurfaceTier.LOW",
                        component = "AuthCard.shadowElevation"
                    )
                }
                item {
                    TokenResolutionCard(
                        category = "Color Palette",
                        primitive = "Color(0xFF2563EB)",
                        semantic = "MaterialTheme.colorScheme.primary",
                        component = "AppButton(PRIMARY).containerColor"
                    )
                }
                item {
                    TokenResolutionCard(
                        category = "Shape Tokens",
                        primitive = "24.dp RoundedCorner",
                        semantic = "MaterialTheme.shapes.extraLarge",
                        component = "AppSurface(HIGH).shape"
                    )
                }
            }
        }
    }
}

@Composable
private fun TokenResolutionCard(
    category: String,
    primitive: String,
    semantic: String,
    component: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Primitive:  $primitive",
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Semantic:   $semantic",
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Component:  $component",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
