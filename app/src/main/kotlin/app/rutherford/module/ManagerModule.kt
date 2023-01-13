package app.rutherford.module

import app.rutherford.auth.manager.SignInManager
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PasswordPolicyValidator
import app.rutherford.configuration.SecretsConfig
import java.security.SecureRandom

class ManagerModule(repositoryModule: RepositoryModule, secretsConfig: SecretsConfig) {
    val userManager: UserManager
    val signInManager: SignInManager

    init {
        val passwordPolicyValidator = PasswordPolicyValidator()
        val secureRandom = SecureRandom()

        userManager = UserManager(
            Argon2PasswordHasher(
                secretsConfig.authUserSecret,
                secureRandom
            ),
            passwordPolicyValidator,
            repositoryModule.authUserRepository
        )
        signInManager = SignInManager(
            Argon2PasswordHasher(
                secretsConfig.authUserTokenSecret,
                secureRandom
            ),
            secureRandom,
            repositoryModule.authUserRepository,
            repositoryModule.authUserTokenRepository,
            userManager,
        )
    }
}
