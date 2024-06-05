package org.benja.service.parser

import org.benja.model.Transaction
import java.io.File

/**
 * FileParser is a utility object responsible for reading Transaction records from disk files.
 * Files can be in CSV or XML formats, and are processed by appropriate parsers.
 *
 */
object FileParser {
    /**
     * Reads all files from a directory and parses their contents into a list of Transaction objects.
     *
     * Only files ending in .csv or .xml will be processed. Files with other extensions will be ignored.
     *
     * @param directoryPath The path of the directory that contains the files to be read.
     * @return list of Transaction objects parsed from all valid files. Returns an empty list if no valid files are
     * found or if an error occurs during reading the files.
     */
    fun readFilesFromDirectory(directoryPath: String): List<Transaction> {
        val folder = File(directoryPath)
        val files = folder.listFiles()
        val transactions = ArrayList<Transaction>()
        files?.let {
            it.forEach { file -> transactions.addAll(parseFile(file)) }
        }
        return transactions
    }

    /**
     * Parses a file into a list of Transaction objects using an appropriate parser based on the file's extension.
     *
     * @param file The file to be processed.
     * @return List of Transaction objects parsed from the file. Returns an empty list if the file extension is not
     * supported or if an error occurs during reading the file.
     */
    private fun parseFile(file: File): List<Transaction> {
        val fileName = file.name.lowercase()
        val strategy: Parser? = when {
            fileName.endsWith(".csv") -> JacksonCsvParser()
            fileName.endsWith(".xml") -> JacksonXmlParser()
            else -> {
                println("Unsupported file type: $fileName")
                null
            }
        }

        return strategy?.parseRecords(file) ?: emptyList()
    }
}