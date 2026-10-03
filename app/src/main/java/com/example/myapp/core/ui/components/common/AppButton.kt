package com.example.myapp.core.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class AppButtonVariant {
    PRIMARY,
    OUTLINE,
    GHOST
}

/**
 * Modern Material 3 Button with built-in Loading State support.
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
        .height(48.dp)

    when (variant) {
        AppButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
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
                shape = MaterialTheme.shapes.medium,
                modifier = buttonModifier
            ) {
                ButtonContent(isLoading = isLoading, text = text, leadingIcon = leadingIcon)
            }
        }
        AppButtonVariant.GHOST -> {
            TextButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                shape = MaterialTheme.shapes.medium,
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
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp,
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
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}
