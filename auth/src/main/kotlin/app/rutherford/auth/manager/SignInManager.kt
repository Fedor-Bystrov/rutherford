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
        val user = authUserRepository
            .findBy(email = email, application = appNameFromRequest)
            ?: throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception

        val passwordValid = userManager.isPasswordCorrect(user, password)
        if (!passwordValid) {
            throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception
        }

        // TODO generate refresh token, persist it
        // TODO generate jwt (access) token
    }
}
