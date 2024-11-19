package frgp.utn.edu.ar.quepasa.domain.repository

import com.google.gson.JsonParser
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import quepasa.api.exceptions.ValidationError
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.process(ifNull: () -> Unit = {  }): ApiResponse<T?> {
    return ApiResponseHandler().getResponse(this)
}

class ApiResponseHandler {

    fun <T> getResponse(response: Response<T>): ApiResponse<T?> {
        try {
            if(response.isSuccessful) {
                return ApiResponse.Success(response.body());
            } else if(response.code() == 403) {
                return ApiResponse.Error(Fail(message = "Recurso prohibido. ", status = response.code()))
            } else {
                val error = response.errorBody()?.string();
                val jsonA = JsonParser.parseString(error)
                val json = jsonA.asJsonObject
                if(json.has("message")) {
                    return ApiResponse.Error(Fail(message = json.get("message").asString, status = response.code()))
                } else if(json.has("field") || json.has("errors")) {
                    return ApiResponse.ValidationError(ValidationError(json.get("field").asString, json.get("errors").asJsonArray.map { it.asString }.toTypedArray().toMutableSet()))
                } else
                    return ApiResponse.Error(Fail(message = "Error desconocido. ", status = response.code()))
            }
        } catch(e: IOException) {
            return ApiResponse.Error(Fail(message = "Error desconocido. ", status = 0))
        }
    }

}