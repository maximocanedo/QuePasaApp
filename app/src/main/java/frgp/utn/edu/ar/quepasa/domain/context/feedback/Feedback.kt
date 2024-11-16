package frgp.utn.edu.ar.quepasa.domain.context.feedback

data class Feedback(
    val field: String,
    val message: String
) {
    fun of(field: String): String? = if(this.field.equals(field)) message else null
}