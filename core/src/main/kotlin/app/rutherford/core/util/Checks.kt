package app.rutherford.core.util

import org.apache.commons.validator.routines.EmailValidator

object Checks {
    fun <T : Any> validateNotNull(paramName: String, value: T?): T {
        return checkNotNull(value) { "NULL_PARAM: $paramName" }
    }

    fun validateNotBlank(paramName: String, value: String?): String {
        check(!value.isNullOrBlank()) { "NULL_OR_BLANK_PARAM: $paramName" }
        return value
    }

    fun validateEmailFormat(email: String?): String {
        val emailValidator = EmailValidator.getInstance()
        val notBlankEmail = validateNotBlank("email", email)
        check(emailValidator.isValid(notBlankEmail)) { "MALFORMED_EMAIL" }
        return notBlankEmail
    }
}
