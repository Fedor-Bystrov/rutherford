package app.rutherford.core.exception

import app.rutherford.core.ErrorCode

abstract class RutherfordException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

    abstract fun errorCode(): ErrorCode
}
