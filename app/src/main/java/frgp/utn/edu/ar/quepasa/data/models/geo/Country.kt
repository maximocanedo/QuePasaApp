package frgp.utn.edu.ar.quepasa.data.models.geo
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val iso3: String,
    val label: String,
    val active: Boolean
)
