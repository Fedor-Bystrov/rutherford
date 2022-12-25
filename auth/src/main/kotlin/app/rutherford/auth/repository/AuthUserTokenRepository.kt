package app.rutherford.auth.repository

import app.rutherford.auth.entity.AuthUserToken
import app.rutherford.auth.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.repository.JooqRepository
import app.rutherford.core.transaction.TransactionContext
import app.rutherford.core.types.Base64.Companion.base64
import app.rutherford.schema.generated.tables.records.AuthUserTokenRecord
import app.rutherford.schema.generated.tables.references.AUTH_USER_TOKEN
import org.jooq.DSLContext

class AuthUserTokenRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserTokenRecord, AuthUserToken>(
    defaultContext,
    AUTH_USER_TOKEN,
    AUTH_USER_TOKEN.ID
) {
    // TODO impl find by user_id and token_hash
    //  - can't just use token_hash since two users can have the same password
    //  - or use user-defined salt to generate token_hash?
    // TODO fix auth_user_token index

    fun get(ctx: TransactionContext? = null, id: Id<AuthUserToken>): AuthUserToken =
        getById(ctx?.configuration, id.value)

    fun find(ctx: TransactionContext? = null, id: Id<AuthUserToken>): AuthUserToken? =
        findById(ctx?.configuration, id.value)

    fun insert(ctx: TransactionContext, entity: AuthUserToken): AuthUserToken =
        insertOne(ctx.configuration, entity)

    fun insert(ctx: TransactionContext, entities: Collection<AuthUserToken>) =
        insertBatch(ctx.configuration, entities)

    fun update(ctx: TransactionContext, entity: AuthUserToken): AuthUserToken =
        updateOne(ctx.configuration, entity)

    override fun fromRecord(record: AuthUserTokenRecord): AuthUserToken = authUserToken()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .expiration(record.expiration)
        .state(record.state!!)
        .salt(base64(record.salt!!))
        .tokenHash(base64(record.tokenHash!!))
        .userId(Id(record.userId!!))
        .build()

    override fun toRecord(entity: AuthUserToken): AuthUserTokenRecord = AuthUserTokenRecord(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        expiration = entity.expiration,
        state = entity.state,
        salt = entity.salt.bytes(),
        tokenHash = entity.tokenHash.bytes(),
        userId = entity.userId.value
    )
}
