package frgp.utn.edu.ar.quepasa.utils.request

import frgp.utn.edu.ar.quepasa.data.model.enums.RequestStatus

private val roleRequestTranslationsSingle = mapOf(
    RequestStatus.WAITING to "En espera",
    RequestStatus.APPROVED to "Aprobado",
    RequestStatus.REJECTED to "Rechazado"
)

private val roleRequestTranslationsPlural = mapOf(
    RequestStatus.WAITING to "En espera",
    RequestStatus.APPROVED to "Aprobadas",
    RequestStatus.REJECTED to "Rechazadas"
)

fun roleRequestsSingleToSpanish(): List<String> {
    return roleRequestTranslationsSingle.values.toList()
}

fun roleRequestsPluralToSpanish(): List<String> {
    return roleRequestTranslationsPlural.values.toList()
}