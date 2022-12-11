package app.rutherford.module

import app.rutherford.module.configuration.DatabaseConfig
import app.rutherford.module.tool.FlywayMigrator.migrate
import app.rutherford.module.tool.JooqGenerator.generateSchema
import io.javalin.Javalin

class ApplicationModule {
    private val databaseConfig: DatabaseConfig
    private val database: DatabaseModule
    private val javalin: Javalin
    private val resources: ResourceModule

    init {
        // TODO extract to env files (add .env support)
        val url = "jdbc:postgresql://localhost:5432/rutherford"
        val user = "rutherford_app"
        val password = "123"

        databaseConfig = DatabaseConfig(
            jdbcUrl = url,
            username = user,
            password = password
        )
        database = DatabaseModule(databaseConfig)

        javalin = Javalin.create { config -> config.showJavalinBanner = false }

        resources = ResourceModule(javalin)
    }

    fun start() {
        migrate(databaseConfig)
        generateSchema(databaseConfig)
        resources.bindRoutes()
        javalin.start(7070) // TODO read port from env
    }

    fun stop() {
        javalin.stop()
    }
}