package com.example.myapp.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.layouts.AppBottomBar
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.core.ui.layouts.BottomNavItem
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.spacing

/**
 * Production-ready Dashboard screen with foundational design token compliance.
 */
@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    var currentTab by remember { mutableStateOf("dashboard") }

    val navItems = listOf(
        BottomNavItem(
            route = "dashboard",
            title = "Dashboard",
            icon = Icons.Default.Dashboard
        ),
        BottomNavItem(
            route = "profile",
            title = "Profile",
            icon = Icons.Default.Person
        ),
        BottomNavItem(
            route = "settings",
            title = "Settings",
            icon = Icons.Default.Settings
        )
    )

    AppScaffold(
        topBar = {
            AppTopBar(
                title = when (currentTab) {
                    "profile" -> "My Profile"
                    "settings" -> "Settings"
                    else -> "Dashboard"
                },
                actions = {
                    IconButton(onClick = onSignOut) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        bottomBar = {
            AppBottomBar(
                items = navItems,
                currentRoute = currentTab,
                onItemClick = { item -> currentTab = item.route }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = spacing.medium, vertical = spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            // Welcome Card with M3 High Tier Surface
            AppSurface(
                tier = SurfaceTier.HIGH,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(spacing.large)) {
                    Text(
                        text = "Welcome to Material 3",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(spacing.extraSmall))
                    Text(
                        text = "Unified AppScaffold with standard AppBar and AppBottom navigation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Quick Stats / Tiles Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                QuickTile(
                    title = "Features",
                    subtitle = "3 Modules",
                    icon = Icons.Default.Dashboard,
                    modifier = Modifier.weight(1f)
                )
                QuickTile(
                    title = "Profile",
                    subtitle = "Active",
                    icon = Icons.Default.Person,
                    modifier = Modifier.weight(1f)
                )
            }

            // System Status Card
            AppSurface(
                tier = SurfaceTier.LOW,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Clean Architecture + DDD",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "SOLID Principles Enforced",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text("Active", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    AppSurface(
        tier = SurfaceTier.LOW,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
