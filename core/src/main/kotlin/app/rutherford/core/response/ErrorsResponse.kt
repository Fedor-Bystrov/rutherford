package app.rutherford.core.response

import app.rutherford.core.ErrorCode

data class ErrorResponse(
    val code: ErrorCode? = null,
    val errors: Collection<String>? = null
)
