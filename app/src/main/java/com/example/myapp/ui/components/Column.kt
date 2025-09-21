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
// Reusable Column Component
// ----------------------------

/**
 * A reusable Column with spacing, alignment, padding, background, and flexible sizing.
 *
 * @param modifier Modifier to apply to the Column.
 * @param verticalSpacing Vertical spacing between children.
 * @param horizontalAlignment Horizontal alignment of children.
 * @param padding Padding inside the Column.
 * @param backgroundColor Background color of the Column.
 * @param width Optional explicit width.
 * @param height Optional explicit height.
 * @param wrapContentWidth If true, Column width wraps its content.
 * @param wrapContentHeight If true, Column height wraps its content.
 * @param content Composable content inside the Column.
 */
@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    verticalSpacing: Dp = 8.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    padding: Dp = 0.dp,
    backgroundColor: Color = Color.Transparent,
    width: Dp? = null,
    height: Dp? = null,
    wrapContentWidth: Boolean = false,
    wrapContentHeight: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var columnModifier = modifier
        .background(backgroundColor)
        .padding(padding)

    columnModifier = when {
        width != null -> columnModifier.width(width)
        wrapContentWidth -> columnModifier.wrapContentWidth()
        else -> columnModifier
    }

    columnModifier = when {
        height != null -> columnModifier.height(height)
        wrapContentHeight -> columnModifier.wrapContentHeight()
        else -> columnModifier
    }

    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}