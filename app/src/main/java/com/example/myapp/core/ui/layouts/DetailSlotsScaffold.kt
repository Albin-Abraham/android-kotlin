package com.example.myapp.core.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier

/**
 * DetailSlots Pattern Scaffold.
 * Standardizes slot-based screen layouts across multiple features with M3 Expressive container hierarchy.
 * Follows GoF Template Method / Slot Pattern.
 */
@Composable
fun DetailSlotsScaffold(
    topBarTitle: String,
    onBackClick: (() -> Unit)? = null,
    headerSlot: (@Composable () -> Unit)? = null,
    mediaSlot: (@Composable () -> Unit)? = null,
    keyDetailsSlot: @Composable () -> Unit,
    secondaryContentSlot: (@Composable () -> Unit)? = null,
    actionSlot: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = topBarTitle,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            if (actionSlot != null) {
                AppSurface(
                    tier = SurfaceTier.HIGH,
                    shadowElevation = 8.dp,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        actionSlot()
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Slot 1: Header
            headerSlot?.invoke()

            // Slot 2: Media / Hero Banner
            mediaSlot?.invoke()

            // Slot 3: Key Details / Form (Wrapped in M3 Elevated Surface)
            AppSurface(
                tier = SurfaceTier.LOW,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(20.dp)) {
                    keyDetailsSlot()
                }
            }

            // Slot 4: Secondary Content
            secondaryContentSlot?.invoke()
        }
    }
}
