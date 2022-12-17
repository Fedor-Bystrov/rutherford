package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUserToken
import app.rutherford.database.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.database.entity.Entity.Id
import app.rutherford.database.jooq.generated.tables.records.AuthUserTokenRecord
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER_TOKEN
import org.jooq.Configuration
import org.jooq.DSLContext

class AuthUserTokenRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserTokenRecord, AuthUserToken>(
    defaultContext,
    AUTH_USER_TOKEN,
    AUTH_USER_TOKEN.ID
) {

    // TODO what password hashing algo to use? Use the same as in .net (or similar)?

    // TODO impl find by user_id and token_hash
    //  - can't just use token_hash since two users can have the same password
    //  - or use user-defined salt to generate token_hash?
    // TODO fix auth_user_token index
    fun get(conf: Configuration? = null, id: Id<AuthUserToken>): AuthUserToken = getById(conf, id.value)
    fun find(conf: Configuration? = null, id: Id<AuthUserToken>): AuthUserToken? = findById(conf, id.value)
    fun insert(conf: Configuration, entity: AuthUserToken): AuthUserToken = insertOne(conf, entity)
    fun insert(conf: Configuration, entities: Collection<AuthUserToken>) = insertBatch(conf, entities)
    fun update(conf: Configuration, entity: AuthUserToken): AuthUserToken = updateOne(conf, entity)

    override fun fromRecord(record: AuthUserTokenRecord): AuthUserToken = authUserToken()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .expiration(record.expiration)
        .state(record.state!!)
        .tokenHash(record.tokenHash!!)
        .userId(Id(record.userId!!))
        .build()

    override fun toRecord(entity: AuthUserToken): AuthUserTokenRecord = AuthUserTokenRecord(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        expiration = entity.expiration,
        state = entity.state,
        tokenHash = entity.tokenHash,
        userId = entity.userId.value
    )
}