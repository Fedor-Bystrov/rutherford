package app.rutherford.auth.resource.request

data class SignInRequest(
    val email: String,
    val password: String,
) {
    override fun toString(): String {
        return "<masked>"
    }
}
