package frgp.utn.edu.ar.quepasa.data.source.remote

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import frgp.utn.edu.ar.quepasa.data.AuthInterceptor
import frgp.utn.edu.ar.quepasa.data.model.utils.LocalDateTimeDeserializer
import frgp.utn.edu.ar.quepasa.data.source.remote.geo.NeighbourhoodService
import frgp.utn.edu.ar.quepasa.data.source.remote.media.EventPictureService
import frgp.utn.edu.ar.quepasa.data.source.remote.media.PostPictureService
import frgp.utn.edu.ar.quepasa.domain.repository.ApiResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        val gson = gsonBuilder.create()
        return Retrofit.Builder()
            .baseUrl("http://canedo.com.ar:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun provideNeighbourhoodService(retrofit: Retrofit): NeighbourhoodService {
        return retrofit
            .create(NeighbourhoodService::class.java)
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
    fun providePostPictureService(retrofit: Retrofit): PostPictureService {
        return retrofit
            .create(PostPictureService::class.java)
    }

    @Provides
    @Singleton
    fun provideTrendService(retrofit: Retrofit): TrendService {
        return retrofit.create(TrendService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventService(retrofit: Retrofit): EventService {
        return retrofit.create(EventService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventPictureService(retrofit: Retrofit): EventPictureService {
        return retrofit.create(EventPictureService::class.java)
    }
}