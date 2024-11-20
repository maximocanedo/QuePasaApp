package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.EventPatchRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.EventRvsp
import frgp.utn.edu.ar.quepasa.data.model.commenting.EventComment
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.data.source.remote.EventService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventService: EventService
) {
    private suspend fun <T> handleResponse(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("Error en la respuesta: ${response.message()}")
        }
    }
    /** GETs **/
    suspend fun getEvents(query: String = "", page: Int, size: Int, active: Boolean, sort: String = "title,asc"): Page<Event> =
        handleResponse { eventService.getEvents(query, page, size, active, sort) }
    suspend fun getEventById(id: UUID): Event =
        handleResponse { eventService.getEventById(id) }
    suspend fun getEventsByUser(username: String, page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByUser(username, page, size) }
    suspend fun getEventsByAuthUser(page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByAuthUser(page, size) }
    suspend fun getEventsByAudience(
        audience: Audience,
        page: Int,
        size: Int,
        active: Boolean
    ): Page<Event> =
        handleResponse { eventService.getEventsByAudience(audience, page, size, active) }

    suspend fun getEventsByCategory(
        category: EventCategory,
        page: Int,
        size: Int,
        active: Boolean
    ): Page<Event> =
        handleResponse { eventService.getEventsByCategory(category, page, size, active) }

    suspend fun getEventsByNeighbourhood(
        neighbourhoodId: Long,
        query: String = "",
        page: Int = 0,
        size: Int = 10,
        active: Boolean = true,
        sort: String = "title,asc"
    ): Page<Event> =
        handleResponse {
            eventService.getEventsByNeighbourhood(
                neighbourhoodId,
                query,
                page,
                size,
                active,
                sort
            )
        }

    suspend fun getEventsByNeighbourhoodAndCategory(
        neighbourhoodId: Long,
        category: EventCategory,
        query: String = "",
        page: Int = 0,
        size: Int = 10,
        active: Boolean = true,
        sort: String = "title,asc"
    ): Page<Event> =
        handleResponse {
            eventService.getEventsByNeighbourhoodAndCategory(
                neighbourhoodId,
                category,
                query,
                page,
                size,
                active,
                sort
            )
        }

    /** POSTs **/
    suspend fun createEvent(event: EventCreateRequest): Event =
        handleResponse { eventService.createEvent(event) }
    suspend fun rvspEvent(eventId: UUID): EventRvsp =
        handleResponse { eventService.rsvpEvent(eventId) }
    suspend fun getRvspsByUser(confirmed: Boolean = true): List<EventRvsp> =
        handleResponse { eventService.getRsvpsByUser(confirmed) }

    suspend fun addNeighbourhoodToEvent(eventId: UUID, neighbourhoodId: Int): Event =
        handleResponse { eventService.addNeighbourhoodToEvent(eventId, neighbourhoodId) }

    /** PATCHs **/
    suspend fun updateEvent(id: UUID, event: EventPatchRequest): Event =
        handleResponse { eventService.updateEvent(id, event) }

    /** DELETEs **/
    suspend fun deleteEvent(id: UUID): Event =
        handleResponse { eventService.deleteEvent(id) }

    suspend fun removeNeighbourhoodFromEvent(eventId: UUID, neighbourhoodId: Int): Event =
        handleResponse { eventService.removeNeighbourhoodFromEvent(eventId, neighbourhoodId) }

    /** VOTES **/
    suspend fun getVotes(eventId: UUID): VoteCount =
        handleResponse { eventService.getVotes(eventId) }

    suspend fun upVote(eventId: UUID): VoteCount =
        handleResponse { eventService.upVote(eventId) }

    suspend fun downVote(eventId: UUID): VoteCount =
        handleResponse { eventService.downVote(eventId) }

    /** COMMENTS **/
    suspend fun getComments(eventId: UUID, page: Int, size: Int): Page<EventComment> =
        handleResponse { eventService.getComments(eventId, page, size) }

    suspend fun comment(eventId: UUID, content: String): EventComment =
        handleResponse { eventService.comment(eventId, content) }
}