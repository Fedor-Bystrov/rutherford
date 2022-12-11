package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUser
import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.jooq.generated.tables.pojos.JAuthUser
import app.rutherford.database.jooq.generated.tables.records.AuthUserRecord
import org.jooq.DSLContext

class AuthUserRepository(private val dslContext: DSLContext) {
    fun findAll(): List<AuthUser> {
        return listOf()
    }

    fun fromPojo(pojo: JAuthUser): AuthUser = authUser()
        .id(pojo.id!!)
        .createdAt(pojo.createdAt!!)
        .updatedAt(pojo.updatedAt!!)
        .lastLogin(pojo.lastLogin)
        .applicationName(pojo.applicationName!!)
        .email(pojo.email!!)
        .emailConfirmed(pojo.emailConfirmed!!)
        .passwordHash(pojo.passwordHash!!)
        .build()

    fun toPojo(entity: AuthUser): JAuthUser = JAuthUser(
        id = entity.id,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        lastLogin = entity.lastLogin,
        applicationName = entity.applicationName,
        email = entity.email,
        emailConfirmed = entity.emailConfirmed,
        passwordHash = entity.passwordHash,
    )

    fun fromRecord(record: AuthUserRecord): AuthUser = authUser()
        .id(record.id!!)
        .createdAt(record.createdAt!!)
        .updatedAt(record.updatedAt!!)
        .lastLogin(record.lastLogin)
        .applicationName(record.applicationName!!)
        .email(record.email!!)
        .emailConfirmed(record.emailConfirmed!!)
        .passwordHash(record.passwordHash!!)
        .build()

    fun toRecord(entity: AuthUser): AuthUserRecord = AuthUserRecord(
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