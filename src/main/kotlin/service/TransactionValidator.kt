package org.benja.service

import org.benja.model.InvalidTransactionReason
import org.benja.model.Transaction
import org.benja.model.ValidatedTransactions

/**
 * Singleton object used for transaction validation.
 */
object TransactionValidator {
    /**
     * Validate the provided transactions.
     * Checks each transaction for unique reference, valid end balance calculation and non-negative end balance value.
     *
     * @param transactions The list of transactions to be validated
     * @return [ValidatedTransactions] object containing map of invalid transactions by reason and list of remaining
     * valid transactions
     */
    fun validateTransactions(transactions: List<Transaction>): ValidatedTransactions {
        val validTransactions = mutableListOf<Transaction>()
        val invalidTransactions = mutableMapOf<InvalidTransactionReason, MutableList<Transaction>>()
        val seenReferences = mutableSetOf<String>()

        transactions.forEach { transaction ->
            when {
                !isReferenceUnique(transaction, seenReferences) -> {
                    invalidTransactions.addTransactionForReason(
                        InvalidTransactionReason.INVALID_REFERENCE_NUMBER,
                        transaction
                    )
                }
                !isEndBalanceCalculationValid(transaction) -> {
                    invalidTransactions.addTransactionForReason(
                        InvalidTransactionReason.INVALID_END_BALANCE_CALCULATION,
                        transaction
                    )
                }
                isEndBalanceValueNegative(transaction) -> {
                    invalidTransactions.addTransactionForReason(
                        InvalidTransactionReason.INVALID_END_BALANCE_NEGATIVE,
                        transaction
                    )
                }
                else -> validTransactions += transaction
            }
        }

        return ValidatedTransactions(invalidTransactions, validTransactions)
    }

    fun validateTransactionsAgain(transactions: List<Transaction>): ValidatedTransactions {
        val seenReferences = mutableSetOf<String>()

        val (validTransactions, invalidTransactions) = transactions.partition { transaction ->
            isReferenceUnique(transaction, seenReferences) &&
            isEndBalanceCalculationValid(transaction) &&
            !isEndBalanceValueNegative(transaction)
        }

        val invalidTransactionsByReason = invalidTransactions.groupBy { transaction ->
            when {
                !isReferenceUnique(transaction, seenReferences) ->
                    InvalidTransactionReason.INVALID_REFERENCE_NUMBER
                !isEndBalanceCalculationValid(transaction) ->
                    InvalidTransactionReason.INVALID_END_BALANCE_CALCULATION
                isEndBalanceValueNegative(transaction) ->
                    InvalidTransactionReason.INVALID_END_BALANCE_NEGATIVE
                else -> throw IllegalStateException("Invalid transaction without a known reason")
            }
        }.mapValues { (_, v) -> v.toMutableList() }

        return ValidatedTransactions(invalidTransactionsByReason.toMutableMap(), validTransactions.toMutableList())
    }


    /**
     * Checks if the end balance calculation is valid for the given transaction.
     *
     * @param transaction The transaction to validate
     * @return True if end balance of the transaction equals the sum of start balance and mutation, false otherwise
     */
    private fun isEndBalanceCalculationValid(transaction: Transaction): Boolean {
        return transaction.endBalance == transaction.startBalance + transaction.mutation
    }

    /**
     * Checks if the end balance value is negative for the given transaction.
     *
     * @param transaction The transaction to validate
     * @return True if the end balance of the transaction is negative, false otherwise
     */
    private fun isEndBalanceValueNegative(transaction: Transaction): Boolean {
        return transaction.endBalance.signum() == -1
    }

    /**
     * Checks if the reference for the given transaction is unique.
     *
     * @param transaction The transaction to check
     * @return True if the reference of the transaction is not present in the seen references, false otherwise
     */
    private fun isReferenceUnique(transaction: Transaction, seenReferences: MutableSet<String>): Boolean {
        if (transaction.reference in seenReferences) {
            return false
        }
        seenReferences.add(transaction.reference)
        return true
    }

    /**
     * Adds a transaction to the map of invalid transactions by the specified reason.
     * This is an extension function, you can think of it as a way to add a new method to the class its extending. In
     * this case that would be MutableMap<...>. This allows us to call this method on any instance of it.
     *
     * @param reason The reason why the transaction is invalid
     * @param transaction The invalid transaction
     */
    private fun MutableMap<InvalidTransactionReason, MutableList<Transaction>>.addTransactionForReason(
        reason: InvalidTransactionReason,
        transaction: Transaction
    ) {
        this.getOrPut(reason) { mutableListOf() } += transaction
    }
}