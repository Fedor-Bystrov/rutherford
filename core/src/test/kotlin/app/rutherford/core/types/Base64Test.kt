package app.rutherford.core.types

import app.rutherford.core.types.Base64.Companion.base64
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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
    fun `should create from base64 string`(value: String) {
        // when
        val base64 = base64(value)

        // then
        assertThat(base64.toString()).isEqualTo(value)

        // and
        val decoded = JavaBase64.getDecoder().decode(value)
        assertThat(base64.decodeBytes()).isEqualTo(decoded)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "This is String",
            "This is String This is String 123",
            "adfasdglashdgasdhfoquwero811i &^$!#&^!$@*!%(@!%@!!%^@!",
        ]
    )
    fun `should create from base64 byte array`(value: String) {
        // given
        val base64Bytes = JavaBase64.getEncoder().encode(value.encodeToByteArray())

        // when
        val base64 = base64(base64Bytes)

        // then
        val base64String = JavaBase64.getEncoder().encodeToString(value.encodeToByteArray())
        assertThat(base64.toString()).isEqualTo(base64String)

        // and
        assertThat(base64.decodeBytes()).isEqualTo(value.encodeToByteArray())
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
            "adfasdglashdgasdhfoquwero811i &^\$!#&^!\$@*!%(@!%@dhfoquwero811i &^\$!#&^!\$@*!%(@!%@!!%^@!",
        ]
    )
    fun `should create correctly`(value: String) {
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

        // and
        assertThat(fromBytes.decodeBytes()).isEqualTo(value.encodeToByteArray())
        assertThat(fromString.decodeBytes()).isEqualTo(value.encodeToByteArray())
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "1, MQ==",
            "A, QQ==",
            "This is String, VGhpcyBpcyBTdHJpbmc=",
            "This is String This is String 123, VGhpcyBpcyBTdHJpbmcgVGhpcyBpcyBTdHJpbmcgMTIz",
            "adfasdglashdgasdhfoquwero811i &^\$!#&^!\$@*!%(@!%@dhfoquwero811i &^\$!#&^!\$@*!%(@!%@!!%^@!, YWRmYXNkZ2xhc2hkZ2FzZGhmb3F1d2VybzgxMWkgJl4kISMmXiEkQCohJShAISVAZGhmb3F1d2VybzgxMWkgJl4kISMmXiEkQCohJShAISVAISElXkAh",
        ]
    )
    fun `should encode correctly`(value: String, expected: String) {
        // when
        val base64 = Base64.encode(value.encodeToByteArray())

        // then
        assertThat(base64.toString()).isEqualTo(expected)
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

    @ParameterizedTest
    @ValueSource(
        strings = [
            "dGhpcyBpcyBzdHJpbmcK",
            "dGhpcyBpcyBzdHJpbmdnCg==",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbmdnCg==",
            "YWRnYXNkZ2FzZ2FzdGhpcyBpcyBzdHJpbiEmQCUhKiVAJiMkISomKCFeQCkjIXNhZ3NkZmdhc2ZnTEhGS0pHRkpIR0ZLRkdKSGdnCg=="
        ]
    )
    fun `should return correct bytes`(value: String) {
        // when
        val base64 = base64(value)

        // then
        assertThat(base64.bytes()).isEqualTo(value.encodeToByteArray())
    }
}
