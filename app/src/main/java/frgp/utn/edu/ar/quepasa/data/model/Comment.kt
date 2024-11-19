package frgp.utn.edu.ar.quepasa.data.model

import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import java.sql.Timestamp
import java.util.UUID

data class Comment(
    var id: UUID,
    var content: String,
    var author: User? = null,
    var timestamp: Timestamp,
    var active: Boolean = true,
    var votes: VoteCount? = null
)
