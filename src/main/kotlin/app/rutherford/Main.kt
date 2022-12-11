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
//  1. Add CreateUserAuthToken resource
//      1.1 it should accept json and create entity in the database
//      1.2 create transaction method similar to Exposed framework
//  2. Add logback.xml (similar to penn)
//  3. Set up test containers for e2e tests

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
