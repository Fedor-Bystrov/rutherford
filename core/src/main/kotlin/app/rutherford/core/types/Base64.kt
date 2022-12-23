package app.rutherford.core.types

import java.util.Base64 as JavaBase64

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Base64

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
