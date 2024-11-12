package frgp.utn.edu.ar.quepasa.utils.events

import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory

private val categoryTranslations = mapOf(
    EventCategory.EDUCATIVE to "Educación",
    EventCategory.FAIR to "Feria",
    EventCategory.PARTY to "Fiesta",
    EventCategory.OPERA to "Ópera",
    EventCategory.CINEMA to "Cine",
    EventCategory.EMERGENCY to "Emergencia",
    EventCategory.MEDICAL to "Médico",
    EventCategory.FOR_PETS to "Mascotas",
    EventCategory.SPORTIVE to "Deportivo",
    EventCategory.HOA_MEETING to "Reunión de vecinos",
    EventCategory.YARD_SALE to "Venta de garaje",
    EventCategory.TOURISTIC to "Turismo",
    EventCategory.CONCERT to "Concierto",
    EventCategory.THEATRE to "Teatro"
)

fun categoriesToSpanish(): List<String> {
    return categoryTranslations.values.toList()
}

fun categoryToSpanish(category: String): String {
    return categoryTranslations.entries.find { it.key == EventCategory.valueOf(category) }?.value
        ?: throw IllegalArgumentException("Non existing category")
}

fun categoryToEnglish(category: String): EventCategory {
    return categoryTranslations.entries.find { it.value == category }?.key
        ?: throw IllegalArgumentException("Non existing category")
}