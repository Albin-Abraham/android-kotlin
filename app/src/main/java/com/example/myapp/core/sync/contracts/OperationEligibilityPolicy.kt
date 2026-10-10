package com.example.myapp.core.sync.contracts

/**
 * Business operation eligibility policy.
 * Distinguishes queueable offline events from operations requiring authoritative server confirmation.
 */
interface OperationEligibilityPolicy {
    fun isEligibleForOfflineQueue(operationType: String): Boolean
    fun requiresStrictOrdering(operationType: String): Boolean
    fun maxRetries(operationType: String): Int
}

/**
 * Default ZenOS Enterprise Policy implementation.
 */
object DefaultOperationEligibilityPolicy : OperationEligibilityPolicy {
    private val queueableOperations = setOf(
        "user.preference.update",
        "hrmis.attendance.capture",
        "hrmis.leave.draft.save",
        "audit.log.event"
    )

    private val strictlyOrderedOperations = setOf(
        "hrmis.attendance.capture"
    )

    override fun isEligibleForOfflineQueue(operationType: String): Boolean {
        return operationType in queueableOperations
    }

    override fun requiresStrictOrdering(operationType: String): Boolean {
        return operationType in strictlyOrderedOperations
    }

    override fun maxRetries(operationType: String): Int {
        return when (operationType) {
            "hrmis.attendance.capture" -> 5
            else -> 3
        }
    }
}
