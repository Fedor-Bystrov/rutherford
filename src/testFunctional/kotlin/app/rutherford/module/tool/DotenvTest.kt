package app.rutherford.module.tool

import app.rutherford.module.exception.DotenvException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class DotenvTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            "TEST1, true1",
            "TEST2, true1231",
            "TEST3, 123123true",
            "TEST4, asdasasd",
            "TEST5, 000000",
            "TEST6, ---!!!---",
        ]
    )
    fun `should load string env variable from dotenv file`(key: String, expectedValue: String) {
        assertEquals(expectedValue, Dotenv.get(key))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "TEST_NOT_EXIST1",
            "TEST_NOT_EXIST2",
        ]
    )
    fun `should throw when string env variable is not found`(key: String) {
        val result = assertThrows<DotenvException> { Dotenv.getInt(key) }
        assertEquals("No value for $key key", result.message)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "TEST_INT1, 0",
            "TEST_INT2, 500500",
        ]
    )
    fun `should load int env variable from dotenv file`(key: String, expectedValue: Int) {
        assertEquals(expectedValue, Dotenv.getInt(key))
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "TEST_INT3",
            "TEST_INT4",
        ]
    )
    fun `should throw when cannot parse int env variable`(key: String) {
        val result = assertThrows<DotenvException> { Dotenv.getInt(key) }
        assertEquals("No value for $key key", result.message)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "TEST_NOT_EXIST1",
            "TEST_NOT_EXIST2",
        ]
    )
    fun `should throw when int env variable is not found`(key: String) {
        val result = assertThrows<DotenvException> { Dotenv.getInt(key) }
        assertEquals("No value for $key key", result.message)
    }
}