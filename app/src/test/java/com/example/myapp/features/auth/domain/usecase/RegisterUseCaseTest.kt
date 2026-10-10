package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterUseCaseTest {

    private class FakeAuthRepository : AuthRepository {
        override suspend fun login(credentials: AuthCredentials): Result<User> =
            Result.success(User("1", credentials.username, "user@test.com", "tok"))

        override suspend fun register(username: String, email: String, password: String): Result<User> =
            Result.success(User("usr_new", username, email, "tok_new"))

        override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)

        override suspend fun authenticateWithBiometrics(): Result<User> =
            Result.success(User("1", "bio", "bio@test.com", "tok"))
    }

    private val useCase = RegisterUseCase(FakeAuthRepository())

    @Test
    fun register_fails_when_terms_not_accepted() = runBlocking {
        val result = useCase("john_doe", "john@example.com", "securePass123", acceptedTerms = false)
        assertTrue(result.isFailure)
        assertEquals("You must accept the Terms of Service & Privacy Policy", result.exceptionOrNull()?.message)
    }

    @Test
    fun register_fails_when_email_is_invalid() = runBlocking {
        val result = useCase("john_doe", "invalid-email", "securePass123", acceptedTerms = true)
        assertTrue(result.isFailure)
        assertEquals("Please provide a valid email address", result.exceptionOrNull()?.message)
    }

    @Test
    fun register_fails_when_password_is_too_short() = runBlocking {
        val result = useCase("john_doe", "john@example.com", "123", acceptedTerms = true)
        assertTrue(result.isFailure)
        assertEquals("Password must be at least 6 characters", result.exceptionOrNull()?.message)
    }

    @Test
    fun register_succeeds_with_valid_inputs() = runBlocking {
        val result = useCase("john_doe", "john@example.com", "securePass123", acceptedTerms = true)
        assertTrue(result.isSuccess)
        assertEquals("john_doe", result.getOrNull()?.username)
        assertEquals("john@example.com", result.getOrNull()?.email)
    }
}
