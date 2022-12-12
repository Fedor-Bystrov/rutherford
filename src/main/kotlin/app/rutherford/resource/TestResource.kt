package app.rutherford.resource

import app.rutherford.database.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.database.repository.AuthUserRepository
import app.rutherford.database.transaction.transaction
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context


class TestResource(
    private val javalin: Javalin,
    private val authUserRepository: AuthUserRepository
) : Resource {
    override fun bindRoutes() {
        javalin.routes {
            path("/test") {
                get(allUsers())
                post(createUser())
            }
        }
    }

    private fun allUsers(): (Context) -> Unit {
        return { context ->
            val users = authUserRepository.findAll()
            context.json(users)
        }
    }

    private fun createUser(): (Context) -> Unit {
        return {
            transaction { tx ->
                authUserRepository.insert(
                    tx, authUser()
                        .applicationName("applicationName")
                        .email("pojo.email")
                        .passwordHash("pojo.passwordHash")
                        .build()
                )
            }
        }
    }
}