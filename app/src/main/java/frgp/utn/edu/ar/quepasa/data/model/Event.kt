package frgp.utn.edu.ar.quepasa.data.model


import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.UUID
@Serializable
data class Event (
    @Contextual
    var id: UUID? = null,
    var title: String? = null,
    var description: String? = null,
    @Contextual
    var owner: User? = null,
    var address: String? = null,
    @Contextual
    var start: LocalDateTime? = null,
    @Contextual
    var end: LocalDateTime? = null,
    var category: EventCategory? = null,
    @Contextual
    var createdAt: Timestamp? = null,
    var audience: Audience? = null,
    var isActive: Boolean = false,
    @Contextual
    var neighbourhoods: Set<Neighbourhood>? = null,
    @Contextual
    val votes: VoteCount? = null
)







