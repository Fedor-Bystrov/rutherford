package app.rutherford.auth.manager

import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.core.ApplicationName

class SignInManager(
    private val userRepository: AuthUserRepository,
    private val userManager: UserManager,
) {
    fun signIn(
        email: String,
        password: String,
        appNameFromRequest: ApplicationName,
    ) {
        val user = userRepository
            .findBy(email = email, application = appNameFromRequest)
            ?: throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception

        val passwordValid = userManager.isPasswordCorrect(user, password)
        if (!passwordValid) {
            throw RuntimeException() // TODO throw NO_USER_WITH_SUCH_COMBINATION exception
        }

        // TODO impl session-based authentication
    }
}
