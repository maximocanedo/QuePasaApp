package frgp.utn.edu.ar.quepasa.data.model


import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import java.sql.Timestamp

data class Post (
    var id: Int? = null,
    var owner: User? = null,
    var audience: Audience = Audience.NEIGHBORHOOD,
    var title: String? = null,
    var subtype: PostSubtype? = null,
    var description: String? = null,
    var neighbourhood: Neighbourhood? = null,
    var timestamp: Timestamp? = null,
    var tags: String? = null,
    var isActive: Boolean = true,
    val votes: VoteCount? = null,
)












