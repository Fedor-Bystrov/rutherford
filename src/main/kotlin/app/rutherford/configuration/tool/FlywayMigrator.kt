package app.rutherford.configuration.tool

import app.rutherford.configuration.DatabaseConfig
import org.flywaydb.core.Flyway

object FlywayMigrator {
    fun migrate(databaseConfig: DatabaseConfig) {
        Flyway.configure()
            .dataSource(
                databaseConfig.jdbcUrl,
                databaseConfig.username,
                databaseConfig.password
            )
            .validateMigrationNaming(true)
            .load()
            .migrate()
    }
}