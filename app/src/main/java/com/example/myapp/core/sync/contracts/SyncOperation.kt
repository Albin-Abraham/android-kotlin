package com.example.myapp.core.sync.contracts

/**
 * Lifecycle states of an offline pending business operation.
 */
enum class SyncOperationState {
    PENDING,
    PROCESSING,
    SYNCED,
    RETRY_PENDING,
    CONFLICT,
    FAILED,
    CANCELLED
}

/**
 * Enterprise envelope for queueable offline operations.
 * Enforces tenant-binding, idempotency, and auditability.
 */
data class PendingOperation(
    val operationId: String,
    val operationType: String,
    val tenantId: String,
    val idempotencyKey: String,
    val payloadJson: String,
    val createdAtEpochMillis: Long,
    val state: SyncOperationState = SyncOperationState.PENDING,
    val retryCount: Int = 0,
    val lastError: String? = null
)
