package frgp.utn.edu.ar.quepasa.data.source.remote

import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.EventPatchRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.EventRvsp
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import org.w3c.dom.Comment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface EventService {
    /** SECCION GETs **/
    @GET("events")
    suspend fun getEvents(@Query("q") q: String, @Query("page") page: Int, @Query("size") size: Int, @Query("active") active: Boolean, @Query("sort") sort: String): Response<Page<Event>>

    @Headers("Accept: application/json")
    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: UUID): Response<Event>

    @GET("events/{username}")
    suspend fun getEventsByUser(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<Page<Event>>

    @GET("events/me")
    suspend fun getEventsByAuthUser(@Query("page") page: Int, @Query("size") size: Int): Response<Page<Event>>

    @GET("events/audience/{audience}")
    suspend fun getEventsByAudience(@Path("audience") audience: Audience, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Event>>

    @GET("events/eventCategory/{category}")
    suspend fun getEventsByCategory(@Path("category") category: EventCategory, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Event>>

    /** SECCION POSTs **/
    @POST("events")
    suspend fun createEvent(@Body event: EventCreateRequest): Response<Event>

    @POST("events/{eventId}/rsvp")
    suspend fun rsvpEvent(@Path("eventId") eventId: UUID): Response<EventRvsp>

    @POST("events/{eventId}/neighbourhood/{neighbourhoodId}")
    suspend fun addNeighbourhoodToEvent(
        @Path("eventId") eventId: UUID,
        @Path("neighbourhoodId") neighbourhoodId: Int
    ): Response<Event>

    /** SECCION PATCHs **/
    @PATCH("events/{id}")
    suspend fun updateEvent(@Path("id") id: UUID, @Body event: EventPatchRequest): Response<Event>

    /** SECCION DELETEs **/
    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: UUID): Response<Event>

    @DELETE("events/{eventId}/neighbourhood/{neighbourhoodId}")
    suspend fun removeNeighbourhoodFromEvent(
        @Path("eventId") eventId: UUID,
        @Path("neighbourhoodId") neighbourhoodId: Int
    ): Response<Event>

    /** SECCION VOTOS **/
    @GET("events/{eventId}/votes")
    suspend fun getVotes(@Path("eventId") eventId: UUID): Response<VoteCount>
    @POST("events/{eventId}/votes/up")
    suspend fun upVote(@Path("eventId") eventId: UUID): Response<VoteCount>
    @POST("events/{eventId}/votes/down")
    suspend fun downVote(@Path("eventId") eventId: UUID): Response<VoteCount>

    /** SECCION COMENTARIOS **/
    @GET("events/{eventId}/comments")
    suspend fun getComments(@Path("eventId") eventId: UUID, @Query("page") page: Int, @Query("size") size: Int): Response<Page<Comment>>
    @POST("events/{eventId}/comments")
    suspend fun comment(@Path("eventId") eventId: UUID, @Body content: String): Response<Comment>
}