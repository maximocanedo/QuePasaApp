package frgp.utn.edu.ar.quepasa.utils.validators.users

import frgp.utn.edu.ar.quepasa.domain.repository.UserRepository
import frgp.utn.edu.ar.quepasa.utils.validators.Validator
import java.util.regex.Matcher
import java.util.regex.Pattern


class UsernameValidator(private val value: String): Validator<String>(value, "username") {

    suspend fun isAvailable(repository: UserRepository): UsernameValidator {
        if(!repository.checkUsernameAvailability(getValue())) {
            super.invalidate("Este nombre de usuario no está disponible. ");
        }
        return this;
    }

    fun meetsMinimumLength(): UsernameValidator {
        if (getValue().length < 4) super.invalidate("Debe tener al menos cuatro caracteres. ")
        return this
    }
    fun meetsMaximumLength(): UsernameValidator {
        if (getValue().length > 24) super.invalidate("No debe tener más de 24 caracteres. ")
        return this
    }
    fun doesntHaveIllegalCharacters(): UsernameValidator {
        val p: Pattern = Pattern.compile("^[a-zA-Z0-9._]+$")
        val m: Matcher = p.matcher(getValue())
        if (!m.matches()) super.invalidate("Debe tener únicamente letras, números, guiones bajos y/o puntos. ")
        return this
    }
    fun neitherStartsNorEndsWithDoubleDotsOrUnderscores(): UsernameValidator {
        val p = Pattern.compile("^(?![_.]).*(?<![_.])$")
        val m = p.matcher(getValue())
        if (!m.matches()) super.invalidate("Debe tener únicamente letras, números, guiones bajos y/o puntos. ")
        return this
    }
    fun doesntHaveTwoDotsOrUnderscoresInARow(): UsernameValidator {
        val p = Pattern.compile("^(?!.*[._]{2}).*$")
        val m = p.matcher(getValue())
        if (!m.matches()) super.invalidate("No debe tener dos puntos o guiones bajos seguidos. ")
        return this
    }

}