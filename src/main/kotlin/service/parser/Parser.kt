package org.benja.service.parser

import org.benja.model.Transaction
import java.io.File

interface Parser {
    fun parseRecords(file: File): List<Transaction>
}