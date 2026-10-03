package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseTest {

    private class FakeAuthRepository(
        private val shouldSucceed: Boolean = true
    ) : AuthRepository {
        override suspend fun login(credentials: AuthCredentials): Result<User> {
            return if (shouldSucceed) {
                Result.success(User("1", credentials.username, "test@example.com", "token123"))
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        }

        override suspend fun register(username: String, email: String, password: String): Result<User> {
            return Result.success(User("1", username, email, "token123"))
        }

        override suspend fun requestPasswordReset(email: String): Result<Unit> {
            return Result.success(Unit)
        }
    }

    @Test
    fun login_returns_error_when_username_is_blank() = runBlocking {
        val useCase = LoginUseCase(FakeAuthRepository())
        val result = useCase(AuthCredentials(username = "", password = "password"))

        assertTrue(result.isFailure)
        assertEquals("Username is required", result.exceptionOrNull()?.message)
    }

    @Test
    fun login_returns_error_when_password_is_too_short() = runBlocking {
        val useCase = LoginUseCase(FakeAuthRepository())
        val result = useCase(AuthCredentials(username = "user", password = "12"))

        assertTrue(result.isFailure)
        assertEquals("Password must be at least 4 characters", result.exceptionOrNull()?.message)
    }

    @Test
    fun login_succeeds_with_valid_credentials() = runBlocking {
        val useCase = LoginUseCase(FakeAuthRepository(shouldSucceed = true))
        val result = useCase(AuthCredentials(username = "user", password = "password"))

        assertTrue(result.isSuccess)
        assertEquals("user", result.getOrNull()?.username)
    }
}
