package app.rutherford.module

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import io.javalin.validation.JavalinValidation
import org.slf4j.LoggerFactory
import java.util.*

class JavalinModule {
    val javalin: Javalin

    init {
        // TODO add exception mapper, handle JavalinValidation exception
        JavalinValidation.register(UUID::class.java) { UUID.fromString(it) }

        val logger = LoggerFactory.getLogger("Rutherford")
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
    }

    fun start(applicationPort: Int) {
        javalin.start(applicationPort)
    }

    fun stop() {
        javalin.stop()
    }
}