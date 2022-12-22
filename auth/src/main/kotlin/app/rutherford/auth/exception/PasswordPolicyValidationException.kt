package app.rutherford.auth.exception

import org.json.JSONObject

class PasswordPolicyValidationException(private val errors: List<String>) : RuntimeException() {
    fun toJson(): JSONObject = JSONObject().put("errors", errors)
}
