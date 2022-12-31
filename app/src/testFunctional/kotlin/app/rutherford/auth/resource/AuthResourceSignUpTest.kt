package app.rutherford.auth.resource

import app.rutherford.FunctionalTest
import io.javalin.http.HttpStatus.BAD_REQUEST
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import org.skyscreamer.jsonassert.JSONAssert.assertEquals as assertJsonEquals

class AuthResourceSignUpTest : FunctionalTest() {
    companion object {
        @JvmStatic
        fun malformedBodies(): Stream<Arguments> = Stream.of(
            arguments(null),
            arguments(JSONObject()),
            arguments(JSONObject().put("email", "")),
            arguments(JSONObject().put("password1", "")),
            arguments(JSONObject().put("password2", "")),
            arguments(JSONObject().put("email", "").put("password1", "")),
            arguments(JSONObject().put("email", "").put("password2", "")),
            arguments(JSONObject().put("password1", "").put("password2", "")),
            arguments(JSONObject().put("email", "test@email.com")),
            arguments(JSONObject().put("password1", "password1")),
            arguments(JSONObject().put("password2", "password2")),
            arguments(JSONObject().put("email", "test@email.com").put("password1", "password1")),
            arguments(JSONObject().put("email", "test@email.com").put("password2", "password2")),
            arguments(JSONObject().put("password1", "password1").put("password2", "password2")),
        )

        @JvmStatic
        fun emptyOrBlankBodies(): Stream<Arguments> = Stream.of(
            arguments(
                JSONObject()
                    .put("email", "")
                    .put("password1", "")
                    .put("password2", ""),
                listOf("MALFORMED_EMAIL", "NULL_OR_BLANK_PARAM: password1", "NULL_OR_BLANK_PARAM: password2")
            )
        )
    }

    @ParameterizedTest
    @MethodSource("malformedBodies")
    fun `should return DESERIALIZATION_FAILED on malformed request body`(body: JSONObject?) {
        // when
        val response = http.post("/api/auth/sign-up", body)

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

    @ParameterizedTest
    @MethodSource("emptyOrBlankBodies")
    fun `should return errors when parameters in request body are empty or blank`(
        body: JSONObject,
        errors: Collection<String>
    ) {
        // when
        val response = http.post("/api/auth/sign-up", body)

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "httpStatus": ${BAD_REQUEST.code},
                    "code": "VALIDATION_ERROR",
                    "errors": [
                       ${errors.joinToString { "\"$it\"" }}
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
