package com.example.myapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


// ----------------------------
// Reusable Spacer Component
// ----------------------------

/**
 * A flexible spacer that can have optional width and height.
 *
 * @param width Optional width of the spacer.
 * @param height Optional height of the spacer.
 */
@Composable
fun AppSpacer(
    width: Dp? = null,
    height: Dp? = null
) {
    Spacer(
        modifier = Modifier.then(
            if (width != null || height != null) Modifier.size(
                width = width ?: Dp.Unspecified,
                height = height ?: Dp.Unspecified
            ) else Modifier
        )
    )
}