package app.rutherford.auth.repository

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.core.ApplicationName
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.repository.JooqRepository
import app.rutherford.core.transaction.TransactionContext
import app.rutherford.core.types.Base64.Companion.base64
import app.rutherford.schema.generated.tables.records.AuthUserRecord
import app.rutherford.schema.generated.tables.references.AUTH_USER
import org.jooq.DSLContext

class AuthUserRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserRecord, AuthUser>(
    defaultContext,
    AUTH_USER,
    AUTH_USER.ID
) {

    fun get(ctx: TransactionContext? = null, id: Id<AuthUser>): AuthUser = getById(ctx?.configuration, id.value)
    fun find(ctx: TransactionContext? = null, id: Id<AuthUser>): AuthUser? = findById(ctx?.configuration, id.value)
    fun find(ctx: TransactionContext? = null, ids: Collection<Id<AuthUser>>): Collection<AuthUser> =
        findByIds(ctx?.configuration, ids.map { it.value })

    fun insert(ctx: TransactionContext, entity: AuthUser): AuthUser = insertOne(ctx.configuration, entity)
    fun insert(ctx: TransactionContext, entities: Collection<AuthUser>) = insertBatch(ctx.configuration, entities)
    fun update(ctx: TransactionContext, entity: AuthUser): AuthUser = updateOne(ctx.configuration, entity)

    fun findBy(ctx: TransactionContext? = null, email: String, application: ApplicationName) =
        findOneWhere(
            ctx?.configuration,
            AUTH_USER.EMAIL.eq(email)
                .and(
                    AUTH_USER.APPLICATION_NAME.eq(application)
                )
        )

    override fun fromRecord(record: AuthUserRecord): AuthUser = authUser()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .lastLogin(record.lastLogin)
        .applicationName(record.applicationName!!)
        .email(record.email!!)
        .emailConfirmed(record.emailConfirmed!!)
        .salt(base64(record.salt!!))
        .passwordHash(base64(record.passwordHash!!))
        .build()

    override fun toRecord(entity: AuthUser): AuthUserRecord = AuthUserRecord(
        id = entity.id().value,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        lastLogin = entity.lastLogin,
        applicationName = entity.applicationName,
        email = entity.email,
        emailConfirmed = entity.emailConfirmed,
        salt = entity.salt.bytes(),
        passwordHash = entity.passwordHash.bytes(),
    )
}
