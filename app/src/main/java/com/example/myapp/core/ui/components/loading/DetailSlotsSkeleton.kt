package com.example.myapp.core.ui.components.loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.breakpoints
import com.example.myapp.core.ui.theme.density
import com.example.myapp.core.ui.theme.elevations
import com.example.myapp.core.ui.theme.spacing

/**
 * 14-Constraint Skeleton Loader matching DetailSlotsScaffold geometry.
 * Completely eliminates cascading pop-ins and layout shift while fetching data.
 */
@Composable
fun DetailSlotsSkeleton(
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    val density = MaterialTheme.density
    val breakpoints = MaterialTheme.breakpoints

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing.medium, vertical = spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        // 1. Header Skeleton with concentric halo geometry
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .shimmer(shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(28.dp)
                    .shimmer(shape = MaterialTheme.shapes.small)
            )
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(16.dp)
                    .shimmer(shape = MaterialTheme.shapes.small)
            )
        }

        Spacer(modifier = Modifier.height(spacing.extraSmall))

        // 2. Key Details Card Skeleton matching 480dp max breakpoint constraint
        AppSurface(
            tier = SurfaceTier.LOW,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = MaterialTheme.elevations.level2,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = breakpoints.maxCardWidth)
        ) {
            Column(
                modifier = Modifier
                    .padding(spacing.large)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(density.inputHeightDefault)
                        .shimmer(shape = RoundedCornerShape(14.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(density.inputHeightDefault)
                        .shimmer(shape = RoundedCornerShape(14.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .shimmer(shape = MaterialTheme.shapes.small)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(density.buttonHeightLarge)
                        .shimmer(shape = MaterialTheme.shapes.large)
                )
            }
        }
    }
}
