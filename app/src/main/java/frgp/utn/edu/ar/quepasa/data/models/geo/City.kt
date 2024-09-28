package frgp.utn.edu.ar.quepasa.data.models.geo

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Long,
    val name: String,
    val subdivision: SubnationalDivision,
    val active: Boolean
)
