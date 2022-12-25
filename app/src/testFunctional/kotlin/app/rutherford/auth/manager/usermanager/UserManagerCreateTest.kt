package app.rutherford.auth.manager.usermanager

import app.rutherford.FunctionalTest
import app.rutherford.auth.entity.AuthUser
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
                    .email("test@test.com")
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

    @Test
    fun `should throw when password don't satisfy policy`() {
        TODO("implement")
    }

    @Test
    fun `should throw when email and application combination already exist`() {
        TODO("implement")
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
