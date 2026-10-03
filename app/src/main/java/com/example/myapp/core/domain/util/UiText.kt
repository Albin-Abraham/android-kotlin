package com.example.myapp.core.domain.util

/**
 * Clean Architecture Value Object for Localized UI Text.
 * Decouples domain logic from Android Context/R.string while enabling 100% i18n support.
 */
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringResource(val resId: Int, val args: List<Any> = emptyList()) : UiText

    companion object {
        fun raw(text: String): UiText = DynamicString(text)
        fun res(resId: Int, vararg args: Any): UiText = StringResource(resId, args.toList())
    }
}
