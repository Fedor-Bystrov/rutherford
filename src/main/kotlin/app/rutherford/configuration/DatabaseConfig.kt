package app.rutherford.configuration

data class DatabaseConfig(
    val jdbcUrl: String,
    val username: String,
    val password: String,
) {
    init {
        require(jdbcUrl.isNotBlank()) { "jdbcUrl is blank " }
        require(username.isNotBlank()) { "username is blank " }
        require(password.isNotBlank()) { "password is blank " }
    }
}