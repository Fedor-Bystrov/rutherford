package app.rutherford.core.types

import java.util.Base64 as JavaBase64

// TODO add tests
class Base64 private constructor(private val bytes: ByteArray) {
    companion object {
        fun base64(value: String): Base64 {
            val bytes = JavaBase64.getDecoder().decode(value)
            return Base64(bytes)
        }

        fun base64(value: ByteArray): Base64 {
            val bytes = JavaBase64.getDecoder().decode(value)
            return Base64(bytes)
        }
    }

    override fun toString(): String = JavaBase64.getEncoder().encodeToString(bytes)
}
