package frgp.utn.edu.ar.quepasa.data.models.auth


import com.google.type.DateTime
import frgp.utn.edu.ar.quepasa.data.model.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
@Serializable
data class SingleUseRequest (
    var id: Int? = null,
    var hash: String? = null,
    @Contextual
    var action: SingleUseRequestAction? = null,
    @Contextual
    var user: User? = null,
    @Contextual
    var requested: DateTime? = null,
    var isActive: Boolean = false,
    val isExpired: Boolean
)
