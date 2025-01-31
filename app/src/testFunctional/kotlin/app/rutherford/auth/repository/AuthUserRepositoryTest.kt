package app.rutherford.auth.repository

import app.rutherford.FunctionalTest
import app.rutherford.auth.entity.AuthUser
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.ApplicationName.TEST2
import app.rutherford.core.ErrorCode.ENTITY_NOT_FOUND
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.exception.EntityNotFoundException
import app.rutherford.core.transaction.transaction
import app.rutherford.fixtures.anAuthUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.UUID.randomUUID
import java.util.stream.Stream
import kotlin.test.assertNotNull

class AuthUserRepositoryTest : FunctionalTest() {
    companion object {
        private val userId1 = Id<AuthUser>(randomUUID())
        private val userId2 = Id<AuthUser>(randomUUID())
        private val userId3 = Id<AuthUser>(randomUUID())

        @JvmStatic
        private fun userIds() = Stream.of(arguments(userId1), arguments(userId2), arguments(userId3))
    }

    private lateinit var userWithNotConfirmedEmail: AuthUser
    private lateinit var user2: AuthUser
    private lateinit var user3: AuthUser

    @BeforeEach
    fun setUp() {
        userWithNotConfirmedEmail = anAuthUser().id(userId1).lastLogin(null).emailConfirmed(false).build()
        user2 = anAuthUser().id(userId2).emailConfirmed(true).build()
        user3 = anAuthUser().id(userId3).applicationName(TEST2).build()

        transaction {
            authUserRepository.insert(
                this, listOf(
                    userWithNotConfirmedEmail,
                    user2,
                    user3
                )
            )
        }
    }

    @ParameterizedTest
    @MethodSource("userIds")
    fun `should find correct auth_user by id`(userId: Id<AuthUser>) {
        // given
        val expected = getExpectedUser(userId)

        // when
        val result = authUserRepository.find(id = userId)

        // then
        assertNotNull(result)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.id()).isEqualTo(expected.id())
        assertThat(result.createdAt).isEqualTo(expected.createdAt)
        assertThat(result.updatedAt).isEqualTo(expected.updatedAt)
        assertThat(result.lastLogin).isEqualTo(expected.lastLogin)
        assertThat(result.applicationName).isEqualTo(expected.applicationName)
        assertThat(result.email).isEqualTo(expected.email)
        assertThat(result.emailConfirmed).isEqualTo(expected.emailConfirmed)
        assertThat(result.salt).isEqualTo(expected.salt)
        assertThat(result.passwordHash).isEqualTo(expected.passwordHash)
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("userIds")
    fun `should find correct auth_user by id in transaction`(userId: Id<AuthUser>) {
        // given
        val expected = getExpectedUser(userId)

        // when
        val result = transaction {
            authUserRepository.find(this, id = userId)
        }

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return empty result when cannot find by id`() {
        // given
        val id = Id<AuthUser>(randomUUID())

        // when
        val result = authUserRepository.find(id = id)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should find correct auth_user by collection of ids`() {
        // given
        val users = listOf(userWithNotConfirmedEmail, user2, user3)

        // when
        val result = authUserRepository.find(ids = users.map { it.id() })

        // then
        assertThat(result).containsAll(users)
    }

    @Test
    fun `should find correct auth_user by email and application`() {
        // when
        val result = authUserRepository.findBy(email = user3.email, application = user3.applicationName)

        // then
        assertThat(result).isEqualTo(user3)
    }

    @Test
    fun `should find correct auth_user by email and application in transaction`() {
        // when
        val result = transaction {
            authUserRepository.findBy(this, email = user3.email, application = TEST2)
        }

        // then
        assertThat(result).isEqualTo(user3)
    }

    @Test
    fun `should return empty result when cannot find by email and applicaiton`() {
        // when
        val result = authUserRepository.findBy(email = user3.email, application = TEST1)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should find correct auth_user by collection of ids in transaction`() {
        // given
        val users = listOf(userWithNotConfirmedEmail, user2, user3)

        // when
        val result = transaction {
            authUserRepository.find(this, ids = users.map { it.id() })
        }

        // then
        assertNotNull(result)
        assertThat(result.size).isEqualTo(users.size)
        assertThat(result).containsAll(users)
    }

    @ParameterizedTest
    @MethodSource("userIds")
    fun `should get correct auth_user by id`(userId: Id<AuthUser>) {
        // given
        val expected = getExpectedUser(userId)

        // when
        val result = authUserRepository.get(id = userId)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should throw when get cannot find user by id`() {
        // given
        val id = Id<AuthUser>(randomUUID())

        // then
        assertThatThrownBy { authUserRepository.get(id = id) }
            .isInstanceOfSatisfying(EntityNotFoundException::class.java) {
                assertThat(it.errorCode()).isEqualTo(ENTITY_NOT_FOUND)
                assertThat(it.message).isEqualTo("auth_user with id: ${id.value} not found")
            }
    }

    @Test
    fun `should insert single entity`() {
        // given
        val user = anAuthUser().build()

        // when
        val result = transaction {
            authUserRepository.insert(this, user)
        }

        // then
        assertThat(result).isEqualTo(user)

        // and
        val createdUser = authUserRepository.find(id = user.id())
        assertThat(createdUser).isEqualTo(user)
    }

    @Test
    fun `should insert single entity and find it in the same transaction`() {
        // given
        val user = anAuthUser().build()

        // when
        transaction {
            authUserRepository.insert(this, user)

            val result = authUserRepository.find(this, user.id())
            assertThat(result).isEqualTo(user)
        }

        // then
        val createdUser = authUserRepository.get(id = user.id())
        assertThat(createdUser).isEqualTo(user)
    }

    @Test
    fun `should update single entity`() {
        // given
        val updatedUser = userWithNotConfirmedEmail.confirmEmail()

        // when
        val result = transaction {
            authUserRepository.update(this, updatedUser)
        }

        // then
        assertThat(result).isEqualTo(updatedUser)

        // and
        val foundUser = authUserRepository.get(id = userWithNotConfirmedEmail.id())
        assertThat(foundUser).isEqualTo(updatedUser)
        assertThat(foundUser.emailConfirmed).isTrue
    }

    @Test
    fun `should update single entity and see changes in the same transaction`() {
        // when
        transaction {
            authUserRepository.update(this, userWithNotConfirmedEmail.confirmEmail())

            // then
            val updated = authUserRepository.get(this, userWithNotConfirmedEmail.id())
            assertThat(updated.emailConfirmed).isTrue
        }

        // and
        val foundUser = authUserRepository.get(id = userWithNotConfirmedEmail.id())
        assertThat(foundUser.emailConfirmed).isTrue
    }

    private fun getExpectedUser(id: Id<AuthUser>): AuthUser = when (id) {
        userId1 -> userWithNotConfirmedEmail
        userId2 -> user2
        userId3 -> user3
        else -> throw IllegalArgumentException("user with id $id not found")
    }
}
