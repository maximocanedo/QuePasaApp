package frgp.utn.edu.ar.quepasa.data.dto

import javax.annotation.Nullable

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T?) : ApiResponse<T?>()
    data class ValidationError(val details: frgp.utn.edu.ar.quepasa.data.dto.ValidationError) : ApiResponse<Nothing>()
    data class Error(val exception: Fail) : ApiResponse<Nothing>()
}