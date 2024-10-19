package frgp.utn.edu.ar.quepasa.data.model


import com.google.type.DateTime
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
@Serializable
data class Post (
    var id: Int? = null,
    @Contextual
    var owner: User? = null,
    var audience: Audience = Audience.NEIGHBORHOOD,
    var title: String? = null,
    var subtype: PostSubtype? = null,
    var description: String? = null,
    @Contextual
    var neighbourhood: Neighbourhood? = null,
    @Contextual
    var timestamp: DateTime? = null,
    var tags: String? = null,
    var isActive: Boolean = true,
    @Contextual
    val votes: VoteCount? = null,
)












