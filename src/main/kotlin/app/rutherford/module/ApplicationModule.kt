package app.rutherford.module

import app.rutherford.database.transaction.TransactionManager
import app.rutherford.module.configuration.DatabaseConfig
import app.rutherford.module.tool.FlywayMigrator.migrate
import app.rutherford.module.tool.JooqGenerator.generateSchema

class ApplicationModule {
    private val applicationPort: Int
    private val databaseConfig: DatabaseConfig
    private val database: DatabaseModule
    private val repository: RepositoryModule
    private val transactionManager: TransactionManager
    private val javalinModule: JavalinModule
    private val resources: ResourceModule

    init {
        // TODO extract to env files (add .env support)
        val url = "jdbc:postgresql://localhost:5432/rutherford"
        val user = "rutherford_app"
        val password = "123"
        applicationPort = 7070

        databaseConfig = DatabaseConfig(
            jdbcUrl = url,
            username = user,
            password = password
        )
        database = DatabaseModule(databaseConfig)
        repository = RepositoryModule(database.dslContext)
        transactionManager = TransactionManager.of(database.dslContext)
        javalinModule = JavalinModule()
        resources = ResourceModule(javalinModule.javalin, repository)
    }

    fun start() {
        migrate(database.dataSource)
        generateSchema(databaseConfig)
        resources.bindRoutes()
        javalinModule.start(applicationPort)
    }

    fun stop() {
        javalinModule.stop()
    }
}