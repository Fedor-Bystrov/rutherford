package app.rutherford.module

import app.rutherford.database.exception.EntityNotFoundException
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.ExceptionHandler
import io.javalin.http.HttpStatus
import io.javalin.http.HttpStatus.BAD_REQUEST
import io.javalin.http.HttpStatus.NOT_FOUND
import io.javalin.json.JavalinJackson
import io.javalin.validation.JavalinValidation
import io.javalin.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.reflect.KClass

class JavalinModule {
    val javalin: Javalin
    private val logger: Logger

    init {
        // TODO add exception mapper, handle JavalinValidation exception
        JavalinValidation.register(UUID::class.java) { UUID.fromString(it) }

        logger = LoggerFactory.getLogger("Rutherford")
        val jsonMapper = JavalinJackson(
            jacksonObjectMapper()
                .registerModule(JavaTimeModule())
                .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        )

        javalin = Javalin.create { config ->
            config.showJavalinBanner = false
            config.jsonMapper(jsonMapper)
            config.requestLogger.http { ctx, executionTimeMs ->
                logger.info(
                    "${ctx.ip()} ${ctx.userAgent()} ${ctx.contentType()} ${ctx.contentLength()} " +
                            "${ctx.method()} ${ctx.path()} ${ctx.statusCode()} in $executionTimeMs ms"
                )
            }
        }

        exception(JsonParseException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, "Malformed JSON") }
        exception(ValidationException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, getMessage(e)) }
        exception(IllegalStateException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, e.message) }
        exception(EntityNotFoundException::class) { e, c -> nonCritical(e, c, NOT_FOUND, "Not Found") }
    }

    fun start(applicationPort: Int) {
        javalin.start(applicationPort)
    }

    fun stop() {
        javalin.stop()
    }

    private fun <E : Exception> exception(clazz: KClass<E>, exceptionHandler: ExceptionHandler<E>) =
        javalin.exception(clazz.java, exceptionHandler)

    private fun <E : Exception> nonCritical(e: E, ctx: Context, httpStatus: HttpStatus, message: String?) {
        logger.info("An error occurred with id /*TODO add ID*/", e)
        ctx.status(httpStatus)
        ctx.json(
            mapOf(
                "message" to message, // TODO swap to fluent json builder
                "code" to httpStatus.code
            )
        )
    }

    private fun getMessage(e: ValidationException): String {
        return "Validation error. Invalid parameters: ${e.errors.keys}"
    }
}