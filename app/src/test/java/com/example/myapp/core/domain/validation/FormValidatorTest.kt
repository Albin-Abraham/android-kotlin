package com.example.myapp.core.domain.validation

import com.example.myapp.core.domain.util.UiText
import org.junit.Assert.*
import org.junit.Test

class FormValidatorTest {

    @Test
    fun validator_detects_blank_and_short_password() {
        val validator = FormValidator.builder()
            .field("username", Rules.notBlank(UiText.raw("Username is required")))
            .field(
                "password",
                Rules.notBlank(UiText.raw("Password is required")),
                Rules.minLength(6, UiText.raw("Password must be at least 6 characters"))
            )
            .build()

        val emptyForm = mapOf("username" to "", "password" to "123")
        val errors = validator.validateAll(emptyForm)

        assertEquals("Username is required", (errors["username"] as? UiText.DynamicString)?.value)
        assertEquals("Password must be at least 6 characters", (errors["password"] as? UiText.DynamicString)?.value)
    }

    @Test
    fun validator_detects_cross_field_password_mismatch() {
        val validator = FormValidator.builder()
            .field("password", Rules.notBlank())
            .field("confirmPassword", Rules.matchesOtherField("password", UiText.raw("Passwords must match")))
            .build()

        val mismatchedForm = mapOf("password" to "secret123", "confirmPassword" to "secretXYZ")
        val errors = validator.validateAll(mismatchedForm)

        assertEquals("Passwords must match", (errors["confirmPassword"] as? UiText.DynamicString)?.value)

        val matchedForm = mapOf("password" to "secret123", "confirmPassword" to "secret123")
        val noErrors = validator.validateAll(matchedForm)

        assertTrue(noErrors.isEmpty())
    }

    @Test
    fun rule_combinators_and_or_work_correctly() {
        val phoneOrEmailRule = Rules.email(UiText.raw("Invalid contact")) or Rules.minLength(10, UiText.raw("Invalid contact"))

        val validEmailResult = phoneOrEmailRule.validate("user@example.com")
        assertTrue(validEmailResult.isValid)

        val validPhoneResult = phoneOrEmailRule.validate("12345678901")
        assertTrue(validPhoneResult.isValid)

        val invalidResult = phoneOrEmailRule.validate("short")
        assertFalse(invalidResult.isValid)
    }
}
