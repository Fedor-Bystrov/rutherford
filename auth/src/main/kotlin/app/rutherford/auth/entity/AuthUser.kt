package app.rutherford.auth.entity

import app.rutherford.core.ApplicationName
import app.rutherford.core.abstract.entity.Entity
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.core.util.Checks.validateNotBlank
import app.rutherford.core.util.Checks.validateNotNull
import java.time.Instant
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

class AuthUser private constructor(builder: Builder) : Entity() {
    val id: UUID
    val createdAt: Instant
    val updatedAt: Instant
    val lastLogin: Instant?
    val applicationName: ApplicationName
    val email: String
    val emailConfirmed: Boolean
    val passwordHash: String

    init {
        id = validateNotNull("id", builder.id)
        createdAt = validateNotNull("createdAt", builder.createdAt)
        updatedAt = validateNotNull("updatedAt", builder.updatedAt)
        lastLogin = builder.lastLogin
        applicationName = validateNotNull("applicationName", builder.applicationName)
        email = validateNotBlank("email", builder.email)
        emailConfirmed = validateNotNull("emailConfirmed", builder.emailConfirmed)
        passwordHash = validateNotBlank("passwordHash", builder.passwordHash)
    }

    override fun id(): Id<AuthUser> = Id(this.id)

    override fun copy(): Builder = authUser()
        .id(this.id)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .lastLogin(this.lastLogin)
        .applicationName(this.applicationName)
        .email(this.email)
        .emailConfirmed(this.emailConfirmed)
        .passwordHash(this.passwordHash)

    fun confirmEmail(): AuthUser = copy()
        .updatedAt(now())
        .emailConfirmed(true)
        .build()

    class Builder : EntityBuilder() {
        internal var id: UUID? = null
        internal var createdAt: Instant? = null
        internal var updatedAt: Instant? = null
        internal var lastLogin: Instant? = null
        internal var applicationName: ApplicationName? = null
        internal var email: String? = null
        internal var emailConfirmed: Boolean? = false
        internal var passwordHash: String? = null

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
        fun id(id: Id<AuthUser>?) = apply { this.id = id?.value}
        fun createdAt(createdAt: Instant?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Instant?) = apply { this.updatedAt = updatedAt }
        fun lastLogin(lastLogin: Instant?) = apply { this.lastLogin = lastLogin }
        fun applicationName(applicationName: ApplicationName?) = apply { this.applicationName = applicationName }
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
        return "AuthUser(id=$id)"
    }
}