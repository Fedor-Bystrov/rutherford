package app.rutherford.auth.util

import app.rutherford.core.types.Base64.Companion.base64
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.withSettings
import java.io.File
import java.security.SecureRandom

class Argon2DigestTest {
    private val salt = base64("meR2zPnoFtNbk4fXTxN8yw==")
    private val secret = base64("dGhpcyBpcyBzdXBlciBzZWNyZXQga2V5Cg==")

    private val secureRandom = mock(SecureRandom::class.java, withSettings().withoutAnnotations())
    private val argon2 = Argon2Digest(secret, secureRandom)

    @BeforeEach
    fun setUp() {
        Mockito.doAnswer {
            val arr = it.getArgument(0) as ByteArray
            System.arraycopy(salt.decodeBytes(), 0, arr, 0, 16)
            null
        }.`when`(secureRandom).nextBytes(any())
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = '\t',
        textBlock = """
            password	jE2c5e4yOjHuBClmFIfw76d4eDhAdV5nHgSObfW7L9o=
            S5eUBk	1Jdz57+HtxSsRdKJb5Aeq9b4NdEzWgb6MCi0230j7Gc=
            yswBYn1	ZXqdOMyblkyBTyCG1b+0RjoNm9daWz3tmycf82pwocw=
            1bZc!YBI	O82bN+TWzRzHTnwaGz3x+bpQHEJ6AUNUaebPiP1J/lE=
            &%Tf3TOhV	X31VLgHrVHBWtR6lEFALVin0Xesu4bcBPCNQAedX3Zw=
            g2CiEZ	TyWS8xbzPJoCr0P4WjmDiOI02tNdypzQ+1arjVWjxyM=
            aiVuB6CbZy	tyZs2D1D3geHh4wfO5lIhfZoso9Udy2xCPBKppqUPE0=
            2ot0ceSi9T	oBBRCppJtvx8hKwKtbHtYQDK/5YbNSw9/IGs/CzQI0k=
            InDa3l#4*&^Iwae	ppWORA0uid8SCn+QVPo9BmMDv5+XeLC9jG78xMsWxkE=
            QOkpYLOEI2	AI3GP4BO5CJ9+aPYv7l0Ychx+zqq80+REjGzquNDKmM=
            Q5NHxcTiMgU07gCosFdt5HxhW	JHgXrvtKyq8+N5m0mJk8Qwy6n45jxhAaIL10kYjK5m8=
            Gqn8rp5GcJe4xAf2DvgGgO9fV	cJwNYfkJQdTSSevGWT6gCrExcyQw6B7LWPfYEq/TwgE=
            CsePvsLdRAVsZFd3XMf6EsLUW	gE3xKerSqe/GufNPVCjhkP9H86zmFivaTcwc1b34Jls=
            260oz6U  wnvh1WMdWJ1QP8D8fj	V3fJsaOTUpp4ARWrIwLuguAO1Y5G8bs/Rr2WKwbluTk=
            JX3EmkXCp*^(%*%${'$'}%&\${'$'}bVft0h6a3Z8PbxYn	wjCD8JpiwHS2O5t07kv9fpo7hsIOqlsioRuKiI5jbNM=
            FovD51nlTD!@#${'$'}%^&`8()±+_+|\\?/~<>\,,.±§	iLzf+kWgVdaK6JvzYCFtZMboX8OdEV9il5QBcK8EvII=
            3z31hCYAWSEup3wa_++--2euZ3MjiSgGKUUhJljIkIc6KMXPt	mkpGXwRjqK0yPL12Wle77QNapGV9mRTdpnXrw4YRkgA=
            FXt6PGNah2vhSLfD7thU45vOedfpXTgaPy8Ws2K3P6RFVtRfl	EZt9nTUWqpNSUUt1Y0glIFpv+iFsCaeMf4R+GZu1wK8=
            TKPQLSvMNNldYJhq98alOWGfHQ5pW5hSQIyZAycY1sHzf375a	Fcfxb8sfhnF7aJsl6Lv/mCFNlgdVaNh6mjNxTshEfkE=
            0P14voZ7uvnhPaGF23B3YdMwe5Q7jSCAsnUgac5jzORl1Jcs2	PTcKmQhE5W04KZfeO+U1vqnwCyixKZ2y6fUD+/Cz66I=
            EVdd3BEkAvk1Edoq8XzPJM2QPJtkoQQvHtyyXg1vGrZUVZUyn	WE/jVB45YLltivdw5cxnjsQo0RV8xUFfkWmumDVqItI=
            x2tohmvk6787xdowmu2qiepan14gxkegcvasqm09i0etl4eyA3	dzJX+XBAGfehDVMNe6Lk6MR0rYx9qqqXm0quxt3x5I4=""",
    )
    fun `should generate correct hashes`(password: String, expectedHash: String) {
        // when
        val (resultSalt, resultHash) = argon2.hash(password)

        // then
        assertThat(resultSalt).isEqualTo(salt)
        assertThat(resultHash).isEqualTo(base64(expectedHash))
    }

    @Test
    fun `should generate correct hashes for most common passwords`() {
        // given
        val passwords = File("src/test/resources/password_hasher_test_data.csv").readLines()

        // when
        passwords.parallelStream().forEach {
            val (password, expectedHash) = it.split('\t')
            val (resultSalt, resultHash) = argon2.hash(password)

            // then
            assertThat(resultSalt).isEqualTo(salt)
            assertThat(resultHash).isEqualTo(base64(expectedHash))
        }
    }

    @Test
    fun `should generate correct hashes given salt`() {
        // given
        val salt = base64("NmesIWpwHotJJyt1SMKyROFR0A14ndWfZNGasBIe5aw=")
        val password = """13123sfvsdfg!(@(000!#@JG\;\;\./,,/.,/';"""
        val expectedHash = "wPXEmEwPZa97e8JYZncnoaxaTovwX5bYvAkFRz6jobs="

        // when
        val (resultSalt, resultHash) = argon2.hash(password, salt.decodeBytes())

        assertThat(resultSalt).isEqualTo(salt)
        assertThat(resultHash).isEqualTo(base64(expectedHash))
    }
}
