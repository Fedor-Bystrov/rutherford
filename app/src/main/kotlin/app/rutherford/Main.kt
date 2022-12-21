package app.rutherford

import app.rutherford.module.ApplicationModule

// TODO 1. Basic Auth Functionality
//  - Create AuthManger it should implement following functionalities
//      - sign_up (create user)
//      - sign_in (issue access and refresh tokens given correct user details)
//      - log_out (remove refresh_token i.e. move refresh_token to deleted state)
//      - change_password
//      - anything else (check .net identity)
//  - Use .net identity as a reference (but with jwt in mind)

// TODO 2. Email confirmation Functionality
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
