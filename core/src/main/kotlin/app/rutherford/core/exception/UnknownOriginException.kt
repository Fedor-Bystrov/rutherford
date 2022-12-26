package app.rutherford.core.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.UNKNOWN_ORIGIN

class UnknownOriginException(originUrl: String?) :
    RutherfordException("No application registered for $originUrl") {
    override fun errorCode(): ErrorCode = UNKNOWN_ORIGIN
}
