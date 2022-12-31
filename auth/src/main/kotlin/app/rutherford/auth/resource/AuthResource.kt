package app.rutherford.auth.resource

import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.resource.request.SignUpRequest
import app.rutherford.core.ApplicationName
import app.rutherford.core.abstract.resource.Resource
import app.rutherford.core.exception.UnknownOriginException
import app.rutherford.core.util.Checks.isValidEmail
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.HttpStatus.BAD_REQUEST
import io.javalin.http.HttpStatus.CREATED
import io.javalin.http.bodyValidator
import org.eclipse.jetty.http.HttpHeader.ORIGIN
import java.net.URI

class AuthResource(
    private val javalin: Javalin,
    private val userManager: UserManager
) : Resource() {
    override fun bindRoutes() {
        javalin.routes {
            path("/api/auth") {
                post("/sign-up", this::signUp)
            }
        }
    }

    private fun signUp(ctx: Context) {
        val request = ctx.bodyValidator<SignUpRequest>()
            .check({ isValidEmail(it.email) }, "MALFORMED_EMAIL")
            .check({ it.password1.isNotBlank() }, "NULL_OR_BLANK_PARAM: password1")
            .check({ it.password2.isNotBlank() }, "NULL_OR_BLANK_PARAM: password2")
            .check({ it.password1 == it.password2 }, "PASSWORDS_MISMATCH")
            .get()

        val applicationName = getApplicationName(ctx)
        try {
            userManager.create(
                email = request.email,
                password = request.password1,
                applicationName = applicationName,
            )
            ctx.status(CREATED)
        } catch (e: UserAlreadyExistException) {
            errorResponse(ctx, BAD_REQUEST, e.errorCode())
        } catch (e: PasswordPolicyValidationException) {
            errorResponse(ctx, BAD_REQUEST, e.errorCode(), e.errors)
        }
    }

    private fun getApplicationName(ctx: Context): ApplicationName {
        val origin = ctx.header(ORIGIN.asString()) ?: throw UnknownOriginException()
        try {
            return ApplicationName.getForOrigin(URI.create(origin))
        } catch (e: IllegalArgumentException) {
            throw UnknownOriginException(origin) // TODO add test (both url with \ and without it should work)
        }
    }
}
