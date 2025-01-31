package app.rutherford.auth.resource

import app.rutherford.FunctionalTest
import app.rutherford.core.ApplicationName
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.ApplicationName.TEST2
import app.rutherford.core.transaction.transaction
import app.rutherford.fixtures.anAuthUser
import io.javalin.http.HttpStatus.BAD_REQUEST
import io.javalin.http.HttpStatus.CREATED
import io.javalin.http.HttpStatus.NOT_ACCEPTABLE
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.HttpHeader.ORIGIN
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.Instant
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
            ),
            arguments(
                JSONObject()
                    .put("email", "test@test.com")
                    .put("password1", "")
                    .put("password2", ""),
                listOf("NULL_OR_BLANK_PARAM: password1", "NULL_OR_BLANK_PARAM: password2")
            ),
            arguments(
                JSONObject()
                    .put("email", "test@test.com")
                    .put("password1", "password1")
                    .put("password2", ""),
                listOf("NULL_OR_BLANK_PARAM: password2", "PASSWORDS_MISMATCH")
            ),
            arguments(
                JSONObject()
                    .put("email", "test@test.com")
                    .put("password1", "")
                    .put("password2", "password2"),
                listOf("NULL_OR_BLANK_PARAM: password1", "PASSWORDS_MISMATCH")
            ),
            arguments(
                JSONObject()
                    .put("email", "test@test.com")
                    .put("password1", "password1")
                    .put("password2", "password2"),
                listOf("PASSWORDS_MISMATCH")
            ),
            arguments(
                JSONObject()
                    .put("email", "")
                    .put("password1", "password1")
                    .put("password2", "password2"),
                listOf("MALFORMED_EMAIL", "PASSWORDS_MISMATCH")
            ),
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
    fun `should validate request body`(
        body: JSONObject,
        expectedErrors: Collection<String>
    ) {
        // when
        val response = http.post("/api/auth/sign-up", body)

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "code": "VALIDATION_ERROR",
                    "errors": [
                       ${expectedErrors.joinToString { "\"$it\"" }}
                    ]
                }""",
            response.body(), true
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "   ",
            "             ",
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
    fun `should validate email format`(email: String) {
        // given
        val body = JSONObject()
            .put("email", email)
            .put("password1", "password")
            .put("password2", "password")

        // when
        val response = http.post("/api/auth/sign-up", body)

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "code": "VALIDATION_ERROR",
                    "errors": [
                       "MALFORMED_EMAIL"
                    ]
                }""",
            response.body(), true
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "http:/:7171/",
            "htasdtp:/:7171/",
            "http://localhost:7171/",
            "http://localhost12:7070/",
            "http://test.localhost:7070/",
            "https://google.com/",
            "https://youtube.com/",
        ]
    )
    fun `should validate origin`(origin: String) {
        // given
        val body = JSONObject()
            .put("email", "test@email.com")
            .put("password1", "Passw0rd")
            .put("password2", "Passw0rd")

        // when
        val response = http.post("/api/auth/sign-up", body, mapOf(ORIGIN to origin))

        // then
        assertThat(response.statusCode()).isEqualTo(NOT_ACCEPTABLE.code)
        assertJsonEquals(
            """{
                    "code": "UNKNOWN_ORIGIN",
                }""",
            response.body(), true
        )
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
        ],
    )
    fun `should validate password policy`(password: String, expectedErrors: String) {
        // given
        val body = JSONObject()
            .put("email", "test@email.com")
            .put("password1", password)
            .put("password2", password)

        // when
        val response = http.post("/api/auth/sign-up", body, LOCALHOST_ORIGIN)

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "code": "PASSWORD_POLICY_VIOLATION",
                    "errors": [
                        ${expectedErrors.split('+').joinToString { "\"$it\"" }}
                    ]
                }""",
            response.body(), true
        )
    }

    @ParameterizedTest
    @EnumSource(value = ApplicationName::class)
    fun `should create user for each registered applicationName`(appName: ApplicationName) {
        // given
        val now = Instant.now()
        val email = "${appName.name.lowercase()}@email.com"
        val body = JSONObject()
            .put("email", email)
            .put("password1", "Passw0rd")
            .put("password2", "Passw0rd")

        // when
        val response = http.post("/api/auth/sign-up", body, mapOf(ORIGIN to appName.allowedHost.toString()))

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.code)
        assertThat(response.body()).isEmpty()

        // and
        val createdUser = authUserRepository.findBy(email = email, application = appName)
        assertThat(createdUser).isNotNull
        assertThat(createdUser?.id).isNotNull
        assertThat(createdUser?.createdAt).isAfter(now)
        assertThat(createdUser?.updatedAt).isAfter(now)
        assertThat(createdUser?.lastLogin).isNull()
        assertThat(createdUser?.applicationName).isEqualTo(appName)
        assertThat(createdUser?.email).isEqualTo(email)
        assertThat(createdUser?.emailConfirmed).isFalse
        assertThat(createdUser?.salt).isNotNull
        assertThat(createdUser?.passwordHash).isNotNull
    }

    @ParameterizedTest
    @EnumSource(value = ApplicationName::class)
    fun `should return error when email is already registered for applicationName`(appName: ApplicationName) {
        // given
        val existingUser = transaction {
            authUserRepository.insert(
                this, anAuthUser()
                    .emailConfirmed(true)
                    .applicationName(appName)
                    .email("${appName.name.lowercase()}@email.com")
                    .build()
            )
        }!!
        val body = JSONObject()
            .put("email", existingUser.email)
            .put("password1", "Passw0rd")
            .put("password2", "Passw0rd")

        // when
        val response = http.post(
            "/api/auth/sign-up",
            body,
            mapOf(ORIGIN to existingUser.applicationName.allowedHost.toString())
        )

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        assertJsonEquals(
            """{
                    "code": "USER_ALREADY_EXIST"
                }""",
            response.body(), true
        )
    }

    @Test
    fun `should create user with same email but different applicationName`() {
        // given
        val existingUser = transaction {
            authUserRepository.insert(
                this, anAuthUser()
                    .emailConfirmed(true)
                    .applicationName(TEST1)
                    .build()
            )
        }!!
        val body = JSONObject()
            .put("email", existingUser.email)
            .put("password1", "Passw0rd")
            .put("password2", "Passw0rd")

        // when
        val response = http.post("/api/auth/sign-up", body, mapOf(ORIGIN to TEST2.allowedHost.toString()))

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.code)
        assertThat(response.body()).isEmpty()

        // and
        val createdUser = authUserRepository.findBy(email = existingUser.email, application = TEST2)
        assertThat(createdUser).isNotNull

        // and
        assertThat(createdUser).isNotEqualTo(existingUser)
        assertThat(createdUser?.email).isEqualTo(existingUser.email)
        assertThat(createdUser?.applicationName).isEqualTo(TEST2)
        assertThat(createdUser?.salt).isNotEqualTo(existingUser.salt)
        assertThat(createdUser?.passwordHash).isNotEqualTo(existingUser.passwordHash)
    }
}
