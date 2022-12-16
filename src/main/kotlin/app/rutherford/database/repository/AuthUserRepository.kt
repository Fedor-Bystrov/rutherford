package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUser
import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.jooq.generated.tables.records.AuthUserRecord
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER
import org.jooq.Configuration
import org.jooq.DSLContext
import java.util.*

class AuthUserRepository(
    defaultContext: DSLContext
) : JooqRepository<AuthUserRecord, AuthUser>(
    defaultContext,
    AUTH_USER,
    AUTH_USER.ID
) {
    fun get(conf: Configuration?, id: UUID): AuthUser = getById(conf, id)
    fun find(conf: Configuration?, id: UUID): AuthUser? = findById(conf, id)
    fun find(conf: Configuration?, ids: Collection<UUID>): Collection<AuthUser> = findByIds(conf, ids)
    fun insert(conf: Configuration, entity: AuthUser): AuthUser = insertOne(conf, entity)
    fun insert(conf: Configuration, entities: Collection<AuthUser>) = insertBatch(conf, entities)
    fun update(conf: Configuration, entity: AuthUser): AuthUser = updateOne(conf, entity)
    fun delete(conf: Configuration, id: UUID): Unit = deleteById(conf, id)
    fun delete(conf: Configuration, entities: Collection<AuthUser>) = deleteBatch(conf, entities)

    override fun fromRecord(record: AuthUserRecord): AuthUser = authUser()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .lastLogin(record.lastLogin)
        .applicationName(record.applicationName!!)
        .email(record.email!!)
        .emailConfirmed(record.emailConfirmed!!)
        .passwordHash(record.passwordHash!!)
        .build()

    override fun toRecord(entity: AuthUser): AuthUserRecord = AuthUserRecord(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        lastLogin = entity.lastLogin,
        applicationName = entity.applicationName,
        email = entity.email,
        emailConfirmed = entity.emailConfirmed,
        passwordHash = entity.passwordHash,
    )
}