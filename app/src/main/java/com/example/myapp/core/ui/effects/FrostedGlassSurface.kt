package com.example.myapp.core.ui.effects

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapp.core.ui.theme.alpha

/**
 * Standardized configuration specification for Frosted Glass surfaces.
 */
@Immutable
data class FrostedGlassStyle(
    val blurRadius: Dp = 16.dp,
    val tint: Color = Color.Unspecified,
    val borderColor: Color = Color.Unspecified,
    val cornerRadius: Dp = 20.dp
)

/**
 * Design-System Frosted Glass Surface.
 * Provides hardware-accelerated frosted glass rendering on API 31+ with graceful
 * translucent high-contrast fallback on earlier Android versions.
 */
@Composable
fun FrostedGlassSurface(
    modifier: Modifier = Modifier,
    style: FrostedGlassStyle = FrostedGlassStyle(),
    content: @Composable ColumnScope.() -> Unit
) {
    val alpha = MaterialTheme.alpha
    val shape = RoundedCornerShape(style.cornerRadius)

    val resolvedTint = if (style.tint != Color.Unspecified) {
        style.tint
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
    }

    val resolvedBorderColor = if (style.borderColor != Color.Unspecified) {
        style.borderColor
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = alpha.border)
    }

    val effectModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer {
            renderEffect = RenderEffect
                .createBlurEffect(
                    style.blurRadius.toPx(),
                    style.blurRadius.toPx(),
                    Shader.TileMode.CLAMP
                )
                .asComposeRenderEffect()
        }
    } else {
        Modifier
    }

    Surface(
        shape = shape,
        color = resolvedTint,
        border = BorderStroke(1.dp, resolvedBorderColor),
        modifier = modifier
            .clip(shape)
            .then(effectModifier)
    ) {
        Column(content = content)
    }
}
