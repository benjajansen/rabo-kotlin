import org.benja.model.Transaction
import org.benja.service.parser.FileParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class FileParserTest {
    @Test
    fun `readFilesFromDirectory should return list of transactions from csv and xml files`() {
        val pathToTestFiles: String = javaClass.getResource("/fileParser/positiveSource").file

        // Act
        val transactions: List<Transaction> = FileParser.readFilesFromDirectory(pathToTestFiles)

        // Assert data
        val expectedTransactions: List<Transaction> = listOf(
            Transaction(reference="183398", accountNumber="NL56RABO0149876948", description="Clothes from Richard de Vries", startBalance= BigDecimal("33.34"), mutation=BigDecimal("5.55"), endBalance=BigDecimal("38.89"))
            ,Transaction(reference="112806", accountNumber="NL27SNSB0917829871", description="Subscription from Jan Dekker", startBalance=BigDecimal("28.95"), mutation=BigDecimal("-19.44"), endBalance=BigDecimal("9.51"))
            ,Transaction(reference="110784", accountNumber="NL93ABNA0585619023", description="Subscription from Richard Bakker", startBalance=BigDecimal("13.89"), mutation=BigDecimal("-46.18"), endBalance=BigDecimal("-32.29"))
            ,Transaction(reference="137758", accountNumber="NL93ABNA0585619023", description="Tickets for Daniël King", startBalance=BigDecimal("97.56"), mutation=BigDecimal("46.41"), endBalance=BigDecimal("143.97"))
            ,Transaction(reference="112806", accountNumber="NL43AEGO0773393871", description="Subscription from Daniël Theuß", startBalance=BigDecimal("102.33"), mutation=BigDecimal("11.49"), endBalance=BigDecimal("113.82"))
            ,Transaction(reference="112806", accountNumber="NL74ABNA0248990274", description="Subscription for Rik Dekker", startBalance=BigDecimal("48.2"), mutation=BigDecimal("-4.25"), endBalance=BigDecimal("43.95"))
            ,Transaction(reference="118757", accountNumber="NL32RABO0195610843", description="Candy for Willem King", startBalance=BigDecimal("98.99"), mutation=BigDecimal("-7.85"), endBalance=BigDecimal("91.14"))
            ,Transaction(reference="146294", accountNumber="NL90ABNA0585647886", description="Tickets for Vincent Dekker", startBalance=BigDecimal("13.62"), mutation=BigDecimal("-15.08"), endBalance=BigDecimal("-1.46"))
            ,Transaction(reference="128470", accountNumber="NL91RABO0315273637", description="Tickets for Erik Dekker", startBalance=BigDecimal("53.31"), mutation=BigDecimal("-15.85"), endBalance=BigDecimal("37.46"))
            ,Transaction(reference="141007", accountNumber="NL32RABO0195610843", description="Tickets for Richard de Vries", startBalance=BigDecimal("66.35"), mutation=BigDecimal("44.27"), endBalance=BigDecimal("110.62"))
            ,Transaction(reference="138932", accountNumber="NL90ABNA0585647886", description="Flowers for Richard Bakker", startBalance=BigDecimal("94.9"), mutation=BigDecimal("14.63"), endBalance=BigDecimal("109.53"))
            ,Transaction(reference="131254", accountNumber="NL93ABNA0585619023", description="Candy from Vincent de Vries", startBalance=BigDecimal("5429"), mutation=BigDecimal("-939"), endBalance=BigDecimal("6368"))
            ,Transaction(reference="101339", accountNumber="NL27SNSB0917829871", description="Tickets from Rik King", startBalance=BigDecimal("84.46"), mutation=BigDecimal("40.45"), endBalance=BigDecimal("124.91"))
            ,Transaction(reference="119582", accountNumber="NL32RABO0195610843", description="Subscription from Vincent de Vries", startBalance=BigDecimal("38.86"), mutation=BigDecimal("28.77"), endBalance=BigDecimal("67.63"))
            ,Transaction(reference="180148", accountNumber="NL32RABO0195610843", description="Candy for Rik de Vries", startBalance=BigDecimal("51.01"), mutation=BigDecimal("-25.59"), endBalance=BigDecimal("25.42"))
            ,Transaction(reference="152977", accountNumber="NL69ABNA0433647324", description="Flowers from Richard Bakker", startBalance=BigDecimal("62.17"), mutation=BigDecimal("20.55"), endBalance=BigDecimal("82.72"))
            ,Transaction(reference="167188", accountNumber="NL91RABO0315273637", description="Subscription for Jan Theuß", startBalance=BigDecimal("10.1"), mutation=BigDecimal("-0.3"), endBalance=BigDecimal("9.8"))
            ,Transaction(reference="154771", accountNumber="NL43AEGO0773393871", description="Clothes from Rik Theuß", startBalance=BigDecimal("21.54"), mutation=BigDecimal("-17.57"), endBalance=BigDecimal("3.97"))
            ,Transaction(reference="192480", accountNumber="NL43AEGO0773393871", description="Subscription for Erik de Vries", startBalance=BigDecimal("3980"), mutation=BigDecimal("1000"), endBalance=BigDecimal("4981"))
            ,Transaction(reference="181688", accountNumber="NL90ABNA0585647886", description="Flowers for Jan Theuß", startBalance=BigDecimal("75.39"), mutation=BigDecimal("-32.75"), endBalance=BigDecimal("42.64"))
        )

        assertEquals(expectedTransactions, transactions)
    }

    @Test
    fun `readFilesFromDirectory should return an empty list of transactions from png file`() {
        val pathToTestFiles = javaClass.getResource("/fileParser/negativeSource").file

        // Act
        val transactions = FileParser.readFilesFromDirectory(pathToTestFiles)

        val expectedTransactions = listOf(
            Transaction(reference="183398", accountNumber="NL56RABO0149876948", description="Clothes from Richard de Vries", startBalance= BigDecimal("33.34"), mutation=BigDecimal("5.55"), endBalance=BigDecimal("38.89"))
            ,Transaction(reference="181688", accountNumber="NL90ABNA0585647886", description="Flowers for Jan Theuß", startBalance=BigDecimal("75.39"), mutation=BigDecimal("-32.75"), endBalance=BigDecimal("42.64"))
        )

        assertNotEquals(expectedTransactions, transactions)
    }
}