package app.rutherford.auth.manager

import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.core.ApplicationName

class SignInManager(
    val authUserRepository: AuthUserRepository,
    val userManager: UserManager,
) {
    fun signIn(
        email: String,
        password: String,
        appNameFromRequest: ApplicationName,
    ) {
        // TODO
        //  1. get user, throw NO_USER_WITH_SUCH_COMBINATION if not exist
        //  2. validate password, throw NO_USER_WITH_SUCH_COMBINATION if not exist
        //  3. generate refresh token
        //  4. generate access token

        val user = authUserRepository
            .findBy(email = email, application = appNameFromRequest)
            ?: throw RuntimeException() // TODO throw proper exception

        val passwordValid = userManager.isPasswordCorrect(user, password)
        if (!passwordValid) {
            throw RuntimeException() // TODO throw proper exception
        }
    }
}
