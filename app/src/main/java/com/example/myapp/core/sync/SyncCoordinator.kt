package com.example.myapp.core.sync

import com.example.myapp.core.sync.contracts.DefaultOperationEligibilityPolicy
import com.example.myapp.core.sync.contracts.OperationEligibilityPolicy
import com.example.myapp.core.sync.contracts.PendingOperation
import com.example.myapp.core.sync.contracts.SyncOperationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap

/**
 * Result of enqueueing an operation for background synchronization.
 */
sealed interface EnqueueResult {
    data class Enqueued(val operation: PendingOperation) : EnqueueResult
    data class Rejected(val reason: String) : EnqueueResult
    data class DuplicateIgnored(val existingOperationId: String) : EnqueueResult
}

/**
 * Enterprise Offline Synchronization Coordinator.
 * Manages durable pending operation queue, idempotency, retry backoff, and server reconciliation.
 */
class SyncCoordinator(
    private val policy: OperationEligibilityPolicy = DefaultOperationEligibilityPolicy
) {
    private val mutex = Mutex()
    private val queue = ConcurrentHashMap<String, PendingOperation>()
    private val idempotencyIndex = ConcurrentHashMap<String, String>() // IdempotencyKey -> OperationId

    private val _pendingCount = MutableStateFlow(0)
    val pendingCount: StateFlow<Int> = _pendingCount.asStateFlow()

    suspend fun enqueue(operation: PendingOperation): EnqueueResult = mutex.withLock {
        // 1. Verify eligibility
        if (!policy.isEligibleForOfflineQueue(operation.operationType)) {
            return EnqueueResult.Rejected("Operation type '${operation.operationType}' requires immediate server confirmation.")
        }

        // 2. Idempotency deduplication check
        val existingOpId = idempotencyIndex[operation.idempotencyKey]
        if (existingOpId != null) {
            return EnqueueResult.DuplicateIgnored(existingOpId)
        }

        // 3. Register and queue
        queue[operation.operationId] = operation
        idempotencyIndex[operation.idempotencyKey] = operation.operationId
        _pendingCount.value = queue.values.count { it.state == SyncOperationState.PENDING || it.state == SyncOperationState.RETRY_PENDING }

        EnqueueResult.Enqueued(operation)
    }

    suspend fun processNextBatch(
        executeRemote: suspend (PendingOperation) -> Result<Unit>
    ): Int = mutex.withLock {
        var processedCount = 0
        val pendingOps = queue.values.filter {
            it.state == SyncOperationState.PENDING || it.state == SyncOperationState.RETRY_PENDING
        }

        for (op in pendingOps) {
            queue[op.operationId] = op.copy(state = SyncOperationState.PROCESSING)
            val result = executeRemote(op)

            if (result.isSuccess) {
                queue[op.operationId] = op.copy(state = SyncOperationState.SYNCED)
                processedCount++
            } else {
                val newRetryCount = op.retryCount + 1
                val maxAllowed = policy.maxRetries(op.operationType)
                if (newRetryCount >= maxAllowed) {
                    queue[op.operationId] = op.copy(
                        state = SyncOperationState.FAILED,
                        retryCount = newRetryCount,
                        lastError = result.exceptionOrNull()?.message
                    )
                } else {
                    queue[op.operationId] = op.copy(
                        state = SyncOperationState.RETRY_PENDING,
                        retryCount = newRetryCount,
                        lastError = result.exceptionOrNull()?.message
                    )
                }
            }
        }

        _pendingCount.value = queue.values.count { it.state == SyncOperationState.PENDING || it.state == SyncOperationState.RETRY_PENDING }
        processedCount
    }

    fun getOperation(operationId: String): PendingOperation? = queue[operationId]

    fun clear() {
        queue.clear()
        idempotencyIndex.clear()
        _pendingCount.value = 0
    }
}
