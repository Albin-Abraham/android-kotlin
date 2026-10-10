package com.example.myapp.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.layouts.AppBottomBar
import com.example.myapp.core.ui.layouts.AppColumn
import com.example.myapp.core.ui.layouts.AppRow
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.core.ui.layouts.BottomNavItem
import com.example.myapp.core.ui.layouts.rememberBottomBarScrollBehavior
import com.example.myapp.core.ui.motion.bounceClick
import com.example.myapp.core.ui.network.LiveNetworkMonitor
import com.example.myapp.core.ui.network.OfflineSyncBanner
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.spacing

/**
 * Production-ready Enterprise Dashboard screen.
 * Features auto-hiding scroll-aware bottom navigation, tactile bounce micro-interactions,
 * offline awareness, and token-aware layout primitives.
 */
@Composable
fun HomeScreen(
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val spacing = MaterialTheme.spacing
    var currentTab by remember { mutableStateOf("dashboard") }
    val scrollBehavior = rememberBottomBarScrollBehavior()

    val networkMonitor = remember { LiveNetworkMonitor(context) }
    val isOnline by networkMonitor.isOnline.collectAsState(initial = true)

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
                    else -> "Enterprise Dashboard"
                },
                actions = {
                    IconButton(
                        onClick = onSignOut,
                        modifier = Modifier
                            .size(48.dp)
                            .semantics { contentDescription = "Sign out of account" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
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
                onItemClick = { item -> currentTab = item.route },
                scrollBehavior = scrollBehavior
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    ) { innerPadding ->
        AppColumn(
            spacing = spacing.medium,
            enableFontOverlayProtection = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacing.medium, vertical = spacing.medium)
        ) {
            // 0. Animated Offline Sync Alert
            OfflineSyncBanner(isOnline = isOnline)

            // 1. Welcome & Overview Card (M3 High Tier Surface)
            AppSurface(
                tier = SurfaceTier.HIGH,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(spacing.large)) {
                    AppRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Welcome, Enterprise Admin",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(spacing.extraSmall))
                            Text(
                                text = "ZenOS Design System & Clean Architecture",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            // 2. Metrics & KPI Row with Tactile Bounce Click
            AppRow(
                spacing = spacing.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricCard(
                    title = "Tokens Active",
                    value = "14 / 14",
                    icon = Icons.Default.Layers,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Security Score",
                    value = "100%",
                    icon = Icons.Default.Security,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "FPS / P99",
                    value = "60 fps",
                    icon = Icons.Default.Speed,
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. System Health & Architectural Compliance Card
            AppSurface(
                tier = SurfaceTier.LOW,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Clean Architecture + DDD-Lite",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Invariants, Strategy Patterns & SOLID enforced",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Semantic Status Indicator (Icon + Label, not color alone)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.semantics {
                            contentDescription = "Architecture Status: Active and Compliant"
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = spacing.small, vertical = spacing.extraSmall),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // 4. Quick Feature Modules
            Text(
                text = "Module Quick Access",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = spacing.extraSmall)
            )

            AppRow(
                spacing = spacing.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickModuleTile(
                    title = "Authentication",
                    subtitle = "MFA & Biometrics",
                    icon = Icons.Default.Lock,
                    modifier = Modifier.weight(1f)
                )
                QuickModuleTile(
                    title = "Design Studio",
                    subtitle = "Token Inspector",
                    icon = Icons.Default.Layers,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    AppSurface(
        tier = SurfaceTier.LOW,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.bounceClick { /* Opens metric details */ }
    ) {
        Column(
            modifier = Modifier.padding(spacing.small),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickModuleTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    AppSurface(
        tier = SurfaceTier.LOW,
        shape = MaterialTheme.shapes.large,
        modifier = modifier.bounceClick { /* Opens feature module */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.10f))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
