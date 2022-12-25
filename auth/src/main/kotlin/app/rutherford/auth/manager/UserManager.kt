package app.rutherford.auth.manager

import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.core.ApplicationName
import app.rutherford.core.transaction.transaction
import app.rutherford.core.util.Checks.validateNotBlank


// TODO
//  - Write tests on UserManager#create

class UserManager(
    private val passwordPolicyValidator: PasswordPolicyValidator,
    private val authUserRepository: AuthUserRepository,
    private val passwordHasher: Argon2PasswordHasher,
) {

    /**
     * @throws app.rutherford.auth.exception.PasswordPolicyValidationException if user password is incorrect
     * @throws UserAlreadyExistException if email is already registered for applicationName
     */
    fun create(
        email: String,
        applicationName: ApplicationName,
        password: String
    ) {
        validateNotBlank("email", email)
        passwordPolicyValidator.validate(password)

        authUserRepository
            .findBy(email = email, application = applicationName)
            ?.let { throw UserAlreadyExistException() }

        val (salt, passwordHash) = passwordHasher.hash(password)

        transaction {
            authUserRepository.insert(
                it, authUser()
                    .applicationName(applicationName)
                    .email(email)
                    .emailConfirmed(false)
                    .salt(salt)
                    .passwordHash(passwordHash)
                    .build()
            )
        }
    }
}
