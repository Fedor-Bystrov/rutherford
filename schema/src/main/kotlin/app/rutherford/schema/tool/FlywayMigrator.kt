package app.rutherford.schema.tool

import org.flywaydb.core.Flyway
import javax.sql.DataSource

object FlywayMigrator {
    fun migrate(dataSource: DataSource) {
        Flyway.configure()
            .dataSource(dataSource)
            .validateMigrationNaming(true)
            .connectRetries(3)
            .load()
            .migrate()
    }
}