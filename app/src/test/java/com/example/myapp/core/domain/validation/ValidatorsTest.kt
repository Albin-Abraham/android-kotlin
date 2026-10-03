package com.example.myapp.core.domain.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ValidatorsTest {

    @Test
    fun required_validator_returns_error_when_null_or_blank() {
        val validator = Validators.required("Required field")

        assertEquals("Required field", validator.validate(null))
        assertEquals("Required field", validator.validate(""))
        assertEquals("Required field", validator.validate("   "))
        assertNull(validator.validate("Valid content"))
    }

    @Test
    fun minLength_validator_validates_string_length() {
        val validator = Validators.minLength(5, "Min 5 chars")

        assertEquals("Min 5 chars", validator.validate("1234"))
        assertNull(validator.validate("12345"))
        assertNull(validator.validate("123456"))
    }

    @Test
    fun email_validator_validates_email_format() {
        val validator = Validators.email("Invalid email")

        assertEquals("Invalid email", validator.validate("invalid-email"))
        assertEquals("Invalid email", validator.validate("test@"))
        assertEquals("Invalid email", validator.validate("@domain.com"))
        assertNull(validator.validate("user@example.com"))
    }
}
