package app.rutherford.util

import app.rutherford.util.Checks.validateNotBlank
import app.rutherford.util.Checks.validateNotNull
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ChecksTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "aa", "null", "asdadasdad", "111"])
    fun `should validate not null`(value: String) {
        assertThat(validateNotNull("should not throw", value)).isEqualTo(value)
    }

    @Test
    fun `should throw if value is null`() {
        assertThatThrownBy { validateNotNull("param123", null) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("param123 is null")
    }

    @ParameterizedTest
    @ValueSource(strings = ["  1  ", "  a   a", "nu  ll", " asdadasdad ", "111"])
    fun `should validate not blank`(value: String) {
        assertThat(validateNotBlank("should not throw", value)).isEqualTo(value)
    }

    @Test
    fun `should throw from validateNotBlank if value is null`() {
        assertThatThrownBy { validateNotBlank("param123", null) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("param123 is null or blank")
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "   ", "        "])
    fun `should throw if value is blank`(value: String) {
        assertThatThrownBy { validateNotBlank("param123", value) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("param123 is null or blank")
    }
}