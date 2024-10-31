package frgp.utn.edu.ar.quepasa.data.model

data class PostSubtype (
    var id: Int? = null,
    var type: PostType? = null,
    var description: String? = null,
    var isActive: Boolean = true
)
