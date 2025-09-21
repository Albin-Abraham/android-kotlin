//package com.example.myapp.ui.theme
//
//import android.app.Activity
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//
//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
//@Composable
//fun MyAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}


//=========================================================================

package com.example.myapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

// 1. Colors
val PrimaryColor = Color(0xFF6200EE)
val PrimaryVariantColor = Color(0xFF3700B3)
val SecondaryColor = Color(0xFF03DAC6)
val BackgroundColor = Color(0xFFF2F2F2)
val SurfaceColor = Color.White
val ErrorColor = Color(0xFFB00020)
val OnPrimaryColor = Color.White
val OnBackgroundColor = Color(0xFF333333)
val OnSurfaceColor = Color(0xFF333333)
val OnErrorColor = Color.White

// 2. Typography
val AppTypography = Typography(
    displayLarge = TextStyle(fontSize = 57.sp, letterSpacing = 0.sp),
    headlineMedium = TextStyle(fontSize = 28.sp, letterSpacing = 0.sp),
    titleLarge = TextStyle(fontSize = 22.sp, letterSpacing = 0.sp),
    bodyMedium = TextStyle(fontSize = 16.sp, letterSpacing = 0.5.sp),
    labelLarge = TextStyle(fontSize = 14.sp, letterSpacing = 0.5.sp)
)

// 3. Shapes
val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

// 4. Spacing (design tokens)
object AppSpacing {
    val tiny = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

// 5. Borders
object AppBorders {
    val thin = 1.dp
    val medium = 2.dp
    val thick = 4.dp
}

// 6. Theme
@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = PrimaryColor,
        onPrimary = OnPrimaryColor,
        secondary = SecondaryColor,
        background = BackgroundColor,
        surface = SurfaceColor,
        error = ErrorColor,
        onBackground = OnBackgroundColor,
        onSurface = OnSurfaceColor,
        onError = OnErrorColor
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
