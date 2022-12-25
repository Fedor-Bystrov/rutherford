package app.rutherford.auth.exception

import app.rutherford.core.ApplicationName

class UserAlreadyExistException(email: String, applicationName: ApplicationName) :
    RuntimeException("User with $email and $applicationName already exist")
