package com.example.myapp.core.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class AppButtonVariant {
    PRIMARY,
    OUTLINE,
    GHOST,
    TONAL
}

/**
 * Modern Material 3 Button with built-in Loading State support & executive styling.
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    variant: AppButtonVariant = AppButtonVariant.PRIMARY,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val buttonModifier = modifier
        .fillMaxWidth()
        .height(50.dp)

    when (variant) {
        AppButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp
                ),
                modifier = buttonModifier
            ) {
                ButtonContent(isLoading = isLoading, text = text, leadingIcon = leadingIcon)
            }
        }
        AppButtonVariant.TONAL -> {
            FilledTonalButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = buttonModifier
            ) {
                ButtonContent(isLoading = isLoading, text = text, leadingIcon = leadingIcon)
            }
        }
        AppButtonVariant.OUTLINE -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = ButtonDefaults.outlinedButtonBorder(enabled = enabled && !isLoading),
                modifier = buttonModifier
            ) {
                ButtonContent(isLoading = isLoading, text = text, leadingIcon = leadingIcon)
            }
        }
        AppButtonVariant.GHOST -> {
            TextButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.large,
                modifier = buttonModifier
            ) {
                ButtonContent(isLoading = isLoading, text = text, leadingIcon = leadingIcon)
            }
        }
    }
}

@Composable
private fun ButtonContent(
    isLoading: Boolean,
    text: String,
    leadingIcon: (@Composable () -> Unit)?
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(22.dp),
            strokeWidth = 2.5.dp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}
