package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.EventPatchRequest
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @GET("events")
    suspend fun getEvents(@Query("q") q: String, @Query("page") page: Int, @Query("size") size: Int, @Query("active") active: Boolean, @Query("sort") sort: String): Response<Page<Event>>
    @GET("events/{id}")
    suspend fun getEventById(@Query("id") id: Int): Response<Event>
    @GET("events/{username}")
    suspend fun getEventsByUser(@Query("username") username: String, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Event>>
    @GET("events/me")
    suspend fun getEventsByAuthUser(@Query("page") page: Int, @Query("size") size: Int): Response<Page<Event>>

    @POST("events")
    suspend fun createEvent(@Body event: EventCreateRequest): Response<Event>
    @POST("events/{eventId}/rsvp")
    suspend fun rsvpEvent(@Path("eventId") eventId: Int): Response<Event>
    @POST("events/{eventId}/neighbourhood/{neighbourhoodId}")
    suspend fun addNeighbourhoodToEvent(@Path("eventId") eventId: Int, @Path("neighbourhoodId") neighbourhoodId: Int): Response<Event>

    @PATCH("events/{id}")
    suspend fun updateEvent(@Path("id") id: Int, @Body event: EventPatchRequest): Response<Event>

    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: Int): Response<Event>
    @DELETE("events/{eventId}/neighbourhood/{neighbourhoodId}")
    suspend fun removeNeighbourhoodFromEvent(@Path("eventId") eventId: Int, @Path("neighbourhoodId") neighbourhoodId: Int): Response<Event>

    /** SECCION VOTOS **/
    @GET("events/{eventId}/votes")
    suspend fun getVotes(@Path("eventId") eventId: Int): Response<Int>
    @POST("events/{eventId}/votes/up")
    suspend fun upVote(@Path("eventId") eventId: Int): Response<Int>
    @POST("events/{eventId}/votes/down")
    suspend fun downVote(@Path("eventId") eventId: Int): Response<Int>
}