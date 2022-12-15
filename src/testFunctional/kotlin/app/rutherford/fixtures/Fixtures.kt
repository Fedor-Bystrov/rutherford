package app.rutherford.fixtures

import app.rutherford.database.entity.AuthUser.Builder
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import java.time.Instant
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

fun anAuthUser(
    id: UUID? = null,
    createdAt: Instant? = null,
    updatedAt: Instant? = null,
    lastLogin: Instant? = null,
    applicationName: String? = null,
    email: String? = null,
    emailConfirmed: Boolean? = null,
    passwordHash: String? = null,
) = Builder.authUser()
    .id(id ?: randomUUID())
    .createdAt(createdAt ?: now())
    .updatedAt(updatedAt ?: now())
    .lastLogin(lastLogin ?: now())
    .applicationName(applicationName ?: randomAlphanumeric(10))
    .email(email ?: "${randomAlphabetic(10)}@test.com")
    .emailConfirmed(emailConfirmed ?: false)
    .passwordHash(passwordHash ?: randomAlphanumeric(50));