package frgp.utn.edu.ar.quepasa.data.source.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import frgp.utn.edu.ar.quepasa.data.AuthInterceptor
import frgp.utn.edu.ar.quepasa.domain.repository.ApiResponseHandler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory



@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://canedo.com.ar:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AuthService {
        return retrofit
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit
            .create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiResponseHandler(): ApiResponseHandler {
        return ApiResponseHandler()
    }

    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit): PostService {
        return retrofit
            .create(PostService::class.java)
    }

    @Provides
    @Singleton
    fun providePostTypeService(retrofit: Retrofit): PostTypeService {
        return retrofit
            .create(PostTypeService::class.java)
    }

    @Provides
    @Singleton
    fun providePostSubtypeService(retrofit: Retrofit): PostSubtypeService {
        return retrofit
            .create(PostSubtypeService::class.java)
    }

    @Provides
    @Singleton
    fun provideTrendService(retrofit: Retrofit): TrendService {
        return retrofit.create(TrendService::class.java)
    }

}