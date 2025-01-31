package app.rutherford

import app.rutherford.configuration.DatabaseConfig
import app.rutherford.core.transaction.transaction
import app.rutherford.module.ApplicationModule
import app.rutherford.module.ManagerModule
import app.rutherford.module.RepositoryModule
import app.rutherford.schema.generated.tables.references.AUTH_USER
import org.jooq.conf.Settings
import org.testcontainers.containers.PostgreSQLContainer

class TestEnvironment : AutoCloseable {
    private val postgresContainer = PostgreSQLContainer("postgres:15.1")
    private val application: ApplicationModule

    val applicationPort: Int
    val repository: RepositoryModule
    val managerModule: ManagerModule

    init {
        postgresContainer.start()

        application = ApplicationModule(
            Overrides(
                databaseConfig = DatabaseConfig(
                    jdbcUrl = postgresContainer.jdbcUrl,
                    username = postgresContainer.username,
                    password = postgresContainer.password
                ),
                jooqSettings = Settings()
                    .withReturnAllOnUpdatableRecord(true)
            )
        )

        applicationPort = application.applicationPort
        repository = application.repository
        managerModule = application.managerModule

        addShutdownHook(Thread(postgresContainer::stop))
        addShutdownHook(Thread(application::stop))
        application.start()
    }

    fun reset() {
        transaction {
            this.configuration.dsl().deleteFrom(AUTH_USER).execute()
        }
    }

    override fun close() {
        postgresContainer.close()
        application.stop()
    }

    private fun addShutdownHook(thread: Thread) = Runtime
        .getRuntime()
        .addShutdownHook(thread)
}
