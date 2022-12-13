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

// TODO Set up !!
//  1. Implement insert
//  2. Add tests
//  3. Add logback.xml (similar to penn)
//  4. Set up test containers for e2e tests
//  5. Fix all other todos

// TODO fix
//     1. Flyway timout
//     2. Jooq generator timeout
//     3. Write tests on repositories

fun main() {
    System.setProperty("org.jooq.no-logo", "true")
    System.setProperty("org.jooq.no-tips", "true")

    val application = ApplicationModule()
    application.start()
    Runtime
        .getRuntime()
        .addShutdownHook(Thread(application::stop))
}
