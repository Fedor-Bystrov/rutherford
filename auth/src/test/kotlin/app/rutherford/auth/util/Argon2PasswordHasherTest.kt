package app.rutherford.auth.util

import app.rutherford.core.types.Base64.Companion.base64
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.security.SecureRandom

class Argon2PasswordHasherTest {
    private val secret = base64("dGhpcyBpcyBzdXBlciBzZWNyZXQga2V5Cg==")
    private val secureRandom = SecureRandom()
    private val passwordHasher = Argon2PasswordHasher(secret, secureRandom)

    @BeforeEach
    fun setUp() {
        secureRandom.setSeed(12345L) // TODO doesn't work, it's idiot-proof
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "password, ...", // TODO
//        "S5eUBk",
//        "yswBYn1",
//        "1bZc!YBI",
//        "&%Tf3TOhV",
//        "g2CiEZ",
//        "aiVuB6CbZy",
//        "2ot0ceSi9T",
//        "InDa3l#4*&^Iwae",
//        "QOkpYLOEI2",
//        "Q5NHxcTiMgU07gCosFdt5HxhW",
//        "Gqn8rp5GcJe4xAf2DvgGgO9fV",
//        "CsePvsLdRAVsZFd3XMf6EsLUW",
//        "260oz6U  wnvh1WMdWJ1QP8D8fj",
//        "JX3EmkXCp*^(%*%$%&\$bVft0h6a3Z8PbxYn",
//        "FovD51nlTD!@#$%^&`8()±+_+|\\?/~<>,,.±§",
//        "3z31hCYAWSEup3wa_++--2euZ3MjiSgGKUUhJljIkIc6KMXPt",
//        "FXt6PGNah2vhSLfD7thU45vOedfpXTgaPy8Ws2K3P6RFVtRfl",
//        "TKPQLSvMNNldYJhq98alOWGfHQ5pW5hSQIyZAycY1sHzf375a",
//        "0P14voZ7uvnhPaGF23B3YdMwe5Q7jSCAsnUgac5jzORl1Jcs2",
//        "EVdd3BEkAvk1Edoq8XzPJM2QPJtkoQQvHtyyXg1vGrZUVZUyn",
//        "x2tohmvk6787xdowmu2qiepan14gxkegcvasqm09i0etl4eyA3",
        ]
    )
    fun `should generate correct hashes`(password: String) {
        // when
        val (salt, hash) = passwordHasher.hash(password)

        // then
        // TODO assert salt and hash are correct
    }
}
