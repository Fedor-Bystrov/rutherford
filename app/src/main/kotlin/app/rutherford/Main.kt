package app.rutherford

import app.rutherford.module.ApplicationModule

// TODO 1. Basic Auth Functionality
//  - Implement AuthResource it should have following functionalities:
//      - sign_in (issue access and refresh tokens given correct user details)
//          - Use asymmetric encryption for JWT
//          - Expose JWT public key list endpoint
//      - log_out (remove refresh_token i.e. move refresh_token to deleted state)
//      - change_password
//      - anything else (check .net identity)
//  - Use .net identity as a reference (but with jwt in mind)

// TODO 2. Add CORS, allow only my apps to access the BE

// TODO 3. Email confirmation Functionality
//  - sign_up should send an email to confirm the user's email address
//  - sign_in should return error and ask to confirm the email
//      - user should be able to resend confirmation email

fun main() {
    val application = ApplicationModule(Overrides())
    application.start()
    Runtime
        .getRuntime()
        .addShutdownHook(Thread(application::stop))
}
