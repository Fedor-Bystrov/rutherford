package app.rutherford.util

object Checks {
    fun <T : Any> validateNotNull(paramName: String, value: T?): T {
        return checkNotNull(value) { "$paramName is null" }
    }

    fun validateNotBlank(paramName: String, value: String?): String {
        check(!value.isNullOrBlank()) { "$paramName is null or blank" }
        return value
    }
}