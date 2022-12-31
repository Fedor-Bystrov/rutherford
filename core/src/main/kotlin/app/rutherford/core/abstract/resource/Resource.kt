package app.rutherford.core.abstract.resource

import app.rutherford.core.ErrorCode
import app.rutherford.core.response.ErrorResponse
import io.javalin.http.Context
import io.javalin.http.HttpStatus

abstract class Resource {
    abstract fun bindRoutes()

    protected fun errorResponse(
        ctx: Context,
        httpStatus: HttpStatus,
        errorCode: ErrorCode? = null,
        errors: Collection<String>? = null
    ) {
        ctx.status(httpStatus)
        ctx.json(
            ErrorResponse(
                code = errorCode,
                errors = errors
            )
        )
    }
}
