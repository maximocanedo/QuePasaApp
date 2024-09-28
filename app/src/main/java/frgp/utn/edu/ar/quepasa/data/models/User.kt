package frgp.utn.edu.ar.quepasa.data.models

import frgp.utn.edu.ar.quepasa.data.models.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.models.media.Picture
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val name: String,
    // val phone: Set<Phone>,
    // val mail: Set<Mail>,
    val address: String,
    val neighbourhood: Neighbourhood,
    val profilePicture: Picture,
    val password: String?,
    // val role: Role,
    val active: Boolean
)
