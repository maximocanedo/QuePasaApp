package frgp.utn.edu.ar.quepasa.domain.repository

import com.google.gson.JsonParser
import frgp.utn.edu.ar.quepasa.data.dto.ApiResponse
import frgp.utn.edu.ar.quepasa.data.dto.Fail
import frgp.utn.edu.ar.quepasa.data.dto.ValidationError
import retrofit2.Response
import java.io.IOException

class ApiResponseHandler {

    fun <T> getResponse(response: Response<T>): ApiResponse<T?> {
        try {
            if(response.isSuccessful) {
                return ApiResponse.Success(response.body());
            } else {
                val error = response.errorBody()?.toString();
                val json = JsonParser.parseString(error).asJsonObject
                if(json.has("message")) {
                    return ApiResponse.Error(Fail(message = json.get("message").asString, status = response.code()))
                } else if(json.has("field") || json.has("errors")) {
                    return ApiResponse.ValidationError(ValidationError(field = json.get("field").asString, errors = json.get("errors").asJsonArray.map { it.asString }.toTypedArray()))
                } else
                    return ApiResponse.Error(Fail(message = "Error desconocido. ", status = response.code()))
            }
        } catch(e: IOException) {
            return ApiResponse.Error(Fail(message = "Error desconocido. ", status = 0))
        }
    }

}