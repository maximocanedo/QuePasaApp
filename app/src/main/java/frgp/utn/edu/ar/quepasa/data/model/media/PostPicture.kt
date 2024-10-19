package frgp.utn.edu.ar.quepasa.data.model.media;

import frgp.utn.edu.ar.quepasa.data.model.Post;
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PostPicture (
        @Contextual
        var post: Post? =null
)

