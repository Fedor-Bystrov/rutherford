package app.rutherford.auth.resource.request

data class SignUpRequest(
    val email: String,
    val password1: String,
    val password2: String
)
