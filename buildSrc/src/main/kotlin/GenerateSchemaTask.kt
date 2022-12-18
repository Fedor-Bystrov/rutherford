import org.flywaydb.core.Flyway
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer

open class GenerateSchemaTask : DefaultTask() {
    @TaskAction
    fun generateSchema() {
        val container = PostgreSQLContainer("postgres:15.1") // TODO dockerImageName extract to property

        println("Staring PostgreSQLContainer...") // TODO print version in log
        container.start()
        Runtime.getRuntime().addShutdownHook(Thread(container::stop))

        val jdbcUrl = container.getJdbcUrl()
        val username = container.getUsername()
        val password = container.getPassword()

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

        container.close()
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
        val forcedTypes: List<ForcedType> = listOf( // TODO extract to property
            ForcedType()
                .withUserType("java.time.Instant")
                .withConverter("app.rutherford.schema.converter.InstantConverter")
                .withIncludeTypes("Timestamp"),
            ForcedType()
                .withUserType("app.rutherford.core.ApplicationName")
                .withConverter("org.jooq.impl.EnumConverter")
                .withIncludeExpression(
                    """.*\.AUTH_USER\.application_name"""
                ),
            ForcedType()
                .withUserType("app.rutherford.core.entity.Entity.State")
                .withConverter("org.jooq.impl.EnumConverter")
                .withIncludeExpression(
                    """.*\.auth_user_token\.state"""
                )
        )

        val configuration = Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver") // TODO extract to propertyu
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
                            .withForcedTypes(forcedTypes)
                    )
                    .withTarget(
                        Target() // TODO extract to property
                            .withPackageName("app.rutherford.schema.generated")
                            .withDirectory("src/main/kotlin")
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