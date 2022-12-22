package app.rutherford.fixtures

import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.entity.Entity.State.CREATED
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.entity.AuthUserToken.Builder.Companion.authUserToken
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
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
    .passwordHash(randomAlphanumeric(50))

fun anAuthUserToken() = authUserToken()
    .id(randomUUID())
    .createdAt(now())
    .updatedAt(now())
    .expiration(now())
    .state(CREATED)
    .tokenHash(randomAlphanumeric(50))
    .userId(Id(randomUUID()))
