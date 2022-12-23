package app.rutherford.core.types

import java.util.Base64 as JavaBase64

class Base64 private constructor(decodedBytes: ByteArray) {
    private val encodedValue: String

    init {
        encodedValue = JavaBase64.getEncoder().encodeToString(decodedBytes)
    }

    companion object {
        fun decode(value: String): Base64 {
            val bytes = JavaBase64.getDecoder().decode(value)
            return Base64(bytes)
        }

        fun decode(value: ByteArray): Base64 {
            val bytes = JavaBase64.getDecoder().decode(value)
            return Base64(bytes)
        }

        fun encode(value: ByteArray): Base64 {
            return Base64(value)
        }
    }

    override fun toString(): String = encodedValue
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Base64

        if (encodedValue != other.encodedValue) return false

        return true
    }

    override fun hashCode(): Int {
        return encodedValue.hashCode()
    }


}
