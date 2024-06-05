package org.benja.service.parser

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.benja.model.Transaction
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * This Parser implementation utilizes the [CsvMapper] class from the Jackson library to parse CSV files.
 *
 * This class is intended to parse files containing Transaction records.
 */
class JacksonCsvParser : Parser {
    private val MAPPER = CsvMapper().registerModule(KotlinModule.Builder().build())
    private val FILE_ENCODING = "ISO-8859-1"

    /**
     * Parses Transaction records from the provided CSV file.
     *
     * @param file The CSV file containing Transaction records
     * @return List of Transaction objects parsed from the CSV file. Returns an empty list if an error occurs during
     * reading the file.
     */
    override fun parseRecords(file: File): List<Transaction> {
        return FileInputStream(file).use { fileStream ->
            InputStreamReader(fileStream, FILE_ENCODING).use { reader ->
                try {
                    MAPPER
                        .readerFor(Transaction::class.java)
                        .with(CsvSchema.emptySchema().withHeader())
                        .readValues<Transaction>(reader)
                        .readAll()
                } catch (e: Exception) {
                    println("Error reading file " + file.absolutePath)
                    emptyList<Transaction>()
                }
            }
        }
    }
}