package app.rutherford.auth.util

import app.rutherford.core.types.Base64
import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters

// TODO write PBKDF2 password generator and compare in runtime

class Argon2PasswordHasher(private val salt: Base64, private val key: Base64) { // TODO test
    fun hash(password: String): Base64 {
        val argonGenerator = Argon2BytesGenerator()
        val argon2Parameters = Argon2Parameters
            // Recommended min by OWASP
            .Builder(Argon2Parameters.ARGON2_id)
            .withMemoryAsKB(37_000) // 37 MB
            .withIterations(1)
            .withParallelism(1)
            .withSalt(salt.decodeBytes())
            .withSecret(key.decodeBytes())
            .build()


        val hash = ByteArray(32)
        argonGenerator.init(argon2Parameters)
        argonGenerator.generateBytes(password.toCharArray(), hash)

        // TODO generate salt and return Tuple<salt, hash>
        // TODO salt must to be stored in auth_user
        return Base64.encode(hash)
    }
}

fun main() {
    val salt = Base64.of("afo5hZL7oBUPwm5PPGQwnw==") // 16 byte
    val key = Base64.of("1ZzA+CxCCCVSxnsOaYdC4ZKkTvvx/oYw4ON0DwbNZFM=") // 32 byte
    val hash = Argon2PasswordHasher(salt, key).hash("password")

    println("salt: $salt")
    println("key: $key")
    println("hash: $hash")
}



