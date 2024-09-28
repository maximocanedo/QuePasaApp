package frgp.utn.edu.ar.quepasa.data.models.media

import frgp.utn.edu.ar.quepasa.data.models.User
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Picture(
    val id: String,
    val description: String,
    val active: Boolean,
    val owner: User?
)
