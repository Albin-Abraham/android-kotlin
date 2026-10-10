package com.example.myapp.core.ui.state

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapp.core.ui.components.loading.DetailSlotsSkeleton

/**
 * Polymorphic Screen State model for resilient UI rendering.
 */
sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>
    data class Empty(
        val title: String = "No Data Available",
        val message: String = "There are no records to display at this time.",
        val actionText: String? = null,
        val onAction: (() -> Unit)? = null
    ) : ScreenState<Nothing>
    data class Error(
        val message: String,
        val onRetry: (() -> Unit)? = null
    ) : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
}

/**
 * Standardized State Container Composable.
 * Seamlessly manages smooth cross-fades between Loading, Empty, Error, and Success states.
 */
@Composable
fun <T> UiStateRenderer(
    state: ScreenState<T>,
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit = { DetailSlotsSkeleton() },
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(200))
        },
        label = "UiStateRendererTransition",
        modifier = modifier
    ) { targetState ->
        when (targetState) {
            is ScreenState.Loading -> loadingContent()
            is ScreenState.Empty -> EmptyStateWidget(
                title = targetState.title,
                message = targetState.message,
                actionText = targetState.actionText,
                onAction = targetState.onAction
            )
            is ScreenState.Error -> ErrorRetryBanner(
                errorMessage = targetState.message,
                onRetry = targetState.onRetry
            )
            is ScreenState.Success -> content(targetState.data)
        }
    }
}
