package frgp.utn.edu.ar.quepasa.data.model

data class EventRvsp(
    var id: Int? = null,
    var event: Event? = null,
    var user: User? = null,
    var confirmed: Boolean = false
)