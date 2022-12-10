import io.javalin.Javalin
import org.flywaydb.core.Flyway


// TODO
//  1. Create auth model similar to django / .net (but with jwt in mind)
//  2. Add hibernate support
//  3. Crate sign-up flow
//      3.1 with jwt
//      3.2 write tests (unit + e2e with test-containers)
//  4. Create sign-in flow
//  5. Create log-out flow
//  6. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      6.1 Check .net identity and create TODO for functionality that I need to implement

fun main() {
    runMigrations();

    val app = Javalin
        .create { config -> config.showJavalinBanner = false; }
        .get("/") { ctx -> ctx.result("Hello World") }
        .start(7070) // TODO add graceful shutdown?
}

private fun runMigrations() {
    val url = "jdbc:postgresql://localhost:5432/rutherford"
    val user = "rutherford_app"
    val password = "123"

    Flyway.configure()
        .dataSource(url, user, password)
        .load()
        .migrate()
}