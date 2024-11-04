package frgp.utn.edu.ar.quepasa.utils.pagination

data class Page<T>(
    val content: List<T>,
    val totalElements: Int,
    val totalPages: Int,
    val pageNumber: Int
)