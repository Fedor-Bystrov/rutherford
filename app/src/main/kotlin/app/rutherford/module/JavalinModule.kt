package app.rutherford.module

import app.rutherford.core.exception.EntityNotFoundException
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
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
import io.javalin.http.HttpStatus.INTERNAL_SERVER_ERROR
import io.javalin.http.HttpStatus.NOT_FOUND
import io.javalin.json.JavalinJackson
import io.javalin.validation.JavalinValidation
import io.javalin.validation.ValidationException
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.util.*
import java.util.UUID.randomUUID
import java.util.concurrent.TimeUnit.MINUTES
import kotlin.reflect.KClass

const val X_REQUEST_ID = "X-Request-ID";

class JavalinModule {
    private val logger = LoggerFactory.getLogger("Rutherford")
    val javalin: Javalin = Javalin.create { config ->
        config.showJavalinBanner = false
        config.http.asyncTimeout = MINUTES.toMillis(1)
        config.jsonMapper(
            JavalinJackson(
                jacksonObjectMapper()
                    .registerModule(JavaTimeModule())
                    .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(NON_NULL)
            )
        )
        config.requestLogger.http { ctx, executionTimeMs ->
            logger.info(
                "[${ctx.res().getHeader(X_REQUEST_ID)}] ${ctx.ip()} ${ctx.userAgent()} ${ctx.contentType()}" +
                        " ${ctx.contentLength()} ${ctx.method()} ${ctx.path()} ${ctx.statusCode()}" +
                        " in $executionTimeMs ms"
            )
        }
    }

    init {
        javalin.after { MDC.clear() }
        javalin.before { ctx ->
            if (ctx.header(X_REQUEST_ID).isNullOrBlank()) {
                ctx.header(X_REQUEST_ID, randomUUID().toString())
            }
            MDC.put("request-id", ctx.res().getHeader(X_REQUEST_ID))
        }

        // Validators
        JavalinValidation.register(UUID::class.java) { UUID.fromString(it) }

        // Exception Mappers // TODO write tests on mappers
        exception(JsonParseException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, "Malformed JSON") }
        exception(ValidationException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, getMessage(e)) }
        exception(IllegalStateException::class) { e, c -> nonCritical(e, c, BAD_REQUEST, e.message) }
        exception(EntityNotFoundException::class) { e, c -> nonCritical(e, c, NOT_FOUND, "Not Found") }
        exception(Exception::class) { e, c -> internalServerError(e, c) }
    }

    fun start(applicationPort: Int) {
        javalin.start(applicationPort)
    }

    fun stop() {
        javalin.stop()
    }

    private fun <E : Exception> exception(clazz: KClass<E>, exceptionHandler: ExceptionHandler<E>) =
        javalin.exception(clazz.java, exceptionHandler)


    private fun <E : Exception> nonCritical(e: E, ctx: Context, httpStatus: HttpStatus, message: String?) =
        nonCritical(e, ctx, httpStatus, JSONObject().put("message", message))

    private fun <E : Exception> nonCritical(e: E, ctx: Context, httpStatus: HttpStatus, json: JSONObject) {
        logger.info("An error occurred", e)
        ctx.status(httpStatus)
        ctx.json(json.put("code", httpStatus.code).toString())
    }

    private fun <E : Exception> internalServerError(e: E, ctx: Context) {
        logger.error("An error occurred", e)
        ctx.status(INTERNAL_SERVER_ERROR)
        ctx.json(
            JSONObject()
                .put("message", "Internal Server Error")
                .put("code", INTERNAL_SERVER_ERROR.code)
                .toString()
        )

        when (e) {
            InterruptedException::class.java -> {
                Thread.currentThread().interrupt()
            }
        }
    }

    private fun getMessage(e: ValidationException): String {
        return "Validation error. Invalid parameters: ${e.errors.keys}"
    }
}
