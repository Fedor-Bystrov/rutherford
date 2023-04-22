package app.rutherford.fixtures

import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.types.Base64
import app.rutherford.core.util.Clock.now
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomUtils.nextBytes
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


fun randomBytes(count: Int): ByteArray = nextBytes(count)

fun randomBase64(count: Int): Base64 = Base64.encode(randomBytes(count))
