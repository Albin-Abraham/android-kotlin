package com.example.myapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * TextField variant types.
 */
enum class TextFieldVariant {
    OUTLINE
}

/**
 * A reusable TextField with multiple variants: Outline.
 *
 * @param value Text value of the field.
 * @param onValueChange Callback when the text changes.
 * @param variant TextFieldVariant type (OUTLINE)
 * @param modifier Modifier applied to the TextField container.
 * @param padding Inner padding of the TextField.
 * @param width Optional explicit width.
 * @param height Optional explicit height.
 * @param wrapContentWidth Wrap width to content.
 * @param wrapContentHeight Wrap height to content.
 * @param textStyle Style for the input text.
 * @param placeholder Optional placeholder text.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    variant: TextFieldVariant = TextFieldVariant.OUTLINE,
    modifier: Modifier = Modifier,
    padding: Dp = 8.dp,
    width: Dp? = null,
    height: Dp? = null,
    wrapContentWidth: Boolean = false,
    wrapContentHeight: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null
) {
    var fieldModifier = modifier.padding(padding)

    fieldModifier = when {
        width != null -> fieldModifier.width(width)
        wrapContentWidth -> fieldModifier.wrapContentWidth()
        else -> fieldModifier
    }

    fieldModifier = when {
        height != null -> fieldModifier.height(height)
        wrapContentHeight -> fieldModifier.wrapContentHeight()
        else -> fieldModifier
    }

    fieldModifier = when (variant) {
        TextFieldVariant.OUTLINE -> fieldModifier
            .border(1.dp, Color.Gray)
            .background(Color.Transparent)
    }

    Box(modifier = fieldModifier, contentAlignment = Alignment.CenterStart) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                if (value.isEmpty() && placeholder != null) {
                    Text(text = placeholder, style = textStyle.copy(color = Color.Gray))
                }
                innerTextField()
            }
        )
    }
}