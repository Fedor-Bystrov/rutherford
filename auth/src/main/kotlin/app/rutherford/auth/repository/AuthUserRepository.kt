package app.rutherford.auth.repository

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.core.ApplicationName
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.repository.JooqRepository
import app.rutherford.core.types.Base64
import app.rutherford.schema.generated.tables.records.AuthUserRecord
import app.rutherford.schema.generated.tables.references.AUTH_USER
import org.jooq.Configuration
import org.jooq.DSLContext

class AuthUserRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserRecord, AuthUser>(
    defaultContext,
    AUTH_USER,
    AUTH_USER.ID
) {

    fun get(conf: Configuration? = null, id: Id<AuthUser>): AuthUser = getById(conf, id.value)
    fun find(conf: Configuration? = null, id: Id<AuthUser>): AuthUser? = findById(conf, id.value)
    fun find(conf: Configuration? = null, ids: Collection<Id<AuthUser>>): Collection<AuthUser> =
        findByIds(conf, ids.map { it.value })

    fun insert(conf: Configuration, entity: AuthUser): AuthUser = insertOne(conf, entity)
    fun insert(conf: Configuration, entities: Collection<AuthUser>) = insertBatch(conf, entities)
    fun update(conf: Configuration, entity: AuthUser): AuthUser = updateOne(conf, entity)

    fun findBy(conf: Configuration? = null, email: String, application: ApplicationName) =
        findOneWhere(
            conf,
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
        .passwordHash(Base64.of(record.passwordHash!!))
        .build()

    override fun toRecord(entity: AuthUser): AuthUserRecord = AuthUserRecord(
        id = entity.id().value,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        lastLogin = entity.lastLogin,
        applicationName = entity.applicationName,
        email = entity.email,
        emailConfirmed = entity.emailConfirmed,
        passwordHash = entity.passwordHash.bytes(),
    )
}
