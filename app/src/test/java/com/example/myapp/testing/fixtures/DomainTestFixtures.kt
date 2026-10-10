package com.example.myapp.testing.fixtures

import com.example.myapp.core.sync.contracts.PendingOperation
import com.example.myapp.core.sync.contracts.SyncOperationState
import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User

/**
 * Enterprise Test Data Fixtures for rapid DX in unit and integration tests.
 */
object DomainTestFixtures {

    fun user(
        id: String = "usr_test_101",
        username: String = "test_user",
        email: String = "test_user@zenos.io",
        token: String = "jwt_valid_sample_token_xyz"
    ): User = User(
        id = id,
        username = username,
        email = email,
        token = token
    )

    fun credentials(
        username: String = "test_user",
        password: String = "securePass123"
    ): AuthCredentials = AuthCredentials(
        username = username,
        password = password
    )

    fun pendingOperation(
        operationId: String = "op_test_999",
        operationType: String = "hrmis.attendance.capture",
        tenantId: String = "tenant_enterprise_01",
        idempotencyKey: String = "idempotency_key_abc_123",
        payloadJson: String = "{\"timestamp\":1710000000000,\"type\":\"CHECK_IN\"}",
        createdAtEpochMillis: Long = System.currentTimeMillis(),
        state: SyncOperationState = SyncOperationState.PENDING,
        retryCount: Int = 0
    ): PendingOperation = PendingOperation(
        operationId = operationId,
        operationType = operationType,
        tenantId = tenantId,
        idempotencyKey = idempotencyKey,
        payloadJson = payloadJson,
        createdAtEpochMillis = createdAtEpochMillis,
        state = state,
        retryCount = retryCount
    )
}
