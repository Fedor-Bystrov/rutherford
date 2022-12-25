package app.rutherford.auth.resource

import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.resource.request.SignUpRequest
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.abstract.resource.Resource
import app.rutherford.core.response.ErrorResponse
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.HttpStatus.BAD_REQUEST
import io.javalin.http.HttpStatus.CREATED

class AuthResource(
    private val javalin: Javalin,
    private val userManager: UserManager
) : Resource {
    override fun bindRoutes() {
        javalin.routes {
            path("/api/auth") {
                post("/sign-up", signUp())
            }
        }
    }

    // TODO implement + write tests

    private fun signUp(): (Context) -> Unit = {
        val request = it.bodyAsClass(SignUpRequest::class.java)
        try {
            userManager.create(
                email = request.email,
                applicationName = TEST1,
                password = request.password1
            )
            it.status(CREATED)
        } catch (e: RuntimeException) {
            val message = when (e) {
                PasswordPolicyValidationException::class -> "TODO 1"
                UserAlreadyExistException::class -> "TODO 2"
                // TODO other exception?
                else -> throw e
            }
            it.status(BAD_REQUEST)
            it.json(
                ErrorResponse(
                    message = message,
                    httpStatus = BAD_REQUEST.code
                )
            )

        }
    }
}
