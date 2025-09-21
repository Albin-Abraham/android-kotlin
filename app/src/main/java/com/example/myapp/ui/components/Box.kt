package com.example.myapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// ----------------------------
// Reusable Box Component
// ----------------------------

/**
 * A reusable Box with optional padding, background, alignment, sizing, and content.
 *
 * @param modifier Modifier to apply to the Box.
 * @param padding Padding inside the Box.
 * @param backgroundColor Background color of the Box.
 * @param contentAlignment Alignment of the Box content.
 * @param width Optional explicit width.
 * @param height Optional explicit height.
 * @param wrapContentWidth If true, Box width wraps its content.
 * @param wrapContentHeight If true, Box height wraps its content.
 * @param content Composable content inside the Box.
 */
@Composable
fun AppBox(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    backgroundColor: Color = Color.Transparent, // must be non-null Color
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .padding(padding),
        contentAlignment = contentAlignment,
        content = content
    )
}