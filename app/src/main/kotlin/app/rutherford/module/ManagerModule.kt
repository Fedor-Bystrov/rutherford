package app.rutherford.module

import app.rutherford.auth.manager.SignInManager
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.core.types.Base64
import java.security.SecureRandom

class ManagerModule(repositoryModule: RepositoryModule, authUserSecret: Base64) {
    val userManager: UserManager
    val signInManager: SignInManager

    init {
        val passwordPolicyValidator = PasswordPolicyValidator()
        val passwordHasher = Argon2PasswordHasher(
            authUserSecret,
            SecureRandom()
        )

        userManager = UserManager(
            passwordHasher,
            passwordPolicyValidator,
            repositoryModule.authUserRepository
        )
        signInManager = SignInManager(
            repositoryModule.authUserRepository,
            userManager,
        )
    }
}
