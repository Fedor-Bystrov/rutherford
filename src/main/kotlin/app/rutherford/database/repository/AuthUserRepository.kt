package app.rutherford.database.repository

import app.rutherford.database.entity.AuthUser
import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.jooq.generated.tables.daos.AuthUserDao
import app.rutherford.database.jooq.generated.tables.pojos.JAuthUser
import app.rutherford.database.jooq.generated.tables.records.AuthUserRecord
import app.rutherford.database.jooq.generated.tables.references.AUTH_USER
import org.jooq.Condition
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SelectWhereStep
import org.jooq.impl.DSL.selectFrom
import java.util.*

class AuthUserRepository(private val defaultContext: DSLContext) {
    private val authUserDao: AuthUserDao = AuthUserDao(defaultContext.configuration())

    // TODO use records instead of dao?

    fun findAll(): List<AuthUser> = from(authUserDao.findAll())

    fun insert(configuration: Configuration, authUser: AuthUser) {
        AuthUserDao(configuration).insert(to(authUser))
    }

    fun find(id: UUID): AuthUser? {
        val result = defaultContext.dsl()
            .selectFrom(AUTH_USER)
            .where(byId(id))
            .fetch()
            .map { fromRecord(it) }

        if (result.size > 1) {
            throw RuntimeException("")
        }

        return if (result.isNotEmpty()) result[0] else null
    }

//    protected fun findOne(
//        configuration: Configuration,
//        id: UUID,
//        condition: Condition,
//    ): AuthUser? {
//        return findOneWhere(
//            configuration,
//            byId(id).and(condition),
//        )
//        { fromRecord(it) }
//    }

//    protected fun fromResult(result: Result<AuthUserRecord>): Collection<AuthUser> {
//        return result { fromRecord(it) }
//    }

    protected fun byId(id: UUID): Condition {
        return AUTH_USER.ID.eq(id)
    }

//    protected fun findOneWhere(
//        configuration: Configuration,
//        condition: Condition,
//        transform: (Result<AuthUserRecord>) -> Collection<AuthUser>
//    ): AuthUser? {
//        return findOne(
//            configuration
//                .dsl()
//                .selectFrom(AUTH_USER),
//            condition,
//            transform
//        )
//    }

//    protected fun findOne(
//        query: SelectWhereStep<AuthUserRecord>,
//        condition: Condition,
//        transform: (Result<AuthUserRecord>) -> Collection<AuthUser>
//    ): AuthUser? {
//        val models = query
//            .where(condition)
//            .fetchAny(transform)
//
//        if (models != null && models.size > 1) {
//            throw RuntimeException(
//                "Expected 1 record but got ${models.size}. " +
//                        "Check your condition $condition"
//            )
//        }
//
//        return models?.first()
//    }


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