package app.rutherford.util

import app.rutherford.util.Checks.validateNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
        TODO("Not yet implemented")
    }

    @ParameterizedTest
    @ValueSource(strings = ["  1  ", "  a   a", "nu  ll", " asdadasdad ", "111"])
    fun `should validate not blank`(value: String) {
        assertEquals(value, validateNotNull("should not throw", value))
    }

    @Test
    fun `should throw if value is blank or null`() {
        TODO("Not yet implemented")
    }
}