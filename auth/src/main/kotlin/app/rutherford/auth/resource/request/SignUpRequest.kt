package app.rutherford.auth.resource.request

import app.rutherford.core.util.Checks.validateEmailFormat
import app.rutherford.core.util.Checks.validateNotBlank

data class SignUpRequest(val email: String, val password1: String, val password2: String) {
    init { // TODO test that validation returns correct exceptions
        validateEmailFormat(email)
        validateNotBlank("password1", email)
        validateNotBlank("password2", email)
        check(password1 == password2) { "passwords do not match" } // TODO write test + check response is correct
    }
}
