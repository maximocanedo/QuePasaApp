package frgp.utn.edu.ar.quepasa.data.models.geo

import kotlinx.serialization.Serializable

@Serializable
data class Neighbourhood(
    val id: Long,
    val name: String,
    val city: City,
    val active: Boolean
)
