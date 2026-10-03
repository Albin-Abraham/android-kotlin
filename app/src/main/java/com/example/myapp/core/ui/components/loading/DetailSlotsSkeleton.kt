package com.example.myapp.core.ui.components.loading

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier

/**
 * Skeleton Loader matching DetailSlotsScaffold geometry.
 * Completely eliminates cascading pop-ins and layout shift while fetching data.
 */
@Composable
fun DetailSlotsSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Skeleton
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shimmer(shape = MaterialTheme.shapes.extraLarge)
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

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Key Details Card Skeleton
        AppSurface(
            tier = SurfaceTier.LOW,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shimmer(shape = MaterialTheme.shapes.large)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shimmer(shape = MaterialTheme.shapes.large)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .shimmer(shape = MaterialTheme.shapes.small)
                )
            }
        }

        // 3. Action Button Skeleton
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shimmer(shape = MaterialTheme.shapes.medium)
        )
    }
}
