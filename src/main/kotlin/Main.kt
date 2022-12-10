import io.javalin.Javalin


// TODO
//  1. Set up mysql database in docker
//  2. Add flyway support
//  3. Add hibernate support
//  4. Create auth model similar to django / .net (but with jwt in mind)
//  5. Crate sign-up flow
//      5.1 with jwt
//      5.2 write tests (unit + e2e with test-containers)
//  6. Create sign-in flow
//  7. Create log-out flow
//  8. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      8.1 Check .net identity and create TODO for functionality that I need to implement

fun main() {
    val app = Javalin.create(/*config*/)
        .get("/") { ctx -> ctx.result("Hello World") }
        .start(7070)
}