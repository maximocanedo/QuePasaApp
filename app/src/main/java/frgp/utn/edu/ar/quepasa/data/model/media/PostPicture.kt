package frgp.utn.edu.ar.quepasa.data.model.media

import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp
import java.util.UUID

data class PostPicture (
        override var id: UUID,
        override var description: String,
        override var active: Boolean,
        override var mediaType: String,
        override var uploadedAt: Timestamp,
        override var owner: User,
        var post: Post? = null
) : AbsPicture()

