package app.rutherford.core

import app.rutherford.core.exception.UnknownOriginException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.net.URL

class ApplicationNameTest {
    @ParameterizedTest
    @CsvSource(
        value = [
            "http://localhost:7070, TEST1",
            "http://localhost2:7070, TEST2"
        ]
    )
    fun `should return correct applicationName by URL`(origin: URL, expected: ApplicationName) {
        // when
        val result = ApplicationName.getForOrigin(origin)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "http://localhost:7171",
            "http://localhost12:7070",
            "http://test.localhost:7070",
            "https://google.com",
            "https://youtube.com",
        ]
    )
    fun `should throw UnknownOriginException when applicationName was not found`(origin: URL) {
        // then
        assertThatThrownBy { ApplicationName.getForOrigin(origin) }
            .isInstanceOf(UnknownOriginException::class.java)
            .hasMessage("No application registered for $origin")
    }
}
