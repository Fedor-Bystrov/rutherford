package app.rutherford.resource

import app.rutherford.database.repository.AuthUserRepository
import app.rutherford.database.transaction.transaction
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.HttpStatus.NO_CONTENT
import java.util.*

class TestResource(
    private val javalin: Javalin,
    private val authUserRepository: AuthUserRepository
) : Resource {
    override fun bindRoutes() {
        javalin.routes {
            path("/test/users") {
                get("/all", all())
                get("/{id}", one())
                post(createUser())
            }
        }
    }

    private fun all(): (Context) -> Unit = {
        it.json(
            authUserRepository.find(
                listOf(
                    UUID.fromString("60d327e7-c936-4ec9-99a0-9903de82fe59"),
                    UUID.fromString("e09ce7ca-c30e-4735-bb02-1efa413bdada"),
                    UUID.fromString("38cf9d9c-6f00-4fff-9092-82a325e442eb")
                )
            )
        )
    }


    private fun one(): (Context) -> Unit = {
        val userId = it.pathParamAsClass("id", UUID::class.java).get()
        val user = authUserRepository.find(userId)

        if (user != null)
            it.json(user)
        else
            it.status(NO_CONTENT)
    }


    private fun createUser(): (Context) -> Unit = {
        transaction { tx ->
            // TODO implement
//                authUserRepository.insert(
//                    tx, authUser()
//                        .applicationName("applicationName")
//                        .email("pojo.email")
//                        .passwordHash("pojo.passwordHash")
//                        .build()
//                )
        }
    }
}