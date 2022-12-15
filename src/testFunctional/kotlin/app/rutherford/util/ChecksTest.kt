package app.rutherford.util

import app.rutherford.util.Checks.validateNotBlank
import app.rutherford.util.Checks.validateNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ChecksTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "aa", "null", "asdadasdad", "111"])
    fun `should validate not null`(value: String) {
        assertEquals(value, validateNotNull("should not throw", value))
    }

    @Test
    fun `should throw if value is null`() {
        val result = assertThrows<IllegalStateException> { validateNotNull("param123", null) }
        assertEquals("param123 is null", result.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["  1  ", "  a   a", "nu  ll", " asdadasdad ", "111"])
    fun `should validate not blank`(value: String) {
        assertEquals(value, validateNotBlank("should not throw", value))
    }

    @Test
    fun `should throw from validateNotBlank if value is null`() {
        val result = assertThrows<IllegalStateException> { validateNotBlank("param123", null) }
        assertEquals("param123 is null or blank", result.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "   ", "        "])
    fun `should throw if value is blank`(value: String) {
        val result = assertThrows<IllegalStateException> { validateNotBlank("param123", null) }
        assertEquals("param123 is null or blank", result.message)
    }
}