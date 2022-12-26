package app.rutherford.auth.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.exception.RutherfordException

class UserAlreadyExistException : RutherfordException() {
    override fun errorCode(): ErrorCode = ErrorCode.USER_ALREADY_EXISTS
}
