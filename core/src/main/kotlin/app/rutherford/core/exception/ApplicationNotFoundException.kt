package app.rutherford.core.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.APPLICATION_NOT_FOUND
import java.net.URL

class ApplicationNotFoundException(originUrl: URL) : RutherfordException("No application registered for $originUrl") {
    override fun errorCode(): ErrorCode = APPLICATION_NOT_FOUND;
}
