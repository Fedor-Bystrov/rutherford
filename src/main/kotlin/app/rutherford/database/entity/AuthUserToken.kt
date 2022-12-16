package app.rutherford.database.entity

import app.rutherford.database.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.database.entity.Entity.State.CREATED
import app.rutherford.util.Checks.validateNotBlank
import app.rutherford.util.Checks.validateNotNull
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
    val tokenHash: String
    val userId: UUID

    init {
        id = validateNotNull("id", builder.id)
        createdAt = validateNotNull("createdAt", builder.createdAt)
        updatedAt = validateNotNull("updatedAt", builder.updatedAt)
        expiration = builder.expiration
        state = validateNotNull("state", builder.state)
        tokenHash = validateNotBlank("tokenHash", builder.tokenHash)
        userId = validateNotNull("userId", builder.userId)
    }

    override fun copy(): Builder = authUserToken()
        .id(this.id)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .expiration(this.expiration)
        .state(this.state)
        .tokenHash(this.tokenHash)
        .userId(this.userId)

    fun withTokenHash(tokenHash: String): AuthUserToken = copy()
        .updatedAt(now())
        .tokenHash(tokenHash)
        .build()

    class Builder : EntityBuilder() {
        internal var id: UUID? = null;
        internal var createdAt: Instant? = null;
        internal var updatedAt: Instant? = null;
        internal var expiration: Instant? = null;
        internal var state: State? = CREATED;
        internal var tokenHash: String? = null;
        internal var userId: UUID? = null;

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
        fun createdAt(createdAt: Instant?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Instant?) = apply { this.updatedAt = updatedAt }
        fun expiration(expiration: Instant?) = apply { this.expiration = expiration }
        fun state(state: State?) = apply { this.state = state }
        fun tokenHash(tokenHash: String?) = apply { this.tokenHash = tokenHash }
        fun userId(userId: UUID?) = apply { this.userId = userId }
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
        result = 31 * result + tokenHash.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthUserToken(id=$id)"
    }
}