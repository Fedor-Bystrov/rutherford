package app.rutherford.database.repository

import app.rutherford.FunctionalTest
import app.rutherford.database.entity.AuthUser
import app.rutherford.database.exception.EntityNotFoundException
import app.rutherford.database.transaction.transaction
import app.rutherford.fixtures.anAuthUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
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
        private val userId1 = randomUUID()
        private val userId2 = randomUUID()
        private val userId3 = randomUUID()

        @JvmStatic
        private fun userIds() = Stream.of(arguments(userId1), arguments(userId2), arguments(userId3))
    }

    private lateinit var user1: AuthUser
    private lateinit var user2: AuthUser
    private lateinit var user3: AuthUser

    @BeforeEach
    fun setUp() {
        user1 = anAuthUser().id(userId1).lastLogin(null).build()
        user2 = anAuthUser().id(userId2).emailConfirmed(true).build()
        user3 = anAuthUser().id(userId3).build()

        transaction {
            authUserRepository.insert(
                it, listOf(
                    user1,
                    user2,
                    user3
                )
            )
        }
    }

    @AfterEach
    fun tearDown() {
        super.afterEach()
        transaction {
            authUserRepository.delete(
                it, listOf(
                    user1,
                    user2,
                    user3,
                )
            )
        }
    }

    @ParameterizedTest
    @MethodSource("userIds")
    fun `should find correct auth_user by id`(userId: UUID) {
        // given
        val expected = getExpectedUser(userId)

        // when
        val result = authUserRepository.find(userId)

        // then
        assertNotNull(result)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.createdAt).isEqualTo(expected.createdAt)
        assertThat(result.updatedAt).isEqualTo(expected.updatedAt)
        assertThat(result.lastLogin).isEqualTo(expected.lastLogin)
        assertThat(result.applicationName).isEqualTo(expected.applicationName)
        assertThat(result.email).isEqualTo(expected.email)
        assertThat(result.emailConfirmed).isEqualTo(expected.emailConfirmed)
        assertThat(result.passwordHash).isEqualTo(expected.passwordHash)
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should find correct auth_user by collection of ids`() {
        // given
        val users = listOf(user1, user2, user3)

        // when
        val result = authUserRepository.find(users.map { it.id })

        // then
        assertThat(result).containsAll(users)
    }

    @ParameterizedTest
    @MethodSource("userIds")
    fun `should get correct auth_user by id`(userId: UUID) {
        // given
        val expected = getExpectedUser(userId)

        // when
        val result = authUserRepository.get(userId)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should throw when get cannot find user by id`() {
        // given
        val id = randomUUID()

        assertThatThrownBy { authUserRepository.get(id) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("auth_user with id: $id not found")
    }

    // TODO add test for the rest of method

    private fun getExpectedUser(id: UUID): AuthUser = when (id) {
        userId1 -> user1
        userId2 -> user2
        userId3 -> user3
        else -> throw IllegalArgumentException("user with id $id not found")
    }
}