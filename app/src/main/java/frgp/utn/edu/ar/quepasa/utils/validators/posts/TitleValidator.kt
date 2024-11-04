package frgp.utn.edu.ar.quepasa.utils.validators.posts

import frgp.utn.edu.ar.quepasa.utils.validators.Validator

class TitleValidator(private val value: String): Validator<String>(value, "title") {

    fun notEmpty(): TitleValidator {
        if(getValue().isBlank())
            super.invalidate("Este campo no puede estar vacío. ")
        return this
    }

    fun meetsMinimumLength(): TitleValidator {
        if (getValue().length < 4) super.invalidate("Debe tener al menos cuatro caracteres. ")
        return this
    }
    fun meetsMaximumLength(): TitleValidator {
        if (getValue().length > 60) super.invalidate("No debe tener más de 60 caracteres. ")
        return this
    }
}