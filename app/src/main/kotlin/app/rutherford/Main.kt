package app.rutherford

import app.rutherford.module.ApplicationModule

// TODO Auth Functionality
//  1. Crate sign-up flow
//      2.1 with jwt
//      2.2 write tests (unit + e2e with test-containers)
//  2. Add exception mapping
//  3. Create sign-in flow
//  4. Create log-out flow
//  5. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      4.1 Check .net identity and create TODO for functionality that I need to implement

// TODO Set up
//  - validate jvm pros for docker
//  - write todo, mention how to compile and build docker image

fun main() {
    val application = ApplicationModule(Overrides())
    application.start()
    Runtime
        .getRuntime()
        .addShutdownHook(Thread(application::stop))
}
