package frgp.utn.edu.ar.quepasa.utils.validators.posts

import frgp.utn.edu.ar.quepasa.utils.validators.Validator

class DescriptionValidator(private val value: String): Validator<String>(value, "description") {
    fun notEmpty(): DescriptionValidator {
        if(getValue().isBlank())
            super.invalidate("Este campo no puede estar vacío. ")
        return this
    }

    fun meetsMinimumLength(): DescriptionValidator {
        if (getValue().length < 3) super.invalidate("Debe tener al menos tres caracteres. ")
        return this
    }
    fun meetsMaximumLength(): DescriptionValidator {
        if (getValue().length > 256) super.invalidate("No debe tener más de 256 caracteres. ")
        return this
    }
}