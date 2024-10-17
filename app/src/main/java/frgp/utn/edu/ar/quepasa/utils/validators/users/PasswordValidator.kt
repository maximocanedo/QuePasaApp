package frgp.utn.edu.ar.quepasa.utils.validators.users

import frgp.utn.edu.ar.quepasa.utils.validators.Validator

class PasswordValidator(private val value: String): Validator<String>(value, "password") {

    fun lengthIsEightCharactersOrMore(): PasswordValidator {
        if (getValue().count() <= 8) {
            super.invalidate("Debe tener al menos ocho caracteres.")
        }
        return this
    }
    fun hasOneUpperCaseLetter(): PasswordValidator {
        var hasOneUpperCaseLetter = false
        for (c in getValue().toCharArray()) {
            if (Character.isUpperCase(c)) hasOneUpperCaseLetter = true
        }
        if (!hasOneUpperCaseLetter) super.invalidate("Debe tener al menos una letra mayúscula. ")
        return this
    }
    fun hasOneLowerCaseLetter(): PasswordValidator {
        var hasOneLowerCaseLetter = false
        for (c in getValue().toCharArray()) {
            if (Character.isLowerCase(c)) hasOneLowerCaseLetter = true
        }
        if (!hasOneLowerCaseLetter) super.invalidate("Debe tener al menos una letra mayúscula. ")
        return this
    }
    fun hasOneDigit(): PasswordValidator {
        var hasOneDigit = false
        for (c in getValue().toCharArray()) {
            if (Character.isDigit(c)) hasOneDigit = true
        }
        if (!hasOneDigit) super.invalidate("Debe tener al menos un dígito. ")
        return this
    }
    fun hasOneSpecialCharacter(): PasswordValidator {
        var hasOneSpecialCharacter = false
        for (c in getValue().toCharArray()) {
            if (!Character.isLetterOrDigit(c)) hasOneSpecialCharacter = true
        }
        if (!hasOneSpecialCharacter) super.invalidate("Debe tener al menos un símbolo especial. ")
        return this
    }



}