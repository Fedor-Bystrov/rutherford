package app.rutherford.auth.manager

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.core.ApplicationName
import app.rutherford.core.transaction.transaction
import app.rutherford.core.util.Checks.validateNotBlank

class UserManager(
    private val passwordHasher: Argon2PasswordHasher,
    private val passwordPolicyValidator: PasswordPolicyValidator,
    private val authUserRepository: AuthUserRepository,
) {

    /**
     * @throws IllegalStateException if email is blank
     * @throws app.rutherford.auth.exception.PasswordPolicyValidationException if user password is incorrect
     * @throws UserAlreadyExistException if email is already registered for applicationName
     */
    fun create(
        email: String,
        applicationName: ApplicationName,
        password: String
    ): AuthUser {
        validateNotBlank("email", email)
        passwordPolicyValidator.validate(password)

        authUserRepository
            .findBy(email = email, application = applicationName)
            ?.let { throw UserAlreadyExistException(email, applicationName) }

        val (salt, passwordHash) = passwordHasher.hash(password)

        return requireNotNull(
            transaction {
                authUserRepository.insert(
                    this, authUser()
                        .applicationName(applicationName)
                        .email(email)
                        .emailConfirmed(false)
                        .salt(salt)
                        .passwordHash(passwordHash)
                        .build()
                )
            }) { "Error creating AuthUser. Transaction returned empty result" }
    }
}
