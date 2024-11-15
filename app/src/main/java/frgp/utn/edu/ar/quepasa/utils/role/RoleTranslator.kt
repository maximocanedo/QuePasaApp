package frgp.utn.edu.ar.quepasa.utils.role

import frgp.utn.edu.ar.quepasa.data.model.enums.Role

private val roleTranslations = mapOf(
    Role.USER to "Usuario",
    Role.NEIGHBOUR to "Vecino",
    Role.CONTRIBUTOR to "Contribuyente",
    Role.ORGANIZATION to "Organización",
    Role.GOVT to "Gubernamental"
)

fun rolesToSpanish(): List<String> {
    return roleTranslations.values.toList()
}

fun rolesToSpanishHigherThan(currentRole: Role): List<String> {
    return roleTranslations
        .filterKeys { it.ordinal > currentRole.ordinal }
        .values
        .toList()
}

fun roleToSpanish(role: String): String {
    return roleTranslations.entries.find { it.key == Role.valueOf(role) }?.value ?: throw IllegalArgumentException("Non existing role")
}

fun roleToEnglish(role: String): Role {
    return roleTranslations.entries.find { it.value == role }?.key ?: throw IllegalArgumentException("Non existing role")
}
