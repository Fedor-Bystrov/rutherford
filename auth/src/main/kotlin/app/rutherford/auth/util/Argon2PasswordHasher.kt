package app.rutherford.auth.util

import app.rutherford.core.types.Base64
import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters
import org.bouncycastle.crypto.params.Argon2Parameters.ARGON2_id
import java.security.SecureRandom

private const val SALT_SIZE_BYTES = 16

// Argon parameters
private const val ARGON_MEMORY_KIB = 15_000 // 15 mb
private const val ARGON_ITERATIONS = 10
private const val ARGON_PARALLELISM = 1
private const val HASH_SIZE_BYTES = 32

data class HashingResult(val salt: Base64, val hash: Base64) {
    override fun toString(): String = "HashingResult(<masked_data>)"
}

class Argon2PasswordHasher(encodedSecret: Base64) { // TODO test
    private val secureRandom = SecureRandom()

    // TODO keep encodedSecret in keystore and extract on each run?
    private val secret = encodedSecret.decodeBytes()


    fun hash(password: String): HashingResult {
        val salt = nextSalt()

        val argonGenerator = Argon2BytesGenerator()
        val argon2Parameters = Argon2Parameters
            // Recommended min by OWASP
            .Builder(ARGON2_id)
            .withMemoryAsKB(ARGON_MEMORY_KIB) // 15 MB
            .withIterations(ARGON_ITERATIONS)
            .withParallelism(ARGON_PARALLELISM)
            .withSalt(salt)
            .withSecret(secret)
            .build()


        val hash = ByteArray(HASH_SIZE_BYTES)
        argonGenerator.init(argon2Parameters)
        argonGenerator.generateBytes(password.toCharArray(), hash)

        return HashingResult(
            salt = Base64.encode(salt),
            hash = Base64.encode(hash)
        )
    }

    private fun nextSalt(): ByteArray {
        val salt = ByteArray(SALT_SIZE_BYTES)
        secureRandom.nextBytes(salt)
        return salt
    }
}
