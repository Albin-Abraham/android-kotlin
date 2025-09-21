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
// Reusable Row Component
// ----------------------------

/**
 * A reusable Row with spacing, alignment, padding, background, and flexible sizing.
 *
 * @param modifier Modifier to apply to the Row.
 * @param horizontalSpacing Horizontal spacing between children.
 * @param verticalAlignment Vertical alignment of children.
 * @param padding Padding inside the Row.
 * @param backgroundColor Background color of the Row.
 * @param width Optional explicit width.
 * @param height Optional explicit height.
 * @param wrapContentWidth If true, Row width wraps its content.
 * @param wrapContentHeight If true, Row height wraps its content.
 * @param content Composable content inside the Row.
 */
@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 8.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    padding: Dp = 0.dp,
    backgroundColor: Color = Color.Transparent,
    width: Dp? = null,
    height: Dp? = null,
    wrapContentWidth: Boolean = false,
    wrapContentHeight: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    var rowModifier = modifier
        .background(backgroundColor)
        .padding(padding)

    rowModifier = when {
        width != null -> rowModifier.width(width)
        wrapContentWidth -> rowModifier.wrapContentWidth()
        else -> rowModifier
    }

    rowModifier = when {
        height != null -> rowModifier.height(height)
        wrapContentHeight -> rowModifier.wrapContentHeight()
        else -> rowModifier
    }

    Row(
        modifier = rowModifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalAlignment = verticalAlignment,
        content = content
    )
}