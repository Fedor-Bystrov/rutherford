package app.rutherford.database.entity

import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.util.Checks.validateNotBlank
import app.rutherford.util.Checks.validateNotNull
import java.time.Instant
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

// TODO add annotation for jackson to not (de)serialize this class
class AuthUser private constructor(builder: Builder) : Entity {
    val id: UUID
    val createdAt: Instant
    val updatedAt: Instant
    val lastLogin: Instant?
    val applicationName: String  // TODO use ENUM
    val email: String
    val emailConfirmed: Boolean
    val passwordHash: String

    init {
        id = validateNotNull("id", builder.id)
        createdAt = validateNotNull("createdAt", builder.createdAt)
        updatedAt = validateNotNull("updatedAt", builder.updatedAt)
        lastLogin = builder.lastLogin
        applicationName = validateNotBlank("applicationName", builder.applicationName)
        email = validateNotBlank("email", builder.email)
        emailConfirmed = validateNotNull("emailConfirmed", builder.emailConfirmed)
        passwordHash = validateNotBlank("passwordHash", builder.passwordHash)
    }

    fun confirmEmail(): AuthUser {
        if (this.emailConfirmed) return this
        return copy()
            .emailConfirmed(true)
            .build()
    }

    private fun copy(): Builder = authUser()
        .id(this.id)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .lastLogin(this.lastLogin)
        .applicationName(this.applicationName)
        .email(this.email)
        .emailConfirmed(this.emailConfirmed)
        .passwordHash(this.passwordHash)


    class Builder private constructor(
        var id: UUID? = null,
        var createdAt: Instant? = null,
        var updatedAt: Instant? = null,
        var lastLogin: Instant? = null,
        var applicationName: String? = null,
        var email: String? = null,
        var emailConfirmed: Boolean? = false,
        var passwordHash: String? = null
    ) {
        companion object {
            fun authUser(): Builder {
                val now = now()
                return Builder()
                    .id(randomUUID())
                    .createdAt(now)
                    .updatedAt(now)
            }
        }

        fun id(id: UUID?) = apply { this.id = id }
        fun createdAt(createdAt: Instant?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Instant?) = apply { this.updatedAt = updatedAt }
        fun lastLogin(lastLogin: Instant?) = apply { this.lastLogin = lastLogin }
        fun applicationName(applicationName: String?) = apply { this.applicationName = applicationName }
        fun email(email: String?) = apply { this.email = email }
        fun emailConfirmed(emailConfirmed: Boolean?) = apply { this.emailConfirmed = emailConfirmed }
        fun passwordHash(passwordHash: String?) = apply { this.passwordHash = passwordHash }
        fun build(): AuthUser = AuthUser(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthUser

        if (id != other.id) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (lastLogin != other.lastLogin) return false
        if (applicationName != other.applicationName) return false
        if (email != other.email) return false
        if (emailConfirmed != other.emailConfirmed) return false
        if (passwordHash != other.passwordHash) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + (lastLogin?.hashCode() ?: 0)
        result = 31 * result + applicationName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + emailConfirmed.hashCode()
        result = 31 * result + passwordHash.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthUser(id=$id, applicationName='$applicationName', email='$email')"
    }
}