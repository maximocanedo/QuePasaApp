package frgp.utn.edu.ar.quepasa.data.model.media

import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

data class EventPicture(
    override var id: UUID,
    override var description: String,
    override var active: Boolean,
    override var mediaType: String,
    override var uploadedAt: Timestamp,
    override var owner: User,
    var event: Event? = null
) : AbsPicture()
