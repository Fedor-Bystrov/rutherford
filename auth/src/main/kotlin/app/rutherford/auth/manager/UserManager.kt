package app.rutherford.auth.manager

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.core.ApplicationName
import app.rutherford.core.util.Checks.validateNotBlank


// TODO
//  1. Check (email, application_name) doesn't exist
//  2. Create PasswordHasher + write tests
//  3. Hash user's password
//  4. Create user and store it in the db
//  5. Return created user

class UserManager(
    private val passwordPolicyValidator: PasswordPolicyValidator,
    private val authUserRepository: AuthUserRepository,
) {

    /**
     * @throws app.rutherford.auth.exception.PasswordPolicyValidationException if user password is incorrect
     * @throws app.rutherford.auth.exception.UserAlreadyExistException if email is already registered for applicationName
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
            ?.let { throw UserAlreadyExistException() }


        return authUser()
            .build()
    }
}
