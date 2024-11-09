package frgp.utn.edu.ar.quepasa.data.model.media

import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

abstract class AbsPicture {
    abstract var id: UUID
    abstract var description: String
    abstract var active: Boolean
    abstract var mediaType: String
    abstract var uploadedAt: Timestamp
    abstract var owner: User
    //abstract var voteCount: VoteCount
}
