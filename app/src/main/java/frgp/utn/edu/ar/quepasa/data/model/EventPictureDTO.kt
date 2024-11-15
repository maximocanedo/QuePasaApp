package frgp.utn.edu.ar.quepasa.data.model

import coil3.Bitmap
import java.util.UUID

data class EventPictureDTO(
    var eventId: UUID? = null,
    var bitmap: Bitmap? = null
)