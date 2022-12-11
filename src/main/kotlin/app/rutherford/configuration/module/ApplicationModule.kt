package app.rutherford.configuration.module

import app.rutherford.configuration.DatabaseConfig
import app.rutherford.configuration.tool.FlywayMigrator.migrate
import app.rutherford.configuration.tool.JooqGenerator.generateSchema

class ApplicationModule {
    private val databaseConfig: DatabaseConfig
    private val database: DatabaseModule

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
    }

    fun start() {
        migrate(databaseConfig)
        generateSchema(databaseConfig)
    }
}