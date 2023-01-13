package app.rutherford.auth.manager

import app.rutherford.auth.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.repository.AuthUserTokenRepository
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.core.ApplicationName
import app.rutherford.core.transaction.transaction
import app.rutherford.core.types.Base64
import java.security.SecureRandom
import java.time.Duration
import java.time.Instant.now

class SignInManager(
    private val hasher: Argon2PasswordHasher,
    private val secureRandom: SecureRandom,
    private val userRepository: AuthUserRepository,
    private val tokenRepository: AuthUserTokenRepository,
    private val userManager: UserManager,
    private val jwtManager: JwtManager,
) {
    companion object {
        const val TOKEN_SIZE_IN_BYTES: Int = 32
        val REFRESH_TOKEN_DURATION: Duration = Duration.ofDays(7)
    }

    fun signIn(
        email: String,
        password: String,
        appNameFromRequest: ApplicationName,
    ) {
        val user = userRepository
            .findBy(email = email, application = appNameFromRequest)
            ?: throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception

        val passwordValid = userManager.isPasswordCorrect(user, password)
        if (!passwordValid) {
            throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception
        }

        // TODO is this impl secure? Is it safe to put unencrypted random token into user's cookie?

        val token = nextToken().toString()
        val (salt, tokenHash) = hasher.hash(token)

        transaction {
            tokenRepository.insert(
                this, authUserToken()
                    .userId(user.id())
                    .expiration(now().plus(REFRESH_TOKEN_DURATION))
                    .salt(salt)
                    .tokenHash(tokenHash)
                    .build()
            )
        }

        val accessToken = jwtManager.createToken(user) // TODO correct?

        // TODO return access token + refresh token (unencrypted)
    }

    // TODO extract to utility? This method used in TokenHasher as well
    private fun nextToken(): Base64 {
        val token = ByteArray(TOKEN_SIZE_IN_BYTES)
        secureRandom.nextBytes(token)
        return Base64.encode(token)
    }
}
