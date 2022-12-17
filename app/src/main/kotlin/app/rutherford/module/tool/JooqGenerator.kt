package app.rutherford.module.tool

import app.rutherford.module.configuration.DatabaseConfig
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target

// https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/
// https://www.jooq.org/doc/latest/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-version-providers/
// https://www.jooq.org/doc/latest/manual/code-generation/codegen-matcherstrategy/
object JooqGenerator {
    private val FORCED_TYPES: List<ForcedType> = listOf(
        ForcedType()
            .withUserType("java.time.Instant")
            .withConverter("InstantConverter")
            .withIncludeTypes("Timestamp"),
        ForcedType()
            .withUserType("ApplicationName")
            .withConverter("org.jooq.impl.EnumConverter")
            .withIncludeExpression(
                """.*\.AUTH_USER\.application_name"""
            ),
        ForcedType()
            .withUserType("Entity.State")
            .withConverter("org.jooq.impl.EnumConverter")
            .withIncludeExpression(
                """.*\.auth_user_token\.state"""
            )
    )

    fun generateSchema(databaseConfig: DatabaseConfig) {
        val configuration = Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(databaseConfig.jdbcUrl)
                    .withUser(databaseConfig.username)
                    .withPassword(databaseConfig.password)
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
                            .withForcedTypes(FORCED_TYPES)
                    )
                    .withTarget(
                        Target()
                            .withPackageName("app.rutherford.database.jooq.generated")
                            .withDirectory("app/src/main/kotlin")
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