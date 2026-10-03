package com.example.myapp.core.domain.validation

import com.example.myapp.core.domain.util.UiText

/**
 * Result of a validation operation (Clean Architecture Value Object).
 */
sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Invalid(val errorMessage: UiText) : ValidationResult {
        constructor(message: String) : this(UiText.raw(message))
    }

    val isValid: Boolean get() = this is Valid
    val errorOrNull: UiText? get() = (this as? Invalid)?.errorMessage
}
