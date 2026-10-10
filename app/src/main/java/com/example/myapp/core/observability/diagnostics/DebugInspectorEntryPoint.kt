package com.example.myapp.core.observability.diagnostics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * Developer Diagnostics Entry Point.
 * Provides a discoverable floating developer action button alongside a 3-finger gesture shortcut.
 */
@Composable
fun DebugInspectorEntryPoint(
    controller: DeveloperDiagnosticsController,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (!controller.isAvailable()) {
        content()
        return
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { /* Detects touch interactions */ },
                    onDoubleTap = {
                        // Double tap with shortcut toggle
                        controller.toggle()
                    }
                )
            }
    ) {
        content()

        // Discoverable Floating Tool Button
        FloatingActionButton(
            onClick = { controller.toggle() },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
                .size(44.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BugReport,
                contentDescription = "Open Developer Diagnostics",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
