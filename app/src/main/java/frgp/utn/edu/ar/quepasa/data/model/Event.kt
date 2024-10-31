package frgp.utn.edu.ar.quepasa.data.model


import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.UUID

data class Event (
    var id: UUID? = null,
    var title: String? = null,
    var description: String? = null,
    var owner: User? = null,
    var address: String? = null,
    var start: LocalDateTime? = null,
    var end: LocalDateTime? = null,
    var category: EventCategory? = null,
    var createdAt: Timestamp? = null,
    var audience: Audience? = null,
    var isActive: Boolean = false,
    var neighbourhoods: Set<Neighbourhood>? = null,
    val votes: VoteCount? = null
)







