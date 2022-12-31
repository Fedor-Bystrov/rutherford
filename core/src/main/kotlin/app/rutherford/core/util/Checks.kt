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
        check(isValidEmail(email)) { "MALFORMED_EMAIL" }
        return email!!
    }

    fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        val emailValidator = EmailValidator.getInstance()
        return emailValidator.isValid(email)
    }
}
