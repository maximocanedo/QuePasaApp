package frgp.utn.edu.ar.quepasa.data.models.geo

import kotlinx.serialization.Serializable

@Serializable
data class SubnationalDivision(
    val iso3: String,
    val label: String,
    val denomination: SubnationalDivisionDenomination,
    val country: Country,
    val active: Boolean
)
