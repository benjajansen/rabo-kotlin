package org.benja.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.math.BigDecimal

data class Transaction(

    @field:JsonProperty("Reference")
    @field:JacksonXmlProperty(localName = "reference")
    val reference: String,

    @field:JsonProperty("Account Number")
    @field:JacksonXmlProperty(localName = "accountNumber")
    val accountNumber: String,

    @field:JsonProperty("Description")
    @field:JacksonXmlProperty(localName = "description")
    val description: String,

    @field:JsonProperty("Start Balance")
    @field:JacksonXmlProperty(localName = "startBalance")
    val startBalance: BigDecimal,

    @field:JsonProperty("Mutation")
    @field:JacksonXmlProperty(localName = "mutation")
    val mutation: BigDecimal,

    @field:JsonProperty("End Balance")
    @field:JacksonXmlProperty(localName = "endBalance")
    val endBalance: BigDecimal
)
