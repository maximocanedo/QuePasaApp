package frgp.utn.edu.ar.quepasa.utils.validators

import frgp.utn.edu.ar.quepasa.data.dto.ValidationError

open class Validator<out T>(
    private val value: T,
    private val field: String
) {
    private var valid: Boolean = true
    private var errors: Set<String> = setOf()

    protected fun invalidate(reason: String) {
        this.errors = this.errors.plus(reason)
        this.valid = false
    }

    fun getField(): String { return this.field }
    fun isValid(): Boolean { return this.valid }
    fun getErrors(): Set<String> { return this.errors }
    protected fun getValue(): T { return this.value }
    fun build(): Validator<T> {
        if(isValid()) {
            this.errors = setOf()
        }
        return this
    }
    fun asValidationError(): ValidationError? {
        if(!isValid()) return ValidationError(getField(), getErrors().toTypedArray())
        return null
    }


}