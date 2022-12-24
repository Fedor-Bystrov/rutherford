package app.rutherford.auth.entity

import app.rutherford.auth.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.core.abstract.entity.Entity
import app.rutherford.core.abstract.entity.Entity.State.CREATED
import app.rutherford.core.types.Base64
import app.rutherford.core.util.Checks.validateNotNull
import java.time.Instant
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

class AuthUserToken private constructor(builder: Builder) : Entity() {
    val id: UUID
    val createdAt: Instant
    val updatedAt: Instant
    val expiration: Instant?
    val state: State
    val salt: Base64
    val tokenHash: Base64
    val userId: Id<AuthUser>

    init {
        id = validateNotNull("id", builder.id)
        createdAt = validateNotNull("createdAt", builder.createdAt)
        updatedAt = validateNotNull("updatedAt", builder.updatedAt)
        expiration = builder.expiration
        state = validateNotNull("state", builder.state)
        salt = validateNotNull("salt", builder.salt)
        tokenHash = validateNotNull("tokenHash", builder.tokenHash)
        userId = validateNotNull("userId", builder.userId)
    }

    override fun id(): Id<AuthUserToken> = Id(this.id)

    override fun copy(): Builder = authUserToken()
        .id(this.id)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .expiration(this.expiration)
        .state(this.state)
        .salt(this.salt)
        .tokenHash(this.tokenHash)
        .userId(this.userId)

    fun withTokenHash(tokenHash: Base64): AuthUserToken = copy()
        .updatedAt(now())
        .tokenHash(tokenHash)
        .build()

    class Builder : EntityBuilder() {
        internal var id: UUID? = null
        internal var createdAt: Instant? = null
        internal var updatedAt: Instant? = null
        internal var expiration: Instant? = null
        internal var state: State? = CREATED
        internal var salt: Base64? = null
        internal var tokenHash: Base64? = null
        internal var userId: Id<AuthUser>? = null

        companion object {
            fun authUserToken(): Builder {
                val now = now()
                return Builder()
                    .id(randomUUID())
                    .createdAt(now)
                    .updatedAt(now)
            }
        }

        fun id(id: UUID?) = apply { this.id = id }
        fun id(id: Id<AuthUserToken>?) = apply { this.id = id?.value }
        fun createdAt(createdAt: Instant?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Instant?) = apply { this.updatedAt = updatedAt }
        fun expiration(expiration: Instant?) = apply { this.expiration = expiration }
        fun state(state: State?) = apply { this.state = state }
        fun salt(salt: Base64?) = apply { this.salt = salt }
        fun tokenHash(tokenHash: Base64?) = apply { this.tokenHash = tokenHash }
        fun userId(userId: Id<AuthUser>?) = apply { this.userId = userId }
        fun build(): AuthUserToken = AuthUserToken(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthUserToken

        if (id != other.id) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (expiration != other.expiration) return false
        if (state != other.state) return false
        if (salt != other.salt) return false
        if (tokenHash != other.tokenHash) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + (expiration?.hashCode() ?: 0)
        result = 31 * result + state.hashCode()
        result = 31 * result + salt.hashCode()
        result = 31 * result + tokenHash.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthUserToken(id=$id)"
    }
}
