package frgp.utn.edu.ar.quepasa.data.model

data class PostSubtype (
    var id: Int,
    var type: PostType,
    var description: String,
    var isActive: Boolean = true
)
