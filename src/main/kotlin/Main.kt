package org.benja

import org.benja.service.TransactionValidator
import org.benja.service.parser.FileParser

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val directoryPath = "src/main/resources"
    val transactions = FileParser.readFilesFromDirectory(directoryPath)
    val validationResult = TransactionValidator.validateTransactions(transactions)
    val validationResultAgain = TransactionValidator.validateTransactionsAgain(transactions)

    println("There are a total of " + transactions.size + " transactions")
    println("There are a total of " + validationResult.validTransactions.size + " valid transactions")
}