package frgp.utn.edu.ar.quepasa.data.dto

import quepasa.api.exceptions.ValidationError


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T?) : ApiResponse<T?>()
    data class ValidationError(val details: quepasa.api.exceptions.ValidationError) : ApiResponse<Nothing>()
    data class Error(val exception: Fail) : ApiResponse<Nothing>()
}

fun <T> ApiResponse<T>.handle(
    onSuccess: (T) -> Unit,
    onValidationError: (ValidationError) -> Unit,
    onError: (Fail) -> Unit
): ApiResponse<T> {
    if(this is ApiResponse.Success<*> && this.data != null) onSuccess(this.data as T)
    else if(this is ApiResponse.ValidationError) onValidationError(this.details)
    else if(this is ApiResponse.Error) onError(this.exception)
    return this
}

fun <T> ApiResponse<T>.handle(f: (T) -> Unit): ApiResponse<T>
    = this.handle({
        if(it != null) f(it!!)
        return@handle
}, {  }, {  })
fun <T> ApiResponse<T>.onValidationError(f: (ValidationError) -> Unit): ApiResponse<T>
        = this.handle({  }, f, {  })
fun <T> ApiResponse<T>.error(f: (Fail) -> Unit): ApiResponse<T>
        = this.handle({  }, {  }, f)