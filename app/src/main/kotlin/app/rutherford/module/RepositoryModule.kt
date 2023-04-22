package app.rutherford.module

import app.rutherford.auth.repository.AuthUserRepository
import org.jooq.DSLContext

class RepositoryModule(dslContext: DSLContext) {
    val authUserRepository: AuthUserRepository

    init {
        authUserRepository = AuthUserRepository(dslContext)
    }
}