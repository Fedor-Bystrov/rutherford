package app.rutherford.auth.resource

import app.rutherford.FunctionalTest
import io.javalin.http.HttpStatus.BAD_REQUEST
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals as assertJsonEquals

class AuthResourceSignUpTest : FunctionalTest() {
    @Test
    fun `should returned DESERIALIZATION_FAILED when body is null`() {
        // when
        val response = http.post("/api/auth/sign-up")

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "httpStatus": ${BAD_REQUEST.code},
                    "code": "VALIDATION_ERROR",
                    "errors": [
                        "DESERIALIZATION_FAILED"
                    ]
                }""",
            response.body(), true
        )
    }

    @Test
    fun `should returned DESERIALIZATION_FAILED when body is empty`() {
        // when
        val response = http.post("/api/auth/sign-up", JSONObject())

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "httpStatus": ${BAD_REQUEST.code},
                    "code": "VALIDATION_ERROR",
                    "errors": [
                        "DESERIALIZATION_FAILED"
                    ]
                }""",
            response.body(), true
        )
    }

    @Test
    fun `should validate email is not null`() {
        TODO("impl")
    }

    @Test
    fun `should validate email is not blank`() {
        TODO("impl")
    }

    @Test
    fun `should validate email format`() {
        TODO("impl")
    }

    @Test
    fun `should validate password1 is not null`() {
        TODO("impl")
    }

    @Test
    fun `should validate password1 is not blank`() {
        TODO("impl")
    }

    @Test
    fun `should validate password2 is not null`() {
        TODO("impl")
    }

    @Test
    fun `should validate password2 is not blank`() {
        TODO("impl")
    }

    @Test
    fun `should validate passwords match`() {
        TODO("impl")
    }

    // TODO add more tests
}
