package app.rutherford.core.types

import app.rutherford.core.types.Base64.Companion.base64
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Base64 as JavaBase64

class Base64Test {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "dGhpcyBpcyBzdHJpbmcK",
            "dGhpcyBpcyBzdHJpbmdnCg==",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbmdnCg==",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbiEmQCUhKiVAJiMkISomKCFeQCkjIXNhZ3NkZmdhc2ZnTEhGS0pHRkpIR0ZLRkdKSGdnCg=="
        ]
    )
    fun `should encode string`(value: String) {
        // when
        val base64 = base64(value)

        // then
        assertThat(base64.toString()).isEqualTo(value)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "This is String",
            "This is String This is String 123",
            "adfasdglashdgasdhfoquwero811i &^$!#&^!$@*!%(@!%@!!%^@!",
        ]
    )
    fun `should encode byte array`(value: String) {
        // given
        val base64Bytes = JavaBase64.getEncoder().encode(value.encodeToByteArray())

        // when
        val base64 = base64(base64Bytes)

        // then
        val base64String = JavaBase64.getEncoder().encodeToString(value.encodeToByteArray())
        assertThat(base64.toString()).isEqualTo(base64String)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "1",
            "A",
            "This is String",
            "This is String This is String 123",
            "adfasdglashdgasdhfoquwero811i &^$!#&^!$@*!%(@!%@!!%^@!",
        ]
    )
    fun `should encode correctly`(value: String) {
        // given
        val base64Bytes = JavaBase64.getEncoder().encode(value.encodeToByteArray())
        val base64String = JavaBase64.getEncoder().encodeToString(value.encodeToByteArray())

        // when
        val fromBytes = base64(base64Bytes)
        val fromString = base64(base64String)

        // then
        assertThat(fromBytes).isEqualTo(fromString)
        assertThat(fromBytes.toString()).isEqualTo(fromString.toString())

        // and
        val fromBytesDecoded = JavaBase64.getDecoder().decode(fromBytes.toString())
        val fromStringDecoded = JavaBase64.getDecoder().decode(fromString.toString())

        assertThat(String(fromBytesDecoded)).isEqualTo(value)
        assertThat(String(fromStringDecoded)).isEqualTo(value)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "dGhpcyBpcyBzdHJpbmcK=",
            "dGhpcyBpcyBzdHJpbmdnCg=",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbmdnCg=",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbiEmQCUhKiVAJiMkISomKCFeQCkjIXNhZ3NkZmdhc2ZnTEhGS0pHRkpIR0ZLRkdKSGdnCg==="
        ]
    )
    fun `should throw on malformed string`(value: String) {
        // then
        assertThatThrownBy { base64(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
