package frgp.utn.edu.ar.quepasa.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostType (
    var id: Int? = null,
    var description: String? = null,
    var isActive: Boolean = true
)





