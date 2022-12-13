package app.rutherford.module

import app.rutherford.module.configuration.DatabaseConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect.POSTGRES
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS

class DatabaseModule(private val dbConfig: DatabaseConfig) {
    val dslContext: DSLContext

    init {
        val hikariConfig = hikariConfig()
        val dataSource = HikariDataSource(hikariConfig)
        val configuration = DefaultConfiguration()
            .set(dataSource)
            .set(
                Settings()
                    // Fetched all columns from inserted / stored entity
                    // and updated the UpdatableRecord
                    .withReturnAllOnUpdatableRecord(true)
            )
            .set(POSTGRES)
        dslContext = DSL.using(configuration)
    }

    private fun hikariConfig(): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = "org.postgresql.Driver"
        hikariConfig.jdbcUrl = dbConfig.jdbcUrl
        hikariConfig.username = dbConfig.username
        hikariConfig.password = dbConfig.password
        hikariConfig.minimumIdle = 1
        hikariConfig.maximumPoolSize = 30
        hikariConfig.connectionTimeout = SECONDS.toMillis(1)
        hikariConfig.idleTimeout = MINUTES.toMillis(1)
        hikariConfig.maxLifetime = MINUTES.toMillis(5)
        hikariConfig.leakDetectionThreshold = MINUTES.toMillis(1)
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.validationTimeout = SECONDS.toMillis(1)
        hikariConfig.isAutoCommit = false
        return hikariConfig
    }
}