package com.example.myapp.features.auth.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.alpha
import com.example.myapp.core.ui.theme.breakpoints
import com.example.myapp.core.ui.theme.elevations
import com.example.myapp.core.ui.theme.spacing

/**
 * Standardized enterprise card container for authentication views.
 * Enforces consistent elevation, responsive max-width, border stroke, and internal padding.
 */
@Composable
fun AuthCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val spacing = MaterialTheme.spacing
    val alpha = MaterialTheme.alpha
    val elevations = MaterialTheme.elevations
    val breakpoints = MaterialTheme.breakpoints

    AppSurface(
        tier = SurfaceTier.LOW,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = alpha.border)
        ),
        shadowElevation = elevations.level2,
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = breakpoints.maxCardWidth)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.large, vertical = spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}
