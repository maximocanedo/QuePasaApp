package frgp.utn.edu.ar.quepasa.data.models.geo

import kotlinx.serialization.Serializable

enum class SubnationalDivisionDenomination {
    /**
     * Provincia
     */
    PROVINCE,
    /**
     * Estado
     */
    STATE,
    /**
     * Departamento
     */
    DEPARTMENT,
    /**
     * Región
     */
    REGION,
    /**
     * Territorio
     */
    TERRITORY,
    /**
     * Municipalidad
     */
    MUNICIPALITY,
    /**
     * Cantón
     */
    CANTON,
    /**
     * Distrito
     */
    DISTRICT,
    /**
     * Parroquia
     */
    PARISH,
    /**
     * Emirato
     */
    EMIRATE,
    /**
     * Condado
     */
    COUNTY,
    /**
     * Ciudad (Reservado para ciudades especiales, como C.A.B.A. o Washington D.C.)
     */
    CITY,
    /**
     * Comuna
     */
    COMMUNE,
    /**
     * Entidad
     */
    ENTITY,
    /**
     * Nación (Para naciones constitutivas del Reino Unido, Dinamarca, o de los Países Bajos).
     */
    COUNTRY
}