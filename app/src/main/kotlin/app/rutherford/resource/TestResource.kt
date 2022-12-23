package app.rutherford.resource

import app.rutherford.auth.entity.AuthUser
import app.rutherford.auth.entity.AuthUser.Builder.Companion.authUser
import app.rutherford.auth.repository.AuthUserRepository
import app.rutherford.auth.util.Argon2PasswordHasher
import app.rutherford.auth.util.PBKDF2PasswordHasher
import app.rutherford.core.ApplicationName.TEST1
import app.rutherford.core.abstract.entity.Entity.Id
import app.rutherford.core.abstract.resource.Resource
import app.rutherford.core.transaction.transaction
import app.rutherford.core.types.Base64
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
                get("/argon2", argon2())
                get("/pbkdf2", pbkdf2())
                get("/{id}", one())
                post(createUser())
            }
        }
    }

    private val salt = Base64.of("afo5hZL7oBUPwm5PPGQwnw==") // 16 byte
    private val key = Base64.of("1ZzA+CxCCCVSxnsOaYdC4ZKkTvvx/oYw4ON0DwbNZFM=") // 32 byte
    private val pass = "S2JTt%G%I#ugLGxrGwej*BwejrfjI64wLcd9Zcbe0\$sbcJZo@X"

    private fun argon2(): (Context) -> Unit = {
        val hasher = Argon2PasswordHasher(salt, key)

        for (i in 0..1_000) {
            hasher.hash(pass)
        }
    }

    private fun pbkdf2(): (Context) -> Unit = {
        val hasher = PBKDF2PasswordHasher(salt)

        for (i in 0..1_000) {
            hasher.hash(pass)
        }
    }

    private fun all(): (Context) -> Unit = {
        it.json(
            authUserRepository.find(
                ids = listOf(
                    UUID.fromString("60d327e7-c936-4ec9-99a0-9903de82fe59"),
                    UUID.fromString("e09ce7ca-c30e-4735-bb02-1efa413bdada"),
                    UUID.fromString("38cf9d9c-6f00-4fff-9092-82a325e442eb")
                ).map { id -> Id(id) }
            )
        )
    }


    private fun one(): (Context) -> Unit = {
        val userId = Id<AuthUser>(it.pathParamAsClass("id", UUID::class.java).get())
        val user = authUserRepository.find(id = userId)

        if (user != null)
            it.json(user)
        else
            it.status(NO_CONTENT)
    }


    private fun createUser(): (Context) -> Unit = {
        transaction { tx ->
            authUserRepository.insert(
                tx, authUser()
                    .applicationName(TEST1)
                    .email("pojo.email2")
                    .passwordHash("pojo.passwordHash")
                    .build()
            )
        }
    }
}
