package app.rutherford.core.types

import java.util.Base64 as JavaBase64

class Base64 private constructor(rawBytes: ByteArray) {
    private val encodedBytes: ByteArray

    init {
        encodedBytes = JavaBase64.getEncoder().encode(rawBytes)
    }

    companion object {
        /**
         * Creates a new Base64 object from base64-encoded string
         * @param base64 base64-encoded string
         */
        fun base64(base64: String): Base64 {
            val bytes = JavaBase64.getDecoder().decode(base64)
            return Base64(bytes)
        }

        /**
         * Creates a new Base64 object from base64 encoded byte array
         * @param base64 base64-encoded byte array
         */
        fun base64(base64: ByteArray): Base64 {
            val bytes = JavaBase64.getDecoder().decode(base64)
            return Base64(bytes)
        }

        /**
         * Encodes a new Base64 object given a byte array
         * @param value byte array
         */
        fun encode(value: ByteArray): Base64 {
            return Base64(value)
        }
    }

    fun bytes(): ByteArray = encodedBytes
    fun decodeBytes(): ByteArray = JavaBase64.getDecoder().decode(encodedBytes)

    override fun toString(): String = String(encodedBytes)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Base64

        if (!encodedBytes.contentEquals(other.encodedBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return encodedBytes.contentHashCode()
    }
}
