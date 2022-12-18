package app.rutherford.module

import app.rutherford.Overrides
import app.rutherford.core.configuration.DatabaseConfig
import app.rutherford.database.transaction.TransactionManager
import app.rutherford.util.Dotenv
import app.rutherford.schema.tool.FlywayMigrator.migrate
import app.rutherford.schema.tool.JooqGenerator.generateSchema

class ApplicationModule(
    overrides: Overrides
) {
    private val applicationPort: Int
    private val databaseConfig: DatabaseConfig
    private val database: DatabaseModule
    private val transactionManager: TransactionManager
    private val javalinModule: JavalinModule
    private val resources: ResourceModule

    val repository: RepositoryModule

    init {
        val url = Dotenv.get("DB_URL")
        val user = Dotenv.get("DB_USER")
        val password = Dotenv.get("DB_PASS")
        applicationPort = Dotenv.getInt("PORT")

        databaseConfig = overrides.databaseConfig ?: DatabaseConfig(
            jdbcUrl = url,
            username = user,
            password = password
        )
        database = DatabaseModule(databaseConfig, overrides)
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