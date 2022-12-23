package app.rutherford.auth.util

import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters
import java.security.SecureRandom

class Argon2PasswordHasher {
    fun hash() {
        val secureRandom = SecureRandom()
        val salt = ByteArray(16)
        val key = ByteArray(32)

        secureRandom.nextBytes(salt)
        secureRandom.nextBytes(key)
        // TODO print key and salt

        val generator = Argon2BytesGenerator()

        generator.init(
            Argon2Parameters
                .Builder(Argon2Parameters.ARGON2_id)
//                .withSecret()
                // TODO
                .build()
        )

//        generator.generateBytes()
    }
}
