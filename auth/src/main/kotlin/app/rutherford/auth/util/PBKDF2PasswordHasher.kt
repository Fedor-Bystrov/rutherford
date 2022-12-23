package app.rutherford.auth.util

import app.rutherford.core.types.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

const val PBKDF2_HMAC_SHA512: String = "PBKDF2WithHmacSHA512"

class PBKDF2PasswordHasher(private val salt: Base64) { // TODO test
    fun hash(password: String): Base64 {
        val keySpec = PBEKeySpec(password.toCharArray(), salt.decodeBytes(), 120_000, 512)
        val keyFactory = SecretKeyFactory.getInstance(PBKDF2_HMAC_SHA512)
        val hash = keyFactory.generateSecret(keySpec).encoded
        return Base64.encode(hash)
    }
}

fun main() { // TODO delete
    val salt = Base64.of("afo5hZL7oBUPwm5PPGQwnw==") // 16 byte
    val key = Base64.of("1ZzA+CxCCCVSxnsOaYdC4ZKkTvvx/oYw4ON0DwbNZFM=") // 32 byte
    val hash = PBKDF2PasswordHasher(salt).hash("password")

    println("salt: $salt")
    println("key: $key")
    println("hash: $hash")
}
