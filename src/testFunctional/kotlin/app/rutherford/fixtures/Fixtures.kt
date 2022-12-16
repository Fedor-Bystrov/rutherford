package app.rutherford.fixtures

import app.rutherford.ApplicationName.TEST
import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.database.entity.Entity.State.CREATED
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

fun anAuthUser() = authUser()
    .id(randomUUID())
    .createdAt(now())
    .updatedAt(now())
    .lastLogin(now())
    .applicationName(TEST)
    .email("${randomAlphabetic(10)}@test.com")
    .emailConfirmed(false)
    .passwordHash(randomAlphanumeric(50))

fun anAuthUserToken() = authUserToken()
    .id(randomUUID())
    .createdAt(now())
    .updatedAt(now())
    .expiration(now())
    .state(CREATED)
    .tokenHash(randomAlphanumeric(50))
    .userId(randomUUID())
