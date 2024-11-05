package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory

data class EventPatchRequest(
    val title: String?,
    val description: String?,
    val address: String?,
    val startDate: String?,
    val endDate: String?,
    val category: EventCategory?,
    val audience: Audience?,
    val pictureId: String?
)
