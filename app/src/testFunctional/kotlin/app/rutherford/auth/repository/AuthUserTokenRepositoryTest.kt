package app.rutherford.auth.repository

import app.rutherford.FunctionalTest
import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUserToken
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.exception.EntityNotFoundException
import app.rutherford.core.transaction.transaction
import app.rutherford.core.types.Base64
import app.rutherford.fixtures.anAuthUser
import app.rutherford.fixtures.anAuthUserToken
import app.rutherford.fixtures.randomBase64
import org.apache.commons.lang3.RandomUtils.nextBytes
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
        private val tokenId1 = Id<AuthUserToken>(randomUUID())
        private val tokenId2 = Id<AuthUserToken>(randomUUID())
        private val tokenId3 = Id<AuthUserToken>(randomUUID())

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

        token1 = anAuthUserToken().id(tokenId1).userId(user1.id()).build()
        token2 = anAuthUserToken().id(tokenId2).userId(user2.id()).build()
        token3 = anAuthUserToken().id(tokenId3).userId(user3.id()).build()

        transaction {
            authUserRepository.insert(
                this, listOf(
                    user1,
                    user2,
                    user3
                )
            )
            authUserTokenRepository.insert(
                this, listOf(
                    token1,
                    token2,
                    token3
                )
            )
        }
    }

    @ParameterizedTest
    @MethodSource("tokenIds")
    fun `should find correct auth_user_token by id`(tokenId: Id<AuthUserToken>) {
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
        assertThat(result.salt).isEqualTo(expected.salt)
        assertThat(result.tokenHash).isEqualTo(expected.tokenHash)
        assertThat(result.userId).isEqualTo(expected.userId)
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("tokenIds")
    fun `should find correct auth_user_token by id in transaction`(tokenId: Id<AuthUserToken>) {
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
    fun `should get correct auth_user_token by id`(tokenId: Id<AuthUserToken>) {
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
        val id = Id<AuthUserToken>(randomUUID())

        // then
        assertThatThrownBy { authUserTokenRepository.get(id = id) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("auth_user_token with id: ${id.value} not found")
    }

    @Test
    fun `should insert single entity`() {
        // given
        val token = anAuthUserToken().userId(user1.id()).build()

        // when
        val result = transaction {
            authUserTokenRepository.insert(this, token)
        }

        // then
        assertThat(result).isEqualTo(token)

        // and
        val created = authUserTokenRepository.find(id = token.id())
        assertThat(created).isEqualTo(token)
    }

    @Test
    fun `should insert single entity and find it in the same transaction`() {
        // given
        val token = anAuthUserToken().userId(user2.id()).build()

        // when
        transaction {
            authUserTokenRepository.insert(this, token)

            // then
            val result = authUserTokenRepository.find(this, token.id())
            assertThat(result).isEqualTo(token)
        }

        // and
        val created = authUserTokenRepository.get(id = token.id())
        assertThat(created).isEqualTo(token)
    }

    @Test
    fun `should update single entity`() {
        // given
        val tokenHash = Base64.encode(nextBytes(32))
        val token = token1.withTokenHash(tokenHash)

        // when
        val result = transaction {
            authUserTokenRepository.update(this, token)
        }

        // then
        assertThat(result).isEqualTo(token)

        // and
        val found = authUserTokenRepository.get(id = token1.id())
        assertThat(found).isEqualTo(token)
        assertThat(found.tokenHash).isEqualTo(tokenHash)
    }

    @Test
    fun `should update single entity and see changes in the same transaction`() {
        // given
        val tokenHash = randomBase64(32)

        // when
        transaction {
            authUserTokenRepository.update(this, token3.withTokenHash(tokenHash))

            // then
            val updated = authUserTokenRepository.get(this, token3.id())
            assertThat(updated.tokenHash).isEqualTo(tokenHash)
        }

        // and
        val found = authUserTokenRepository.get(id = token3.id())
        assertThat(found.tokenHash).isEqualTo(tokenHash)
    }

    private fun getExpectedToken(id: Id<AuthUserToken>): AuthUserToken = when (id) {
        tokenId1 -> token1
        tokenId2 -> token2
        tokenId3 -> token3
        else -> throw IllegalArgumentException("token with id $id not found")
    }
}
