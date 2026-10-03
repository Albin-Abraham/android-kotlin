package com.example.myapp.core.domain.validation

import com.example.myapp.core.domain.util.UiText

/**
 * Clean Architecture Form Validator.
 * Coordinates field rules, cross-field validation, and returns structured errors.
 */
class FormValidator(
    private val rules: Map<String, List<ValidationRule<Any?>>>
) {

    /**
     * Validates a single field against its registered rules.
     */
    fun validateField(key: String, value: Any?, allValues: Map<String, Any?>): UiText? {
        val fieldRules = rules[key] ?: return null
        for (rule in fieldRules) {
            val result = rule.validate(value, allValues)
            if (!result.isValid) {
                return result.errorOrNull
            }
        }
        return null
    }

    /**
     * Validates all form fields and returns a map of field keys to error messages (or empty if valid).
     */
    fun validateAll(allValues: Map<String, Any?>): Map<String, UiText?> {
        val errors = mutableMapOf<String, UiText?>()
        for ((key, fieldRules) in rules) {
            val value = allValues[key]
            for (rule in fieldRules) {
                val result = rule.validate(value, allValues)
                if (!result.isValid) {
                    errors[key] = result.errorOrNull
                    break
                }
            }
        }
        return errors
    }

    companion object {
        fun builder(): Builder = Builder()
    }

    class Builder {
        private val rules = mutableMapOf<String, MutableList<ValidationRule<Any?>>>()

        fun field(key: String, vararg ruleList: ValidationRule<Any?>): Builder = apply {
            rules.getOrPut(key) { mutableListOf() }.addAll(ruleList)
        }

        fun build(): FormValidator = FormValidator(rules)
    }
}
