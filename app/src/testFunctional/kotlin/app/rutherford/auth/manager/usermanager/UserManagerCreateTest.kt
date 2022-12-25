package app.rutherford.auth.manager.usermanager

import app.rutherford.FunctionalTest
import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.transaction.transaction
import app.rutherford.fixtures.anAuthUser
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserManagerCreateTest : FunctionalTest() {

    private lateinit var user: AuthUser

    @BeforeEach
    fun setUp() {
        user = transaction {
            authUserRepository.insert(
                this, anAuthUser()
                    .emailConfirmed(true)
                    .applicationName(TEST1)
                    .email("test@email.com")
                    .build()
            )
        }!!
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "   ",
            "         "
        ]
    )
    fun `should throw when email is blank`(email: String) {
        // then
        assertThatThrownBy { userManager.create(email, TEST1, "Passw0rd") }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("email is null or blank")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "pass",
            "password",
            "Password"
        ]
    )
    fun `should throw when password don't satisfy policy`(password: String) {
        // then
        assertThatThrownBy { userManager.create("test@email.com", TEST1, password) }
            .isInstanceOf(PasswordPolicyValidationException::class.java)
    }

    @Test
    fun `should throw when email and application combination already exist`() {
        // given
        val email = user.email
        val applicationName = user.applicationName

        // then
        assertThatThrownBy { userManager.create(email, applicationName, "Passw0rd") }
            .isInstanceOf(UserAlreadyExistException::class.java)
            .hasMessage("User with $email and $applicationName already exist")
    }

    @Test
    fun `should create user`() {
        TODO("implement")
    }

    @Test
    fun `should create user with different application_name`() {
        TODO("implement")
    }
}
