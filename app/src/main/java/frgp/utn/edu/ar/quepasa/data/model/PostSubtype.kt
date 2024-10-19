package frgp.utn.edu.ar.quepasa.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PostSubtype (
    var id: Int? = null,
    @Contextual
    var type: PostType? = null,
    var description: String? = null,
    var isActive: Boolean = true
)
