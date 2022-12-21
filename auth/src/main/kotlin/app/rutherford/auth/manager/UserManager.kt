package app.rutherford.auth.manager

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.core.ApplicationName
import app.rutherford.core.util.Checks.validateNotBlank

class UserManager {
    fun create(
        email: String,
        applicationName: ApplicationName,
        password: String
    ): AuthUser {
        validateNotBlank("email", email)
        validateNotBlank("password", password)

        // TODO
        //  - validate password with https://www.passay.org
        //  - validate user given provided email and applicationName combination doesn't exist
        //    - check that necessary index exist in the db
        //  - generate password hash (use the same algo as in .net)
        //  - store user in the database
        //  - return created user

        return authUser()
            .build()
    }
}
