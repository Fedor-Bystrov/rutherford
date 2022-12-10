package app.rutherford

import app.rutherford.database.schema.tables.daos.UsersDao
import app.rutherford.database.schema.tables.pojos.Users
import org.flywaydb.core.Flyway
import org.jooq.SQLDialect.POSTGRES
import org.jooq.codegen.GenerationTool
import org.jooq.impl.DefaultConfiguration
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import java.sql.DriverManager
import java.time.Instant
import java.util.UUID.randomUUID


// TODO
//  1. Add hibernate support
//  2. Crate sign-up flow
//      2.1 with jwt
//      2.2 write tests (unit + e2e with test-containers)
//  3. Create sign-in flow
//  4. Create log-out flow
//  5. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      5.1 Check .net identity and create TODO for functionality that I need to implement


// TODO use HikariCP

fun main() {
    val url = "jdbc:postgresql://localhost:5432/rutherford"
    val user = "rutherford_app"
    val password = "123"

    runMigrations(url, user, password)
    jooqGenerate()

    val connection = DriverManager.getConnection(url, user, password)
    val configuration = DefaultConfiguration()
        .set(connection)
        .set(POSTGRES)

    // fix table names, it should be singular instead of plural
    val now = Instant.now()

    UsersDao(configuration)
        .insert(Users(randomUUID(), now, now, now, "aa", "test@test.com", false, ""))


//    val app = Javalin
//        .create { config -> config.showJavalinBanner = false; }
//        .get("/") { ctx -> ctx.result("Hello World") }
//        .start(7070) // TODO add graceful shutdown?
}

private fun runMigrations(url: String, user: String, password: String) {
    Flyway.configure()
        .dataSource(url, user, password)
        .validateMigrationNaming(true)
        .load()
        .migrate()
}

private fun jooqGenerate() {
    // https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/
    // https://www.jooq.org/doc/latest/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-version-providers/
    val configuration = Configuration()
        .withJdbc(
            Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl("jdbc:postgresql://localhost:5432/rutherford")
                .withUser("rutherford_app")
                .withPassword("123")
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
                        .withForcedTypes(
                            ForcedType()
                                .withUserType("java.time.Instant")
                                .withConverter("app.rutherford.database.converter.InstantConverter")
                                .withTypes("Timestamp")
                        )
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

    GenerationTool.generate(configuration);
}