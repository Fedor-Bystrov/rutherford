package app.rutherford.auth.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.PASSWORD_POLICY_VIOLATION
import app.rutherford.core.exception.RutherfordException

class PasswordPolicyValidationException(val errors: List<String>) : RutherfordException() {
    override fun errorCode(): ErrorCode = PASSWORD_POLICY_VIOLATION
}
