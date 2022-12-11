package app.rutherford.configuration

import org.flywaydb.core.Flyway

object FlywayMigrator {
    fun migrate(url: String, user: String, password: String) {
        Flyway.configure()
            .dataSource(url, user, password)
            .validateMigrationNaming(true)
            .load()
            .migrate()
    }
}