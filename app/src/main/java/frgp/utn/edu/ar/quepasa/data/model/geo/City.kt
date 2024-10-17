package frgp.utn.edu.ar.quepasa.data.model.geo

data class City(
    val id: Long,
    val name: String,
    val subdivision: SubnationalDivision,
    val active: Boolean
)
