package app.rutherford.auth.manager

import app.rutherford.FunctionalTest
import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.ApplicationName.TEST2
import app.rutherford.core.ErrorCode.USER_ALREADY_EXIST
import app.rutherford.core.transaction.transaction
import app.rutherford.core.types.Base64.Companion.base64
import app.rutherford.fixtures.anAuthUser
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.Instant

class UserManagerCreateTest : FunctionalTest() {

    private lateinit var test1User: AuthUser

    @BeforeEach
    fun setUp() {
        test1User = transaction {
            authUserRepository.insert(
                this, anAuthUser()
                    .emailConfirmed(true)
                    .applicationName(TEST1)
                    .email("test@email.com")
                    .build()
            )
        }!!
    }

    @Test
    fun `should create user`() {
        // given
        val email = "${randomAlphabetic(10)}@test.com"
        val applicationName = TEST1
        val password = "Passw0rd"

        // when
        val result = userManager.create(email, applicationName, password)

        // then
        val minuteAgo = Instant.now().minusSeconds(60)
        assertThat(result.createdAt).isAfter(minuteAgo)
        assertThat(result.updatedAt).isAfter(minuteAgo)
        assertThat(result.lastLogin).isNull()
        assertThat(result.applicationName).isEqualTo(applicationName)
        assertThat(result.email).isEqualTo(email)
        assertThat(result.emailConfirmed).isFalse
        assertThat(result.salt.decodeBytes().size).isEqualTo(16)
        assertThat(result.passwordHash.decodeBytes().size).isEqualTo(32)

        // and
        val createdUser1 = authUserRepository.findBy(email = email, application = applicationName)
        assertThat(result).isEqualTo(createdUser1)

        // and
        val createdUser2 = authUserRepository.get(id = result.id())
        assertThat(result).isEqualTo(createdUser2)
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
            .hasMessage("NULL_OR_BLANK_PARAM: email")
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
        val email = test1User.email
        val applicationName = test1User.applicationName

        // then
        assertThatThrownBy { userManager.create(email, applicationName, "Passw0rd") }
            .isInstanceOfSatisfying(UserAlreadyExistException::class.java) {
                assertThat(it.errorCode()).isEqualTo(USER_ALREADY_EXIST)
            }
    }

    @Test
    fun `should create user with different application_name`() {
        // given
        val email = test1User.email
        val applicationName = TEST2
        val password = "Passw0rd"

        // when
        val result = userManager.create(email, applicationName, password)

        // then
        val minuteAgo = Instant.now().minusSeconds(60)
        assertThat(result.createdAt).isAfter(minuteAgo)
        assertThat(result.updatedAt).isAfter(minuteAgo)
        assertThat(result.lastLogin).isNull()
        assertThat(result.applicationName).isEqualTo(applicationName)
        assertThat(result.email).isEqualTo(test1User.email)
        assertThat(result.emailConfirmed).isFalse
        assertThat(result.salt.decodeBytes().size).isEqualTo(16)
        assertThat(result.passwordHash.decodeBytes().size).isEqualTo(32)

        // and
        val createdUser = authUserRepository.get(id = result.id())
        assertThat(result).isEqualTo(createdUser)

        // and
        assertThat(createdUser.salt).isNotEqualTo(test1User.salt)
        assertThat(createdUser.passwordHash).isNotEqualTo(test1User.passwordHash)
    }

    @Test
    fun `should return false when password is incorrect`() {
        // given
        val password = randomAlphanumeric(10)
        val user = anAuthUser().build()

        // when
        val result = userManager.isPasswordCorrect(user, password)

        // then
        assertThat(result).isFalse
    }

    @Test
    fun `should return true when password is correct`() {
        // given
        val password = "lde2539UZu"
        val user = anAuthUser()
            .salt(base64("RwliP+Su6QcOThLRUw9UyQ=="))
            .passwordHash(base64("iCgJ2lVM7zwwHapOz2IeMPn7F7H0t1HZaeIqYMvmFgE="))
            .build()

        // when
        val result = userManager.isPasswordCorrect(user, password)

        // then
        assertThat(result).isTrue
    }
}
