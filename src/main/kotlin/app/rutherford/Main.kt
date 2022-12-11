package app.rutherford

import app.rutherford.module.ApplicationModule


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
//  4. Add logback.xml (similar to penn)
//  5. Set up test containers for e2e tests

fun main() {
    System.setProperty("org.jooq.no-logo", "true")
    System.setProperty("org.jooq.no-tips", "true")

    val application = ApplicationModule()
    application.start()
    Runtime
        .getRuntime()
        .addShutdownHook(Thread(application::stop))

//    dslContext.transaction { tx ->
//        val now = Instant.now()
//        AuthUserDao(tx)
//            .insert(AuthUser(randomUUID(), now, now, now, "aa", "test4@test.com", false, ""))
//    }
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