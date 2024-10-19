package frgp.utn.edu.ar.quepasa.data.model.geo

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Neighbourhood(
    val id: Long,
    val name: String,
    @Contextual
    val city: City,
    val active: Boolean
)
