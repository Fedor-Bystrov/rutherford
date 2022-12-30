package app.rutherford.auth.resource

import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.resource.request.SignUpRequest
import app.rutherford.core.ApplicationName
import app.rutherford.core.abstract.resource.Resource
import app.rutherford.core.exception.UnknownOriginException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.HttpStatus.BAD_REQUEST
import io.javalin.http.HttpStatus.CREATED
import org.eclipse.jetty.http.HttpHeader.ORIGIN
import java.net.MalformedURLException
import java.net.URL

class AuthResource(
    private val javalin: Javalin,
    private val userManager: UserManager
) : Resource() {
    override fun bindRoutes() {
        javalin.routes {
            path("/api/auth") {
                post("/sign-up", signUp())
            }
        }
    }

    // TODO Create E2E tests on /api/auth/sign-up

    private fun signUp(): (Context) -> Unit = {
        val request = it.bodyAsClass(SignUpRequest::class.java) // TODO write tests on request validation
        val applicationName = getApplicationName(it)
        try {
            userManager.create(
                email = request.email,
                password = request.password1,
                applicationName = applicationName,
            )
            it.status(CREATED)
        } catch (e: UserAlreadyExistException) {
            errorResponse(it, BAD_REQUEST, e.errorCode())
        } catch (e: PasswordPolicyValidationException) {
            errorResponse(it, BAD_REQUEST, e.errorCode(), e.errors)
        }
    }

    // TODO write test on ApplicationName#getForOrigin
    private fun getApplicationName(ctx: Context): ApplicationName {
        val origin = ctx.header(ORIGIN.asString()) ?: throw UnknownOriginException()
        try {
            return ApplicationName.getForOrigin(URI.create(origin))
        } catch (e: IllegalArgumentException) {
            throw UnknownOriginException(origin) // TODO add test
        }
    }
}
