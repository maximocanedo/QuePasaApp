package frgp.utn.edu.ar.quepasa.utils.audience

import frgp.utn.edu.ar.quepasa.data.model.enums.Audience

private val audienceTranslations = mapOf(
    Audience.PUBLIC to "Público",
    Audience.NATIONAL to "Nacional",
    Audience.SUBNATIONAL to "Subnacional",
    Audience.CITY to "Ciudad",
    Audience.NEIGHBORHOOD to "Barrio"
)

fun audiencesToSpanish(): List<String> {
    return audienceTranslations.values.toList()
}

fun audienceToSpanish(audience: String): String {
    return audienceTranslations.entries.find { it.key == Audience.valueOf(audience) }?.value ?: throw IllegalArgumentException("Non existing audience")
}

fun audienceToEnglish(audience: String): Audience {
    return audienceTranslations.entries.find { it.value == audience }?.key ?: throw IllegalArgumentException("Non existing audience")
}