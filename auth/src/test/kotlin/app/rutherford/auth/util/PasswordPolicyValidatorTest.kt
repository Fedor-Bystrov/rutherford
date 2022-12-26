package app.rutherford.auth.util

import app.rutherford.auth.exception.PasswordPolicyValidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class PasswordPolicyValidatorTest {

    private val validator = PasswordPolicyValidator()

    @ParameterizedTest
    @ValueSource(
        strings = [
            "S5eUBk",
            "yswBYn1",
            "1bZc!YBI",
            "&%Tf3TOhV",
            "g2CiEZ",
            "aiVuB6CbZy",
            "2ot0ceSi9T",
            "InDa3l#4*&^Iwae",
            "QOkpYLOEI2",
            "Q5NHxcTiMgU07gCosFdt5HxhW",
            "Gqn8rp5GcJe4xAf2DvgGgO9fV",
            "CsePvsLdRAVsZFd3XMf6EsLUW",
            "260oz6U  wnvh1WMdWJ1QP8D8fj",
            "JX3EmkXCp*^(%*%$%&\$bVft0h6a3Z8PbxYn",
            "FovD51nlTD!@#$%^&`8()±+_+|\\?/~<>,,.±§",
            "3z31hCYAWSEup3wa_++--2euZ3MjiSgGKUUhJljIkIc6KMXPt",
            "FXt6PGNah2vhSLfD7thU45vOedfpXTgaPy8Ws2K3P6RFVtRfl",
            "TKPQLSvMNNldYJhq98alOWGfHQ5pW5hSQIyZAycY1sHzf375a",
            "0P14voZ7uvnhPaGF23B3YdMwe5Q7jSCAsnUgac5jzORl1Jcs2",
            "EVdd3BEkAvk1Edoq8XzPJM2QPJtkoQQvHtyyXg1vGrZUVZUyn",
            "x2tohmvk6787xdowmu2qiepan14gxkegcvasqm09i0etl4eyA3",
        ]
    )
    fun `should process valid passwords`(password: String) {
        // then
        assertDoesNotThrow {
            validator.validate(password)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "aA1",
            "aA1!!",
            "aA1@!",
            "!A1a ",
        ]
    )
    fun `should throw TOO_SHORT`(password: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsExactly("TOO_SHORT:min=6")
            }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "P9Q3Fepd38xc93cZeHmk0U9w1DgSL%hm6 CvB#4C\$l*f\$5fVR%X",
            "1*&rwzDmLwvQe@U#XtaHKmO5scR901e\$ZNiiV1q#seSjhkbH!r_",
            "qAK9*nOBufQjBSz7QI5Fg@AGEODjckN 1...pFmjUqILyGZE\$U9u8*7",
            "gwybp56wj3%XoZ1IjV%xLotvwYi1hl7exdQ!VE!B0#9G431B4db\$tNR4!%skQ81H5IJqmL3vx^o"

        ]
    )
    fun `should throw TOO_LONG`(password: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsExactly("TOO_LONG:max=50")
            }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "AAAAA1A",
            "AAA11B",
            "NB3#XZN598ZOJ77J3\$GL!KN2%,.,.",
            "3TKLMCNKOK 38!53%EV8\$AM#Q^",
            "3YA78GU\$AK7E@6N0ELW449V1&"
        ]
    )
    fun `should throw INSUFFICIENT_LOWERCASE`(password: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsExactly("INSUFFICIENT_LOWERCASE:min=1")
            }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "aaaaa1",
            "1bbbbb",
            "1aa2@@@",
            "r1w8t*qygpvqbfu1&wx7#f7i*",
            "r48*%dq*k%d#wl4!ps5&i3&p!",
            "bratk9375!\$j^!wbist6t7syv",
        ]
    )
    fun `should throw INSUFFICIENT_UPPERCASE`(password: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsExactly("INSUFFICIENT_UPPERCASE:min=1")
            }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "aaaaaA",
            "Abbbbb",
            "ZaaV@@@",
            "FJzlyQ#iWJuht^GuSAxhStkkT",
            "F^LqNB\$r",
            "dckomJhOYY!Nu%ISGPxjrRVQp&aiUJRrQYILSfMboV^beIqOP"
        ]
    )
    fun `should throw INSUFFICIENT_DIGIT`(password: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsExactly("INSUFFICIENT_DIGIT:min=1")
            }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "aaa, TOO_SHORT:min=6+INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "ZZZ, TOO_SHORT:min=6+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "111, TOO_SHORT:min=6+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1",
            "!!!, TOO_SHORT:min=6+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "aaaaaa, INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "ZZZZZZ, INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "111111, INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1",
            "!!!!!!, INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, TOO_LONG:max=50+INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ, TOO_LONG:max=50+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
            "111111111111111111111111111111111111111111111111111, TOO_LONG:max=50+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1",
            "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!, TOO_LONG:max=50+INSUFFICIENT_LOWERCASE:min=1+INSUFFICIENT_UPPERCASE:min=1+INSUFFICIENT_DIGIT:min=1",
        ]
    )
    fun `should throw multiple validation errors at one`(password: String, errorsCodes: String) {
        // then
        assertThatThrownBy { validator.validate(password) }
            .isInstanceOfSatisfying(PasswordPolicyValidationException::class.java) {
                assertThat(it.errors).containsAll(errorsCodes.split("+"))
            }
    }
}
