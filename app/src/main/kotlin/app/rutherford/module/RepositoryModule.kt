package app.rutherford.module

import app.rutherford.database.repository.AuthUserRepository
import app.rutherford.database.repository.AuthUserTokenRepository
import org.jooq.DSLContext

class RepositoryModule(dslContext: DSLContext) {
    val authUserRepository: AuthUserRepository
    val authUserTokenRepository: AuthUserTokenRepository

    init {
        authUserRepository = AuthUserRepository(dslContext)
        authUserTokenRepository = AuthUserTokenRepository(dslContext)
    }
}