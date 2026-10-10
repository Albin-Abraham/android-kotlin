package com.example.myapp.core.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier

/**
 * Navigation item specification for [AppBottomBar].
 */
data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int? = null
)

/**
 * Standardized Material 3 Top App Bar (AppBar).
 * Provides consistent typography, tonal surface coloring, and navigation/action slots.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back"
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
    )
}

/**
 * Standardized Material 3 Bottom Navigation Bar (AppBottom).
 * Enforces M3 navigation bar styling and container tier elevation.
 */
@Composable
fun AppBottomBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    AppSurface(
        tier = SurfaceTier.HIGH,
        shadowElevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        if (item.badgeCount != null && item.badgeCount > 0) {
                            BadgedBox(badge = { Badge { Text("${item.badgeCount}") } }) {
                                Icon(item.icon, contentDescription = item.title)
                            }
                        } else {
                            Icon(item.icon, contentDescription = item.title)
                        }
                    },
                    label = { Text(item.title, style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

/**
 * Universal AppScaffold providing standard M3 AppBar and AppBottom slot orchestration.
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: (@Composable () -> Unit)? = null,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { topBar?.invoke() },
        bottomBar = { bottomBar?.invoke() },
        snackbarHost = snackbarHost,
        floatingActionButton = { floatingActionButton?.invoke() },
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier,
        content = content
    )
}
