package app.rutherford

import app.rutherford.module.ApplicationModule
import app.rutherford.module.RepositoryModule
import app.rutherford.module.configuration.DatabaseConfig
import org.testcontainers.containers.PostgreSQLContainer

class TestEnvironment : AutoCloseable {
    private val postgresContainer = PostgreSQLContainer("postgres:15.1")
    private val application: ApplicationModule

    val repository: RepositoryModule

    init {
        postgresContainer.start()

        application = ApplicationModule(
            Overrides(
                databaseConfig = DatabaseConfig(
                    jdbcUrl = postgresContainer.jdbcUrl,
                    username = postgresContainer.username,
                    password = postgresContainer.password
                )
            )
        )

        repository = application.repository

        addShutdownHook(Thread(postgresContainer::stop))
        addShutdownHook(Thread(application::stop))
        application.start()
    }

    fun resetMocks() {
        // TODO implement
    }

    override fun close() {
        postgresContainer.close()
        application.stop()
    }

    private fun addShutdownHook(thread: Thread) = Runtime
        .getRuntime()
        .addShutdownHook(thread)
}