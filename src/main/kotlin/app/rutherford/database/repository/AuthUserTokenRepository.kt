package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUserToken
import app.rutherford.database.entity.AuthUserToken.Builder.Companion.authUserToken
import app.rutherford.database.jooq.generated.tables.records.AuthUserTokenRecord
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER_TOKEN
import org.jooq.DSLContext

class AuthUserTokenRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserTokenRecord, AuthUserToken>(
    defaultContext,
    AUTH_USER_TOKEN,
    AUTH_USER_TOKEN.ID
) {

    override fun fromRecord(record: AuthUserTokenRecord): AuthUserToken = authUserToken()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .expiration(record.expiration)
        .state(record.state!!)
        .tokenHash(record.tokenHash!!)
        .userId(record.userId!!)
        .build()

    override fun toRecord(entity: AuthUserToken): AuthUserTokenRecord = AuthUserTokenRecord(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        expiration = entity.expiration,
        state = entity.state,
        tokenHash = entity.tokenHash,
        userId = entity.userId
    )
}