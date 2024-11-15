package frgp.utn.edu.ar.quepasa.data.dto.request

import frgp.utn.edu.ar.quepasa.data.model.Post

data class PostCommentDTO (
    val content: String,
    val file: Post
)