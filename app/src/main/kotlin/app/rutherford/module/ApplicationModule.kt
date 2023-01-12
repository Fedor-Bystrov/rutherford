package app.rutherford.module

import app.rutherford.Overrides
import app.rutherford.configuration.DatabaseConfig
import app.rutherford.core.transaction.TransactionManager
import app.rutherford.core.types.Base64.Companion.base64
import app.rutherford.core.util.Dotenv
import app.rutherford.schema.tool.FlywayMigrator.migrate

class ApplicationModule(
    overrides: Overrides
) {
    private val databaseConfig: DatabaseConfig
    private val database: DatabaseModule
    private val transactionManager: TransactionManager
    private val javalinModule: JavalinModule
    private val resources: ResourceModule

    val applicationPort: Int
    val repository: RepositoryModule
    val managerModule: ManagerModule

    init {
        val url = Dotenv.get("DB_URL")
        val user = Dotenv.get("DB_USER")
        val password = Dotenv.get("DB_PASS")
        applicationPort = Dotenv.getInt("PORT")

        val authUserSecret = base64(Dotenv.get("AUTH_USER_SECRET"))

        databaseConfig = overrides.databaseConfig ?: DatabaseConfig(
            jdbcUrl = url,
            username = user,
            password = password
        )
        database = DatabaseModule(databaseConfig, overrides)
        repository = RepositoryModule(database.dslContext)
        managerModule = ManagerModule(repository, authUserSecret)
        transactionManager = TransactionManager.create(database.dslContext)
        javalinModule = JavalinModule()
        resources = ResourceModule(javalinModule.javalin, managerModule)
    }

    fun start() {
        migrate(database.dataSource)
        resources.bindRoutes()
        javalinModule.start(applicationPort)
    }

    fun stop() {
        javalinModule.stop()
    }
}
