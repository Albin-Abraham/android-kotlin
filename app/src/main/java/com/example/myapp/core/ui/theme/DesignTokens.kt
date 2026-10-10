package com.example.myapp.core.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ============================================================================
// 14 FOUNDATIONAL DESIGN SYSTEM PILLARS
// ============================================================================

// 1. Spacing & Layout Grid Tokens
@Immutable
data class SpacingTokens(
    val none: Dp = 0.dp,
    val extraExtraSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val extraExtraLarge: Dp = 48.dp,
    val huge: Dp = 64.dp
)

// 2. Elevation & Depth Tokens
@Immutable
data class ElevationTokens(
    val level0: Dp = 0.dp,
    val level1: Dp = 1.dp,
    val level2: Dp = 3.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 12.dp
)

// 3. Border & Stroke Width Tokens
@Immutable
data class BorderTokens(
    val none: Dp = 0.dp,
    val hairline: Dp = 0.5.dp,
    val thin: Dp = 1.dp,
    val regular: Dp = 1.5.dp,
    val thick: Dp = 2.dp,
    val focusRing: Dp = 2.5.dp
)

// 4. Icon Sizing Tokens
@Immutable
data class IconSizeTokens(
    val micro: Dp = 12.dp,
    val small: Dp = 16.dp,
    val medium: Dp = 20.dp,
    val standard: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 48.dp,
    val hero: Dp = 64.dp
)

// 5. Opacity & Alpha Transparency Tokens
@Immutable
data class AlphaTokens(
    val transparent: Float = 0f,
    val hover: Float = 0.04f,
    val subtle: Float = 0.08f,
    val focus: Float = 0.12f,
    val border: Float = 0.25f,
    val disabled: Float = 0.38f,
    val muted: Float = 0.60f,
    val prominent: Float = 0.87f,
    val opaque: Float = 1f
)

// 6. Motion & Animation Timing Tokens
@Immutable
data class MotionTokens(
    val durationInstant: Int = 50,
    val durationShort: Int = 150,
    val durationMedium: Int = 300,
    val durationLong: Int = 500,
    val durationExtraLong: Int = 800,
    val easingStandard: Easing = FastOutSlowInEasing,
    val easingDecelerate: Easing = LinearOutSlowInEasing,
    val easingEmphasized: Easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
)

// 7. Breakpoints & Responsive Constraints Tokens
@Immutable
data class BreakpointTokens(
    val compactMax: Dp = 599.dp,
    val mediumMax: Dp = 839.dp,
    val expandedMin: Dp = 840.dp,
    val maxCardWidth: Dp = 480.dp,
    val maxModalWidth: Dp = 560.dp,
    val maxContentWidth: Dp = 1200.dp
)

// 8. Touch Targets & Accessibility Tokens
@Immutable
data class AccessibilityTokens(
    val minTouchTarget: Dp = 48.dp,
    val smallTouchTarget: Dp = 40.dp,
    val minContrastRatio: Float = 4.5f
)

// 9. Component Sizing & Density Tokens
@Immutable
data class DensityTokens(
    val buttonHeightSmall: Dp = 36.dp,
    val buttonHeightDefault: Dp = 48.dp,
    val buttonHeightLarge: Dp = 52.dp,
    val inputHeightDefault: Dp = 56.dp,
    val topBarHeight: Dp = 64.dp,
    val bottomBarHeight: Dp = 80.dp
)

// 10. Feedback & Status Colors Tokens
@Immutable
data class StatusTokens(
    val success: Color = Color(0xFF16A34A),
    val onSuccess: Color = Color(0xFFFFFFFF),
    val successContainer: Color = Color(0xFFDCFCE7),
    val onSuccessContainer: Color = Color(0xFF14532D),
    val warning: Color = Color(0xFFD97706),
    val onWarning: Color = Color(0xFFFFFFFF),
    val warningContainer: Color = Color(0xFFFEF3C7),
    val onWarningContainer: Color = Color(0xFF78350F),
    val info: Color = Color(0xFF0284C7),
    val onInfo: Color = Color(0xFFFFFFFF),
    val infoContainer: Color = Color(0xFFE0F2FE),
    val onInfoContainer: Color = Color(0xFF075985)
)

// ============================================================================
// COMPOSITION LOCALS
// ============================================================================

val LocalSpacing = staticCompositionLocalOf { SpacingTokens() }
val LocalElevation = staticCompositionLocalOf { ElevationTokens() }
val LocalBorders = staticCompositionLocalOf { BorderTokens() }
val LocalIconSizes = staticCompositionLocalOf { IconSizeTokens() }
val LocalAlpha = staticCompositionLocalOf { AlphaTokens() }
val LocalMotion = staticCompositionLocalOf { MotionTokens() }
val LocalBreakpoints = staticCompositionLocalOf { BreakpointTokens() }
val LocalAccessibility = staticCompositionLocalOf { AccessibilityTokens() }
val LocalDensity = staticCompositionLocalOf { DensityTokens() }
val LocalStatus = staticCompositionLocalOf { StatusTokens() }

// ============================================================================
// MATERIAL THEME EXTENSIONS
// ============================================================================

val MaterialTheme.spacing: SpacingTokens
    @Composable get() = LocalSpacing.current

val MaterialTheme.elevations: ElevationTokens
    @Composable get() = LocalElevation.current

val MaterialTheme.borders: BorderTokens
    @Composable get() = LocalBorders.current

val MaterialTheme.iconSizes: IconSizeTokens
    @Composable get() = LocalIconSizes.current

val MaterialTheme.alpha: AlphaTokens
    @Composable get() = LocalAlpha.current

val MaterialTheme.motion: MotionTokens
    @Composable get() = LocalMotion.current

val MaterialTheme.breakpoints: BreakpointTokens
    @Composable get() = LocalBreakpoints.current

val MaterialTheme.accessibility: AccessibilityTokens
    @Composable get() = LocalAccessibility.current

val MaterialTheme.density: DensityTokens
    @Composable get() = LocalDensity.current

val MaterialTheme.status: StatusTokens
    @Composable get() = LocalStatus.current
