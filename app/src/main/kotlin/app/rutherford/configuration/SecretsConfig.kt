package app.rutherford.configuration

import app.rutherford.core.types.Base64

data class SecretsConfig(
    val authUserSecret: Base64,
) {
    override fun toString(): String {
        return "SecretsConfig(<masked>)"
    }
}
