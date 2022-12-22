package app.rutherford.auth.util

//import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
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
        ]
    )
    fun `should process valid passwords`(password: String) {
        // then
        assertDoesNotThrow {
            validator.validate(password)
        }
    }
}
