import io.javalin.Javalin


// TODO
//  1. Add flyway support
//  2. Create auth model similar to django / .net (but with jwt in mind)
//  3. Add hibernate support
//  4. Crate sign-up flow
//      4.1 with jwt
//      4.2 write tests (unit + e2e with test-containers)
//  5. Create sign-in flow
//  6. Create log-out flow
//  7. TODO App should support 2FA, email confirmation (with resending email confirmation) etc
//      7.1 Check .net identity and create TODO for functionality that I need to implement

fun main() {
    val app = Javalin.create(/*config*/)
        .get("/") { ctx -> ctx.result("Hello World") }
        .start(7070)
}