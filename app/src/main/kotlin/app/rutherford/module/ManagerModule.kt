package app.rutherford.module

import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.core.types.Base64.Companion.base64
import java.security.SecureRandom

class ManagerModule(repositoryModule: RepositoryModule) {
    val userManager: UserManager

    init {
        val passwordPolicyValidator = PasswordPolicyValidator()
        val passwordHasher = Argon2PasswordHasher(
            base64(""), // TODO secret
            SecureRandom()
        )

        userManager = UserManager(
            passwordHasher,
            passwordPolicyValidator,
            repositoryModule.authUserRepository
        )
    }
}
