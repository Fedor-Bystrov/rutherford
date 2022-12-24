package app.rutherford.fixtures

import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.entity.Entity.State.CREATED
import app.rutherford.core.types.Base64
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomUtils.nextBytes
import java.time.Instant.now
import java.util.UUID.randomUUID

fun anAuthUser() = authUser()
    .id(randomUUID())
    .createdAt(now())
    .updatedAt(now())
    .lastLogin(now())
    .applicationName(TEST1)
    .email("${randomAlphabetic(10)}@test.com")
    .emailConfirmed(false)
    .salt(randomBase64(16))
    .passwordHash(randomBase64(32))

fun anAuthUserToken() = authUserToken()
    .id(randomUUID())
    .createdAt(now())
    .updatedAt(now())
    .expiration(now())
    .state(CREATED)
    .salt(randomBase64(32))
    .tokenHash(randomBase64(32))
    .userId(Id(randomUUID()))


fun randomBytes(count: Int): ByteArray = nextBytes(count)

fun randomBase64(count: Int): Base64 = Base64.encode(randomBytes(count))
