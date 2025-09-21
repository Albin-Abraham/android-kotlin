package com.example.myapp.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Button variant types.
 */
enum class ButtonVariant {
    PRIMARY, OUTLINE, GHOST
}

/**
 * Reusable Button supporting Material 3 variants (Primary, Outline, Ghost).
 *
 * @param onClick Callback when button is clicked.
 * @param variant Button variant type.
 * @param modifier Modifier applied to the Button.
 * @param width Optional explicit width.
 * @param height Optional explicit height.
 * @param wrapContentWidth If true, Button width wraps its content.
 * @param wrapContentHeight If true, Button height wraps its content.
 * @param content Composable content inside the Button.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp? = null,
    wrapContentWidth: Boolean = false,
    wrapContentHeight: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    // Build sizing modifier
    var buttonModifier = modifier

    buttonModifier = when {
        width != null -> buttonModifier.width(width)
        wrapContentWidth -> buttonModifier.wrapContentWidth()
        else -> buttonModifier.fillMaxWidth()
    }

    buttonModifier = when {
        height != null -> buttonModifier.height(height)
        wrapContentHeight -> buttonModifier.wrapContentHeight()
        else -> buttonModifier // let Material 3 handle default height
    }

    when (variant) {
        ButtonVariant.PRIMARY -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            content = content
        )

        ButtonVariant.OUTLINE -> OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            content = content
        )

        ButtonVariant.GHOST -> TextButton(
            onClick = onClick,
            modifier = buttonModifier,
            content = content
        )
    }
}
