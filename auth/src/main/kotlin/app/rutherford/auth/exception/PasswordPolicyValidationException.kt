package app.rutherford.auth.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.PASSWORD_POLICY_VIOLATION
import app.rutherford.core.exception.RutherfordException
import org.json.JSONObject

class PasswordPolicyValidationException(private val errors: List<String>) : RutherfordException() {
    fun toJson(): JSONObject = JSONObject().put("errors", errors) // TODO delete
    override fun errorCode(): ErrorCode = PASSWORD_POLICY_VIOLATION
}
