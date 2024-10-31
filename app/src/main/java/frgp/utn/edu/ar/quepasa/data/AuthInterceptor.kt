package frgp.utn.edu.ar.quepasa.data

import android.content.Context
import frgp.utn.edu.ar.quepasa.data.source.remote.getAuthToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    private val defaultToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXJ0aWFsIjpmYWxzZSwic3ViIjoicm9vdCIsImlhdCI6MTcyODA0OTI0MiwiZXhwIjoyMzM4MDUwNjgyfQ.szj18VgoIbb8RY-N9nUDopXurHr1l7A7KWDom6lBpQY"
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getAuthToken(context) ?: defaultToken
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}