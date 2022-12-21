package app.rutherford.auth.service

import app.rutherford.auth.entity.AuthUser
import app.rutherford.core.util.Checks.validateNotBlank
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.exceptions.JWTVerificationException
import java.time.Duration
import java.time.Instant

data class AccessToken(val value: String) {
    init {
        validateNotBlank("value", value)
    }

    override fun toString() = "AccessToken(<masked_value>)"
}


// https://auth0.com/blog/refresh-tokens-what-are-they-and-when-to-use-them/
const val testKey = "3146b0f8f5413b2e6bd06b6e67412481dfaafc0915cb0c2be3afbfe438f7f7" +
        "0d9c000989c91dfb73c37a42134e7c79c77c01d3a52315b0da34ed85acd52d6d1f"


class AccessTokenService { // TODO add tests
    // TODO put HMAC256 key into pkcs-12 container with password
    // TODO pass key to AccessTokenService in AccessTokenConfig

    private val algorithm = HMAC256(testKey)

    fun createToken(authUser: AuthUser): AccessToken {
        val now = Instant.now()
        val applicationName = authUser.applicationName.name.lowercase()
        return AccessToken(
            JWT.create()
                .withIssuer(applicationName) // TODO or pass iss explicitly?
                .withSubject(authUser.id.toString())
                .withAudience(applicationName) // TODO or aud iss explicitly?
                .withExpiresAt(now.plus(Duration.ofMinutes(5)))
                .withIssuedAt(now)
                .sign(algorithm)
        )
    }

    fun verifyToken(accessToken: AccessToken) {
        val verifier = JWT.require(algorithm)
            .withIssuer("TODO issuer")
            .withAudience("TODO issuer")
            // TODO validate other claims
            // TODO check that iat is validated
            .build()

        try {
            val decodedJWT = verifier.verify(accessToken.value)
            decodedJWT.subject // TODO use decodedJWT to get user details and fetch it from db
        } catch (ex: JWTVerificationException) {
            // TODO Handle invalid signature/claims
            throw ex
        }
    }
}
