import com.durga.arunachaleswara.utils.Calculator
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {

    private val calculator = Calculator()

    @Test
    fun testAddition() {
        val result = calculator.calculateValues(5, 4) { a, b -> a + b }
        assertEquals(9, result)
    }
    @Test
    fun testSubtraction() {
        val result = calculator.calculateValues(5, 4) { a, b -> a - b }
        assertEquals(1, result)

    }

    @Test
    fun testMultiplication() {
        val result = calculator.calculateValues(5, 4) { a, b -> a * b }
        assertEquals(20, result)
    }
}