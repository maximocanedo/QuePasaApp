package frgp.utn.edu.ar.quepasa.data.model.geo

data class Neighbourhood(
    val id: Long,
    val name: String,
    val city: City,
    val active: Boolean
)
