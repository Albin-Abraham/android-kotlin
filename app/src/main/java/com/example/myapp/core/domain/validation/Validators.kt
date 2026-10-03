package com.example.myapp.core.domain.validation

/**
 * Standard reusable field validators implementing FieldValidator strategy.
 */
object Validators {

    fun required(message: String = "This field is required"): FieldValidator = FieldValidator { value ->
        when (value) {
            null -> message
            is String -> if (value.trim().isBlank()) message else null
            else -> null
        }
    }

    fun minLength(min: Int, message: String = "Minimum length is $min characters"): FieldValidator = FieldValidator { value ->
        val str = value as? String ?: return@FieldValidator null
        if (str.length < min) message else null
    }

    fun email(message: String = "Enter a valid email address"): FieldValidator = FieldValidator { value ->
        val str = value as? String ?: return@FieldValidator null
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (str.isNotBlank() && !emailRegex.matches(str)) message else null
    }
}
