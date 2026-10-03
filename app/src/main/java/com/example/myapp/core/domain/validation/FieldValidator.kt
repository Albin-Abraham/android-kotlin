package com.example.myapp.core.domain.validation

/**
 * Functional interface representing a validation strategy for a form field.
 * Follows GoF Strategy Pattern & SOLID Interface Segregation Principle.
 */
fun interface FieldValidator {
    /**
     * Validates [value] and returns an error message if invalid, or null if valid.
     */
    fun validate(value: Any?): String?
}
