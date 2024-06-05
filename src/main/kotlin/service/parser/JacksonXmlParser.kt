package org.benja.service.parser

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.benja.model.Transaction
import java.io.File

/**
 * This Parser implementation utilizes the [XmlMapper] class from the Jackson library to parse XML files.
 *
 * This class is intended to parse XML files containing Transaction records.
 */
class JacksonXmlParser : Parser {
    private val mapper = XmlMapper().registerModule(KotlinModule.Builder().build())

    /**
     * Parses Transaction records from the provided XML file.
     *
     * @param file The XML file containing Transaction records
     * @return List of Transaction objects parsed from the XML file. Returns an empty list if an error occurs during
     * reading the file.
     */
    override fun parseRecords(file: File): List<Transaction> {
        return try {
            mapper.readValue<List<Transaction>>(file)
        } catch (e: Exception) {
            println("Error reading file " + file.absolutePath)
            emptyList<Transaction>()
        }
    }
}