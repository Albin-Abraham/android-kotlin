package com.example.myapp.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

enum class AppThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
    DYNAMIC // Material You dynamic wallpaper colors on Android 12+
}

/**
 * Controller allowing any Screen or ViewModel to inspect or dynamically change the system theme.
 */
interface ThemeController {
    val currentMode: AppThemeMode
    fun setThemeMode(mode: AppThemeMode)
}

val LocalThemeController = staticCompositionLocalOf<ThemeController> {
    error("No ThemeController provided. Wrap your root hierarchy in MyAppTheme.")
}
