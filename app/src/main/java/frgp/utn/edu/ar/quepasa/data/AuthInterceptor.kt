package frgp.utn.edu.ar.quepasa.data

import android.content.Context
import frgp.utn.edu.ar.quepasa.data.source.remote.getAuthToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.HttpMethod

enum class Method {
    GET, POST, HEAD, PATCH, PUT, DELETE, OPTIONS, ALL
}

data class Endpoint(
    val method: Method,
    val uri: String
)

class AuthInterceptor(private val context: Context) : Interceptor {

    private val defaultToken = "eyJhbGciOiJIUzI1NiJ9.eyJwYXJ0aWFsIjpmYWxzZSwic3ViIjoicm9vdCIsImlhdCI6MTcyODA0OTI0MiwiZXhwIjoyMzM4MDUwNjgyfQ.szj18VgoIbb8RY-N9nUDopXurHr1l7A7KWDom6lBpQY"

    private fun getAuthenticationExcludedEndpoints(): Set<Endpoint> {
        return setOf(
            Endpoint(Method.ALL,"/api/login"),
            Endpoint(Method.ALL, "/api/signup"),
            Endpoint(Method.HEAD, "/api/users/*"),
            Endpoint(Method.GET, "/api/countries/*"),
            Endpoint(Method.GET, "/api/countries/*"),
            Endpoint(Method.GET, "/api/cities/*"),
            Endpoint(Method.GET, "/api/neighbourhoods/*"),
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getAuthToken(context) ?: defaultToken
        val request = chain.request().newBuilder();
        val rawPath = chain.request().url.toUri().rawPath
        val isExcluded = getAuthenticationExcludedEndpoints().any { endpoint ->
            val pattern = endpoint.uri.replace("*", ".*")
            val regex = Regex(pattern)
            val urlMatches = regex.matches(rawPath)
            val methodMatches = chain.request().method.equals(endpoint.method.name)
                    || endpoint.method == Method.ALL
            urlMatches && methodMatches
        }
        if (!isExcluded && token.isNotBlank()) {
            request.addHeader("Authorization", "Bearer $token")
        }
        val req = request.build()
        return chain.proceed(req)
    }
}