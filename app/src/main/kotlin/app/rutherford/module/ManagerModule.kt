package app.rutherford.module

import app.rutherford.auth.manager.JwtManager
import app.rutherford.auth.manager.SignInManager
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.util.Argon2Digest
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
            Argon2Digest(
                secretsConfig.authUserSecret,
                secureRandom
            ),
            passwordPolicyValidator,
            repositoryModule.authUserRepository
        )
        signInManager = SignInManager(
            Argon2Digest(
                secretsConfig.authUserTokenSecret,
                secureRandom
            ),
            secureRandom,
            repositoryModule.authUserRepository,
            repositoryModule.authUserTokenRepository,
            userManager,
            JwtManager()
        )
    }
}
