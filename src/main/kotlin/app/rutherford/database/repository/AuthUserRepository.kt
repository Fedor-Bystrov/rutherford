package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUser
import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.jooq.generated.tables.daos.AuthUserDao
import app.rutherford.database.jooq.generated.tables.pojos.JAuthUser
import app.rutherford.database.jooq.generated.tables.records.AuthUserRecord
import org.jooq.Configuration
import org.jooq.DSLContext

class AuthUserRepository(private val dslContext: DSLContext) {

    // TODO use records instead of dao?

    fun findAll(configuration: Configuration? = null): List<AuthUser> = from(
        // TODO put DAO with default (non-tx) config in field
        AuthUserDao(configuration ?: dslContext.configuration()).findAll()
    )

    fun insert(configuration: Configuration, authUser: AuthUser) {
        AuthUserDao(configuration).insert(to(authUser))
    }

    // TODO extract below methods to parent (GenericJooqRepository) class

    private fun from(pojos: List<JAuthUser>): List<AuthUser> {
        return pojos.map { from(it) }
    }

    private fun from(pojo: JAuthUser): AuthUser = authUser()
        .id(pojo.id)
        .createdAt(pojo.createdAt)
        .updatedAt(pojo.updatedAt)
        .lastLogin(pojo.lastLogin)
        .applicationName(pojo.applicationName)
        .email(pojo.email)
        .emailConfirmed(pojo.emailConfirmed)
        .passwordHash(pojo.passwordHash)
        .build()

    private fun to(entity: AuthUser): JAuthUser = JAuthUser(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        lastLogin = entity.lastLogin,
        applicationName = entity.applicationName,
        email = entity.email,
        emailConfirmed = entity.emailConfirmed,
        passwordHash = entity.passwordHash,
    )

    private fun fromRecord(record: AuthUserRecord): AuthUser = authUser()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .lastLogin(record.lastLogin)
        .applicationName(record.applicationName!!)
        .email(record.email!!)
        .emailConfirmed(record.emailConfirmed!!)
        .passwordHash(record.passwordHash!!)
        .build()

    private fun toRecord(entity: AuthUser): AuthUserRecord = AuthUserRecord(
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