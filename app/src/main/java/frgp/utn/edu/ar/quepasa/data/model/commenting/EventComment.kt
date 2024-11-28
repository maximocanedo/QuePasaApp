package frgp.utn.edu.ar.quepasa.data.model.commenting

import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Comment
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

data class EventComment (
    override var id: UUID,
    override var content: String,
    override var author: User? = null,
    override var timestamp: Timestamp,
    override var active: Boolean = true,
    override var votes: VoteCount?,
    val event: Event,
): AbsComment()