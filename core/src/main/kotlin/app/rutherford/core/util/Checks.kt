package app.rutherford.core.util

import org.apache.commons.validator.routines.EmailValidator

object Checks {
    fun <T : Any> validateNotNull(paramName: String, value: T?): T {
        return checkNotNull(value) { "$paramName is null" }
    }

    fun validateNotBlank(paramName: String, value: String?): String {
        check(!value.isNullOrBlank()) { "$paramName is null or blank" }
        return value
    }

    fun validateEmailFormat(email: String?): String {
        val emailValidator = EmailValidator.getInstance()
        val notBlankEmail = validateNotBlank("email", email)
        check(emailValidator.isValid(notBlankEmail)) { "email is not valid" }
        return notBlankEmail
    }
}
