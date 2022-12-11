package app.rutherford

import app.rutherford.configuration.FlywayMigrator.migrate
import app.rutherford.configuration.JooqGenerator.generateSchema
import org.jooq.SQLDialect.POSTGRES
import org.jooq.impl.DefaultConfiguration
import java.sql.DriverManager
import java.time.Instant


// TODO auth functionality
//  1. Crate sign-up flow
//      2.1 with jwt
//      2.2 write tests (unit + e2e with test-containers)
//  2. Create sign-in flow
//  3. Create log-out flow
//  4. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      4.1 Check .net identity and create TODO for functionality that I need to implement

// TODO Set up
//  1. Add hikaricp
//  2. Copy project structure similar to penn
//  3. Add graceful shutdown
//  3. Add logback.xml (similar to penn)

fun main() {
    System.setProperty("org.jooq.no-logo", "true")
    System.setProperty("org.jooq.no-tips", "true")

    // TODO extract to env files (add .env support)
    val url = "jdbc:postgresql://localhost:5432/rutherford"
    val user = "rutherford_app"
    val password = "123"

    migrate(url, user, password)
    generateSchema(url, user, password)

    val connection = DriverManager.getConnection(url, user, password)
    val configuration = DefaultConfiguration()
        .set(connection)
        .set(POSTGRES)

    // fix table names, it should be singular instead of plural
    val now = Instant.now()

//    AuthUserDao(configuration)
//        .insert(AuthUser(randomUUID(), now, now, now, "aa", "test1@test.com", false, ""))


//    val app = Javalin
//        .create { config -> config.showJavalinBanner = false; }
//        .get("/") { ctx -> ctx.result("Hello World") }
//        .start(7070) // TODO add graceful shutdown?
}

//https://javalin.io/tutorials/javalin-java-10-google-guice
// package io.kidbank.user;
//
//import io.alzuma.Routing;
//import io.javalin.Javalin;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import static io.javalin.apibuilder.ApiBuilder.get;
//import static io.javalin.apibuilder.ApiBuilder.path;
//
//@Singleton
//class UserRouting extends Routing<UserController> {
//    private Javalin javalin;
//    @Inject
//    public UserRouting(Javalin javalin) {
//        this.javalin = javalin;
//    }
//
//    @Override
//    public void bindRoutes() {
//        javalin.routes(() -> {
//            path("api/kidbank/users", () -> {
//                get(ctx -> getController().index(ctx));
//            });
//        });
//    }
//}