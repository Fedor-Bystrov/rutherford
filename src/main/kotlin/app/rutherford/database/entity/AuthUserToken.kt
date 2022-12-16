package app.rutherford.database.entity

import app.rutherford.database.entity.AuthUserToken.Builder.Companion.authUserToken
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

    class Builder : EntityBuilder() {
        internal var id: UUID? = null;
        internal var createdAt: Instant? = null;
        internal var updatedAt: Instant? = null;
        internal var expiration: Instant? = null;
        internal var state: State? = null;
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
}