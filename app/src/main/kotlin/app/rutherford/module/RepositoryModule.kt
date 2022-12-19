package app.rutherford.module

import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.repository.AuthUserTokenRepository
import org.jooq.DSLContext

class RepositoryModule(dslContext: DSLContext) {
    val authUserRepository: AuthUserRepository
    val authUserTokenRepository: AuthUserTokenRepository

    init {
        authUserRepository = AuthUserRepository(dslContext)
        authUserTokenRepository = AuthUserTokenRepository(dslContext)
    }
}