package com.example.myapp.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapp.core.domain.util.UiText

/**
 * Resolves [UiText] inside Composable scopes with localization support.
 */
@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResource -> {
            if (args.isEmpty()) {
                stringResource(id = resId)
            } else {
                stringResource(id = resId, *args.toTypedArray())
            }
        }
    }
}
