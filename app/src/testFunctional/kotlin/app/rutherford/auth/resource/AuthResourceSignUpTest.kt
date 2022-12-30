package app.rutherford.auth.resource

import app.rutherford.FunctionalTest
import io.javalin.http.HttpStatus.BAD_REQUEST
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AuthResourceSignUpTest : FunctionalTest() {
    @Test
    fun `should returned MALFORMED_JSON when body is empty`() {
        // given
        // when
        val response = http.post("/api/auth/sign-up")

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.code)
        // TODO assertJsonEquals
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
