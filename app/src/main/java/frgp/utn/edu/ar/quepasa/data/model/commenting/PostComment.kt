package frgp.utn.edu.ar.quepasa.data.model.commenting

import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.User
import java.sql.Timestamp

data class PostComment(
    override var id: Int,
    override var content: String,
    override var author: User? = null,
    override var timestamp: Timestamp,
    override var active: Boolean = true,
    val post: Post,
): AbsComment()