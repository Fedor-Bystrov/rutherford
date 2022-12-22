package app.rutherford.auth.service

import app.rutherford.auth.exception.PasswordPolicyValidationException
import org.passay.CharacterRule
import org.passay.EnglishCharacterData.Digit
import org.passay.EnglishCharacterData.LowerCase
import org.passay.EnglishCharacterData.UpperCase
import org.passay.LengthRule
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.passay.PropertiesMessageResolver

class PasswordPolicyValidator { // TODO add test
    private val passwordValidator = PasswordValidator(
        PropertiesMessageResolver(),
        LengthRule(6, 50),
        CharacterRule(LowerCase),
        CharacterRule(UpperCase),
        CharacterRule(Digit),
    )

    fun validate(password: String) {
        val result = passwordValidator.validate(PasswordData(password))
        if (!result.isValid) {
            throw PasswordPolicyValidationException(
                passwordValidator.getMessages(result)
            )
        }
    }
}
