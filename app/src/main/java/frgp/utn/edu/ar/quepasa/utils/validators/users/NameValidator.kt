package frgp.utn.edu.ar.quepasa.utils.validators.users

import frgp.utn.edu.ar.quepasa.utils.validators.Validator
import java.util.regex.Matcher
import java.util.regex.Pattern


class NameValidator(private val value: String): Validator<String>(value, "name") {
    fun validateCompoundNames(): NameValidator {
        val p: Pattern =
            Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ'’-]{2,}( [A-Za-zÁÉÍÓÚáéíóúñÑ'’-]{2,})*$")
        val m: Matcher = p.matcher(getValue())
        if (!m.matches()) super.invalidate("Cada nombre debe tener al menos dos caracteres válidos. ")
        return this
    }
}