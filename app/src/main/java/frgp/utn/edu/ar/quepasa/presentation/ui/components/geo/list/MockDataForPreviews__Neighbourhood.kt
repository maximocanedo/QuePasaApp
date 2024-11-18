package frgp.utn.edu.ar.quepasa.presentation.ui.components.geo.list

import frgp.utn.edu.ar.quepasa.data.model.enums.SubnationalDivisionDenomination
import frgp.utn.edu.ar.quepasa.data.model.geo.City
import frgp.utn.edu.ar.quepasa.data.model.geo.Country
import frgp.utn.edu.ar.quepasa.data.model.geo.Neighbourhood
import frgp.utn.edu.ar.quepasa.data.model.geo.SubnationalDivision

val ARGENTINA = Country(
    iso3 = "ARG",
    label = "Argentina",
    active = true
)
val BUENOS_AIRES_PROVINCE = SubnationalDivision(
    label = "Buenos Aires",
    active = true,
    denomination = SubnationalDivisionDenomination.PROVINCE,
    iso3 = "AR-B",
    country = ARGENTINA
)
val TIGRE = City(
    name = "Tigre",
    id = 34L,
    active = true,
    subdivision = BUENOS_AIRES_PROVINCE
)
val SF = City(
    name = "San Fernando",
    id = 35L,
    active = true,
    subdivision = BUENOS_AIRES_PROVINCE
)
val CLY = City(
    name = "Claypole",
    id = 36L,
    active = true,
    subdivision = BUENOS_AIRES_PROVINCE
)
val BZCO = City(
    name = "Burzaco",
    id = 37L,
    active = true,
    subdivision = BUENOS_AIRES_PROVINCE
)

val StatesMDFP: Set<SubnationalDivision> = setOf(
    BUENOS_AIRES_PROVINCE,
    SubnationalDivision(
        iso3 = "AR-X",
        label = "Chubut",
        country = ARGENTINA,
        denomination = SubnationalDivisionDenomination.PROVINCE,
        active = true
    )
)

val CitiesMDFP: Set<City> = setOf(
    TIGRE, SF, CLY, BZCO,
    City(
        id = 100L,
        name = "San Isidro",
        subdivision = BUENOS_AIRES_PROVINCE,
        active = true
    )
)

val NeighbourhoodsMDFP: Set<Neighbourhood> = setOf(
    Neighbourhood(
        id = 1,
        name = "Tigre Joven",
        city = TIGRE,
        active = true
    ),
    Neighbourhood(
        id = 2,
        name = "Tres Bocas",
        city = TIGRE,
        active = false
    ),
    Neighbourhood(
        id = 3,
        name = "Las Marías",
        city = TIGRE,
        active = true
    ),
    Neighbourhood(
        id = 4,
        name = "Carupá",
        city = SF,
        active = true
    ),
    Neighbourhood(
        id = 5,
        name = "Carupá Vieja",
        city = TIGRE,
        active = true
    ),
    Neighbourhood(
        id = 6,
        name = "Los Tábanos",
        city = TIGRE,
        active = false
    ),
    Neighbourhood(
        id = 7,
        name = "El Detalle",
        city = TIGRE,
        active = true
    ),
    Neighbourhood(
        id = 8,
        name = "San José",
        city = TIGRE,
        active = true
    ),
    Neighbourhood(
        id = 9,
        name = "Don Orione",
        city = CLY,
        active = true
    ),
    Neighbourhood(
        id = 10,
        name = "Esther",
        city = BZCO,
        active = true
    ),
    Neighbourhood(
        id = 11,
        name = "San Lucas",
        city = CLY,
        active = true
    ),
    Neighbourhood(
        id = 12,
        name = "San Pablo y Echagüe",
        city = BZCO,
        active = true
    ),
    Neighbourhood(
        id = 13,
        name = "Sakura",
        city = BZCO,
        active = true
    ),
    Neighbourhood(
        id = 14,
        name = "Burzaco Centro",
        city = BZCO,
        active = true
    )
)