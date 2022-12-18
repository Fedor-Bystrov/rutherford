import org.flywaydb.core.Flyway
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging.INFO
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer

abstract class GenerateSchemaTask : DefaultTask() {
    @get:Input
    abstract val dockerimageName: Property<String>

    @get:Input
    abstract val outputPackage: Property<String>

    @get:Input
    abstract val forcedTypes: ListProperty<ForcedType>

    @TaskAction
    fun generateSchema() {
        PostgreSQLContainer(dockerimageName.get()).use {
            println("Staring ${it.getDockerImageName()} container...")
            it.start()

            val jdbcUrl = it.getJdbcUrl()
            val username = it.getUsername()
            val password = it.getPassword()

            println("Applying migrations...")
            val migraitonsLocation = "filesystem:${project.projectDir}/src/main/resources/db/migration"

            println("Migrations path: $migraitonsLocation")
            migrate(
                jdbcUrl = jdbcUrl,
                username = username,
                password = password,
                locations = migraitonsLocation
            )

            println("Generating schema...")
            generateSchema(jdbcUrl = jdbcUrl, username = username, password = password)
        }
    }

    private fun migrate(jdbcUrl: String, username: String, password: String, locations: String) {
        Flyway.configure()
            .dataSource(jdbcUrl, username, password)
            .validateMigrationNaming(true)
            .locations(locations)
            .connectRetries(3)
            .load()
            .migrate()
    }

    private fun generateSchema(jdbcUrl: String, username: String, password: String) {
        val configuration = Configuration()
            .withLogging(INFO)
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(jdbcUrl)
                    .withUser(username)
                    .withPassword(password)
            )
            .withGenerator(
                Generator()
                    .withName("org.jooq.codegen.KotlinGenerator")
                    .withDatabase(
                        Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withIncludes(".*")
                            .withExcludes("public.flyway_schema_history")
                            .withInputSchema("public")
                            .withSchemaVersionProvider(
                                "SELECT :schema_name || '_' || MAX(\"version\") " + "FROM \"flyway_schema_history\""
                            )
                            .withForcedTypes(forcedTypes.get())
                    )
                    .withTarget(
                        Target()
                            .withPackageName(outputPackage.get())
                            .withDirectory("${project.projectDir}/src/main/kotlin")
                    )
                    .withGenerate(
                        Generate()
                            .withFluentSetters(true)
                            .withRecords(true)
                            .withRelations(true)
                            .withKotlinSetterJvmNameAnnotationsOnIsPrefix(true)
                            .withDaos(false)
                            .withPojos(false)
                            .withPojosAsKotlinDataClasses(false)
                            .withDeprecated(false)
                            .withImmutablePojos(false)
                            .withJavaTimeTypes(false)
                    )
            )
        GenerationTool.generate(configuration)
    }
}