package app.rutherford.database.repository

import app.rutherford.database.schema.tables.daos.AuthUserDao
import app.rutherford.database.schema.tables.pojos.AuthUser
import org.jooq.DSLContext

class AuthUserRepository(private val dslContext: DSLContext) {
    fun findAll(): List<AuthUser> {
        return AuthUserDao(dslContext.configuration()).findAll()
    }
}