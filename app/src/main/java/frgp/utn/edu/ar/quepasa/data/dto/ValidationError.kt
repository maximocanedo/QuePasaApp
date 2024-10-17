package frgp.utn.edu.ar.quepasa.data.dto

data class ValidationError(
    val field: String,
    val errors: Array<String>
)