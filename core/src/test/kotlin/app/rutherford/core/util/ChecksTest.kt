package app.rutherford.core.util

import app.rutherford.core.util.Checks.isValidEmail
import app.rutherford.core.util.Checks.validateEmailFormat
import app.rutherford.core.util.Checks.validateNotBlank
import app.rutherford.core.util.Checks.validateNotNull
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
        assertThatThrownBy { validateNotNull("param1234", null) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("NULL_PARAM: param1234")
    }

    @ParameterizedTest
    @ValueSource(strings = ["  1  ", "  a   a", "nu  ll", " asdadasdad ", "111"])
    fun `should validate not blank`(value: String) {
        assertThat(validateNotBlank("should not throw", value)).isEqualTo(value)
    }

    @Test
    fun `should throw from validateNotBlank if value is null`() {
        assertThatThrownBy { validateNotBlank("param12345", null) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("NULL_OR_BLANK_PARAM: param12345")
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "   ", "        "])
    fun `should throw if value is blank`(value: String) {
        assertThatThrownBy { validateNotBlank("param12345", value) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("NULL_OR_BLANK_PARAM: param12345")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "Leviblack@mail.com",
            "handsomeKelsey85@virgilio.it",
            "giftedLouis@facebook.com",
            "Billyuptight@comcast.net",
            "disturbedShannon@bol.com.br",
            "Wendyhurt@frontiernet.net",
            "aggressiveAnna@earthlink.net",
            "defeatedShannon16@live.it",
            "illAlison@live.fr",
            "pleasantTeresa@yahoo.com.br",
            "openMichael4@att.net",
            "mysteriousJonathon1@rediffmail.com",
            "uglyJay8@yahoo.co.uk",
            "aliveLogan48@frontiernet.net",
            "gloriousMarco@hotmail.fr",
            "Cristinablushing@tiscali.it",
            "Garrettoutrageous@googlemail.com",
            "terribleKimberly@qq.com",
            "splendidCody62@earthlink.net",
            "blushingRuth@hotmail.com",
            "a+_-_1@a.com"
        ]
    )
    fun `should validate email format`(value: String) {
        assertThat(validateEmailFormat(value)).isEqualTo(value)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "Leviblack@mail.com",
            "handsomeKelsey85@virgilio.it",
            "giftedLouis@facebook.com",
            "Billyuptight@comcast.net",
            "disturbedShannon@bol.com.br",
            "Wendyhurt@frontiernet.net",
            "aggressiveAnna@earthlink.net",
            "defeatedShannon16@live.it",
            "illAlison@live.fr",
            "pleasantTeresa@yahoo.com.br",
            "openMichael4@att.net",
            "mysteriousJonathon1@rediffmail.com",
            "uglyJay8@yahoo.co.uk",
            "aliveLogan48@frontiernet.net",
            "gloriousMarco@hotmail.fr",
            "Cristinablushing@tiscali.it",
            "Garrettoutrageous@googlemail.com",
            "terribleKimberly@qq.com",
            "splendidCody62@earthlink.net",
            "blushingRuth@hotmail.com",
            "a+_-_1@a.com"
        ]
    )
    fun `should return true for valid email`(value: String) {
        assertThat(isValidEmail(value)).isTrue
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "   ",
            "             "
        ]
    )
    fun `should throw if email is blank`(value: String) {
        assertThatThrownBy { validateEmailFormat(value) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("MALFORMED_EMAIL")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "   ",
            "             "
        ]
    )
    fun `should return false when email is blank`(value: String) {
        assertThat(isValidEmail(value)).isFalse
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "a@",
            "a@a",
            "a@@a",
            "test@@test.com",
            "@test.com",
            "test@com@test.com",
            "test@com@test..com",
            "test@com@test,com",
            "---@---",
        ]
    )
    fun `should throw if email format is not valid`(value: String) {
        assertThatThrownBy { validateEmailFormat(value) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("MALFORMED_EMAIL")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "a",
            "a@",
            "a@a",
            "a@@a",
            "test@@test.com",
            "@test.com",
            "test@com@test.com",
            "test@com@test..com",
            "test@com@test,com",
            "---@---",
        ]
    )
    fun `should return false when email format is not valid`(value: String) {
        assertThat(isValidEmail(value)).isFalse
    }
}
