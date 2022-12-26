package app.rutherford.core.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.APPLICATION_NOT_FOUND

class ApplicationNotFoundException(originUrl: String?) :
    RutherfordException("No application registered for $originUrl") {
    override fun errorCode(): ErrorCode = APPLICATION_NOT_FOUND;
}
