package frgp.utn.edu.ar.quepasa.data.dto

import quepasa.api.exceptions.ValidationError


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T?) : ApiResponse<T?>()
    data class ValidationError(val details: quepasa.api.exceptions.ValidationError) : ApiResponse<Nothing>()
    data class Error(val exception: Fail) : ApiResponse<Nothing>()
}