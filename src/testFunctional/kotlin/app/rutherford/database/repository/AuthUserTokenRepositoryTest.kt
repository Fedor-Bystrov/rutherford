package app.rutherford.database.repository

import app.rutherford.FunctionalTest
import app.rutherford.database.entity.AuthUser
import app.rutherford.database.entity.AuthUserToken
import app.rutherford.database.exception.EntityNotFoundException
import app.rutherford.database.transaction.transaction
import app.rutherford.fixtures.anAuthUser
import app.rutherford.fixtures.anAuthUserToken
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

class AuthUserTokenRepositoryTest : FunctionalTest() {
    companion object {
        private val tokenId1 = randomUUID()
        private val tokenId2 = randomUUID()
        private val tokenId3 = randomUUID()

        @JvmStatic
        private fun tokenIds() = Stream.of(arguments(tokenId1), arguments(tokenId2), arguments(tokenId3))
    }

    private lateinit var user1: AuthUser
    private lateinit var user2: AuthUser
    private lateinit var user3: AuthUser

    private lateinit var token1: AuthUserToken
    private lateinit var token2: AuthUserToken
    private lateinit var token3: AuthUserToken

    @BeforeEach
    fun setUp() {
        user1 = anAuthUser().lastLogin(null).emailConfirmed(false).build()
        user2 = anAuthUser().emailConfirmed(true).build()
        user3 = anAuthUser().build()

        token1 = anAuthUserToken().id(tokenId1).userId(user1.id).build()
        token2 = anAuthUserToken().id(tokenId2).userId(user2.id).build()
        token3 = anAuthUserToken().id(tokenId3).userId(user3.id).build()

        transaction {
            authUserRepository.insert(
                it, listOf(
                    user1,
                    user2,
                    user3
                )
            )
            authUserTokenRepository.insert(
                it, listOf(
                    token1,
                    token2,
                    token3
                )
            )
        }
    }

    @ParameterizedTest
    @MethodSource("tokenIds")
    fun `should find correct auth_user_token by id`(tokenId: UUID) {
        // given
        val expected = getExpectedToken(tokenId)

        // when
        val result = authUserTokenRepository.find(id = tokenId)

        // then
        assertNotNull(result)
        assertThat(result.id).isEqualTo(expected.id)
        assertThat(result.createdAt).isEqualTo(expected.createdAt)
        assertThat(result.updatedAt).isEqualTo(expected.updatedAt)
        assertThat(result.expiration).isEqualTo(expected.expiration)
        assertThat(result.state).isEqualTo(expected.state)
        assertThat(result.tokenHash).isEqualTo(expected.tokenHash)
        assertThat(result.userId).isEqualTo(expected.userId)
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("tokenIds")
    fun `should find correct auth_user_token by id in transaction`(tokenId: UUID) {
        // given
        val expected = getExpectedToken(tokenId)

        // when
        val result = transaction {
            authUserTokenRepository.find(id = tokenId)
        }

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("tokenIds")
    fun `should get correct auth_user_token by id`(tokenId: UUID) {
        // given
        val expected = getExpectedToken(tokenId)

        // when
        val result = authUserTokenRepository.get(id = tokenId)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should throw when get cannot find auth_user_token by id`() {
        // given
        val id = randomUUID()

        // then
        assertThatThrownBy { authUserTokenRepository.get(id = id) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("auth_user_token with id: $id not found")
    }

    @Test
    fun `should insert single entity`() {
        // given
        val token = anAuthUserToken().userId(user1.id).build()

        // when
        val result = transaction {
            authUserTokenRepository.insert(it, token)
        }

        // then
        assertThat(result).isEqualTo(token)

        // and
        val created = authUserTokenRepository.find(id = token.id)
        assertThat(created).isEqualTo(token)
    }

    // TODO impl:

//    @Test
//    fun `should insert single entity and find it in the same transaction`() {
//        // given
//        val user = anAuthUser().build()
//
//        // when
//        transaction {
//            authUserRepository.insert(it, user)
//
//            val result = authUserRepository.find(it, user.id)
//            assertThat(result).isEqualTo(user)
//        }
//
//        // then
//        val createdUser = authUserRepository.get(id = user.id)
//        assertThat(createdUser).isEqualTo(user)
//    }
//
//    @Test
//    fun `should update single entity`() {
//        // given
//        val updatedUser = user1.confirmEmail()
//
//        // when
//        val result = transaction {
//            authUserRepository.update(it, updatedUser)
//        }
//
//        // then
//        assertThat(result).isEqualTo(updatedUser)
//
//        // and
//        val foundUser = authUserRepository.get(id = user1.id)
//        assertThat(foundUser).isEqualTo(updatedUser)
//        assertThat(foundUser.emailConfirmed).isTrue
//    }
//
//    @Test
//    fun `should update single entity and see changes in the same transaction`() {
//        // when
//        transaction {
//            authUserRepository.update(it, user1.confirmEmail())
//
//            // then
//            val updated = authUserRepository.get(it, user1.id)
//            assertThat(updated.emailConfirmed).isTrue
//        }
//
//        // and
//        val foundUser = authUserRepository.get(id = user1.id)
//        assertThat(foundUser.emailConfirmed).isTrue
//    }

    private fun getExpectedToken(id: UUID): AuthUserToken = when (id) {
        tokenId1 -> token1
        tokenId2 -> token2
        tokenId3 -> token3
        else -> throw IllegalArgumentException("token with id $id not found")
    }
}