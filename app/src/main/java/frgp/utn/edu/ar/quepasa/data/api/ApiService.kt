package frgp.utn.edu.ar.quepasa.data.api

import frgp.utn.edu.ar.quepasa.data.models.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUsers(): Call<List<User>>


}