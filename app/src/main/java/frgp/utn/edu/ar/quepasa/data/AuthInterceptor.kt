package frgp.utn.edu.ar.quepasa.data

import android.content.Context
import frgp.utn.edu.ar.quepasa.data.source.remote.getAuthToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getAuthToken(context) ?: ""
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}