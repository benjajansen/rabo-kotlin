package org.benja.model

data class ValidatedTransactions (
    val invalidTransactions : Map<InvalidTransactionReason, List<Transaction>>,
    val validTransactions: List<Transaction>
)