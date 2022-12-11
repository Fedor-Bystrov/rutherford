package app.rutherford.configuration

data class DatabaseConfig(
    val jdbcUrl: String,
    val user: String,
    val password: String,
) {
    init {
        require(jdbcUrl.isNotBlank()) { "jdbcUrl is blank " }
        require(user.isNotBlank()) { "user is blank " }
        require(password.isNotBlank()) { "password is blank " }
    }
}