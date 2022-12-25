package app.rutherford.auth.resource

import app.rutherford.auth.exception.PasswordPolicyValidationException
import app.rutherford.auth.exception.UserAlreadyExistException
import app.rutherford.auth.manager.UserManager
import app.rutherford.auth.resource.request.SignUpRequest
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.abstract.resource.Resource
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
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

    private fun signUp(): (Context) -> Unit = {
        val request = it.bodyAsClass(SignUpRequest::class.java)
        try {
            userManager.create(
                email = request.email,
                applicationName = TEST1,
                password = request.password1
            )
            it.status(CREATED)
        } catch (e: PasswordPolicyValidationException) {
            // TODO return bad request with message
        } catch (e: UserAlreadyExistException) {
            // TODO return bad request with message
        }
    }
}
