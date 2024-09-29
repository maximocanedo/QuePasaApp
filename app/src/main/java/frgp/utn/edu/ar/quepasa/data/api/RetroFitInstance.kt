package frgp.utn.edu.ar.quepasa.data.api

import kotlinx.serialization.json.Json
import retrofit2.Retrofit

object RetroFitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}