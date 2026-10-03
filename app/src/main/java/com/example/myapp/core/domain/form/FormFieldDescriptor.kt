package com.example.myapp.core.domain.form

import com.example.myapp.core.domain.validation.FieldValidator

/**
 * Domain representation of field icon types (Pure Kotlin, zero Android dependencies).
 */
enum class FieldIconType {
    NONE,
    PERSON,
    EMAIL,
    LOCK,
    PHONE,
    NUMBER
}

/**
 * Polymorphic form field descriptors for Config-Driven UI.
 * Pure domain definitions with zero UI/framework dependencies.
 */
sealed interface FormFieldDescriptor {
    val key: String
    val label: String
    val isRequired: Boolean
    val validators: List<FieldValidator>

    data class Text(
        override val key: String,
        override val label: String,
        val placeholder: String = "",
        val isPassword: Boolean = false,
        val iconType: FieldIconType = FieldIconType.NONE,
        val maxLines: Int = 1,
        override val isRequired: Boolean = true,
        override val validators: List<FieldValidator> = emptyList()
    ) : FormFieldDescriptor

    data class Number(
        override val key: String,
        override val label: String,
        val iconType: FieldIconType = FieldIconType.NUMBER,
        val min: Double? = null,
        val max: Double? = null,
        override val isRequired: Boolean = true,
        override val validators: List<FieldValidator> = emptyList()
    ) : FormFieldDescriptor

    data class Selection(
        override val key: String,
        override val label: String,
        val options: List<String>,
        override val isRequired: Boolean = true,
        override val validators: List<FieldValidator> = emptyList()
    ) : FormFieldDescriptor

    data class Toggle(
        override val key: String,
        override val label: String,
        val description: String? = null,
        override val isRequired: Boolean = false,
        override val validators: List<FieldValidator> = emptyList()
    ) : FormFieldDescriptor
}
