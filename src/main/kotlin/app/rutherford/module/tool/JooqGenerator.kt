package app.rutherford.module.tool

import app.rutherford.module.configuration.DatabaseConfig
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging.WARN
import org.jooq.meta.jaxb.Target

// https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/
// https://www.jooq.org/doc/latest/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-version-providers/
object JooqGenerator {
    private val FORCED_TYPES: List<ForcedType> = listOf(
        ForcedType()
            .withUserType("java.time.Instant")
            .withConverter("app.rutherford.database.converter.InstantConverter")
            .withTypes("Timestamp")
    )

    fun generateSchema(databaseConfig: DatabaseConfig) {
        val configuration = Configuration()
            .withLogging(WARN)
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(databaseConfig.jdbcUrl)
                    .withUser(databaseConfig.username)
                    .withPassword(databaseConfig.password)
            )
            .withGenerator(
                Generator()
                    .withDatabase(
                        Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withIncludes(".*")
                            .withExcludes("public.flyway_schema_history")
                            .withInputSchema("public")
                            .withSchemaVersionProvider(
                                "SELECT :schema_name || '_' || MAX(\"version\") " + "FROM \"flyway_schema_history\""
                            )
                            .withForcedTypes(FORCED_TYPES)
                    )
                    .withTarget(
                        Target()
                            .withPackageName("app.rutherford.database.schema")
                            .withDirectory("src/main/java")
                    )
                    .withGenerate(
                        Generate()
                            .withDaos(true)
                            .withDeprecated(false)
                            .withFluentSetters(true)
                            .withImmutablePojos(false)
                            .withRecords(true)
                            .withRelations(true)
                            .withJavaTimeTypes(false)
                    )
            )

        GenerationTool.generate(configuration)
    }
}