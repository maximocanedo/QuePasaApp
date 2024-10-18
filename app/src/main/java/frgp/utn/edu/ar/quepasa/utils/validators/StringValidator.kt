package frgp.utn.edu.ar.quepasa.utils.validators

class StringValidator(value: String, field: String): Validator<String>(value, field) {

    fun notEmpty(): StringValidator {
        if(getValue().isBlank())
            super.invalidate("Este campo no puede estar vacío. ")
        return this
    }

}