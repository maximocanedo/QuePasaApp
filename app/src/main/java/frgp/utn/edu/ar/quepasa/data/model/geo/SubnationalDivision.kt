package frgp.utn.edu.ar.quepasa.data.model.geo

import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination

data class SubnationalDivision(
    val iso3: String,
    val label: String,
    val denomination: SubnationalDivisionDenomination,
    val country: Country,
    val active: Boolean
)
