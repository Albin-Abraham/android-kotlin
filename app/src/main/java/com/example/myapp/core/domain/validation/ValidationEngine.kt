package com.example.myapp.core.domain.validation

import com.example.myapp.core.domain.util.UiText

/**
 * Single Validation Rule Contract.
 * Follows GoF Strategy Pattern & SOLID Single Responsibility.
 */
fun interface ValidationRule<T> {
    fun validate(value: T, allValues: Map<String, Any?>): ValidationResult

    fun validate(value: T): ValidationResult = validate(value, emptyMap())

    infix fun and(other: ValidationRule<T>): ValidationRule<T> = ValidationRule { value, allValues ->
        val firstResult = this.validate(value, allValues)
        if (!firstResult.isValid) firstResult else other.validate(value, allValues)
    }

    infix fun or(other: ValidationRule<T>): ValidationRule<T> = ValidationRule { value, allValues ->
        val firstResult = this.validate(value, allValues)
        if (firstResult.isValid) firstResult else other.validate(value, allValues)
    }
}

/**
 * Standard Reusable Fluent Rules.
 */
object Rules {

    fun notBlank(
        errorMessage: UiText = UiText.raw("This field is required")
    ): ValidationRule<Any?> = ValidationRule { value, _ ->
        val isInvalid = when (value) {
            null -> true
            is String -> value.trim().isBlank()
            else -> false
        }
        if (isInvalid) ValidationResult.Invalid(errorMessage) else ValidationResult.Valid
    }

    fun minLength(
        min: Int,
        errorMessage: UiText = UiText.raw("Must be at least $min characters")
    ): ValidationRule<Any?> = ValidationRule { value, _ ->
        val str = value as? String ?: return@ValidationRule ValidationResult.Valid
        if (str.length < min) ValidationResult.Invalid(errorMessage) else ValidationResult.Valid
    }

    fun maxLength(
        max: Int,
        errorMessage: UiText = UiText.raw("Must not exceed $max characters")
    ): ValidationRule<Any?> = ValidationRule { value, _ ->
        val str = value as? String ?: return@ValidationRule ValidationResult.Valid
        if (str.length > max) ValidationResult.Invalid(errorMessage) else ValidationResult.Valid
    }

    fun email(
        errorMessage: UiText = UiText.raw("Please enter a valid email address")
    ): ValidationRule<Any?> = ValidationRule { value, _ ->
        val str = value as? String ?: return@ValidationRule ValidationResult.Valid
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (str.isNotBlank() && !emailRegex.matches(str.trim())) {
            ValidationResult.Invalid(errorMessage)
        } else {
            ValidationResult.Valid
        }
    }

    fun matchesPattern(
        regex: Regex,
        errorMessage: UiText = UiText.raw("Format is invalid")
    ): ValidationRule<Any?> = ValidationRule { value, _ ->
        val str = value as? String ?: return@ValidationRule ValidationResult.Valid
        if (str.isNotBlank() && !regex.matches(str)) {
            ValidationResult.Invalid(errorMessage)
        } else {
            ValidationResult.Valid
        }
    }

    fun matchesOtherField(
        otherFieldKey: String,
        errorMessage: UiText = UiText.raw("Fields do not match")
    ): ValidationRule<Any?> = ValidationRule { value, allValues ->
        val otherValue = allValues[otherFieldKey]
        if (value != otherValue) {
            ValidationResult.Invalid(errorMessage)
        } else {
            ValidationResult.Valid
        }
    }

    fun <T> custom(
        errorMessage: UiText,
        predicate: (T) -> Boolean
    ): ValidationRule<T> = ValidationRule { value, _ ->
        if (!predicate(value)) ValidationResult.Invalid(errorMessage) else ValidationResult.Valid
    }
}
