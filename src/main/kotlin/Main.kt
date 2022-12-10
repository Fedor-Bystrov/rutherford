import database.entity.User
import org.flywaydb.core.Flyway
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Environment
import java.time.Instant.now


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
    runMigrations()

    val registry = StandardServiceRegistryBuilder()
        .applySetting(Environment.DRIVER, "org.postgresql.Driver")
        .applySetting(Environment.URL, "jdbc:postgresql://localhost:5432/rutherford")
        .applySetting(Environment.USER, "rutherford_app")
        .applySetting(Environment.PASS, "123")
        .applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
        .build()

    val sessionFactory = MetadataSources(registry)
        .addAnnotatedClass(User::class.java)
        .buildMetadata()
        .buildSessionFactory()

    val session = sessionFactory.openSession()
    val tx = session.beginTransaction()

    val now = now()
    session.persist(
        User(
            createdAt = now,
            updatedAt = now,
            applicationName = "Test",
            email = "test@test.com",
            passwordHash = "hash"
        )
    )
    tx.commit();
    session.close()


//    val app = Javalin
//        .create { config -> config.showJavalinBanner = false; }
//        .get("/") { ctx -> ctx.result("Hello World") }
//        .start(7070) // TODO add graceful shutdown?
}

private fun runMigrations() {
    val url = "jdbc:postgresql://localhost:5432/rutherford"
    val user = "rutherford_app"
    val password = "123"

    Flyway.configure()
        .dataSource(url, user, password)
        .validateMigrationNaming(true)
        .load()
        .migrate()
}