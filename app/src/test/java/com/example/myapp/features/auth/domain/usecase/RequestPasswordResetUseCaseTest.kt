package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RequestPasswordResetUseCaseTest {

    private class FakeAuthRepository : AuthRepository {
        override suspend fun login(credentials: AuthCredentials): Result<User> = Result.success(User("1", "u", "e@t.com", "t"))
        override suspend fun register(username: String, email: String, password: String): Result<User> = Result.success(User("1", username, email, "t"))
        override suspend fun requestPasswordReset(email: String): Result<Unit> = Result.success(Unit)
        override suspend fun authenticateWithBiometrics(): Result<User> = Result.success(User("1", "u", "e@t.com", "t"))
    }

    private val useCase = RequestPasswordResetUseCase(FakeAuthRepository())

    @Test
    fun reset_fails_with_blank_email() = runBlocking {
        val result = useCase("   ")
        assertTrue(result.isFailure)
        assertEquals("Please enter a valid email address", result.exceptionOrNull()?.message)
    }

    @Test
    fun reset_fails_with_malformed_email() = runBlocking {
        val result = useCase("not-an-email")
        assertTrue(result.isFailure)
        assertEquals("Please enter a valid email address", result.exceptionOrNull()?.message)
    }

    @Test
    fun reset_succeeds_with_valid_email() = runBlocking {
        val result = useCase("user@domain.com")
        assertTrue(result.isSuccess)
    }
}
