package frgp.utn.edu.ar.quepasa.domain.repository

import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.EventPatchRequest
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.data.source.remote.EventService
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import org.w3c.dom.Comment
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
    suspend fun getEvents(query: String, page: Int, size: Int, active: Boolean, sort: String): Page<Event> =
        handleResponse { eventService.getEvents(query, page, size, active, sort) }
    suspend fun getEventById(id: Int): Event =
        handleResponse { eventService.getEventById(id) }
    suspend fun getEventsByUser(username: String, page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByUser(username, page, size) }
    suspend fun getEventsByAuthUser(page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByAuthUser(page, size) }
    suspend fun getEventsByAudience(audience: Audience, page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByAudience(audience, page, size) }
    suspend fun getEventsByCategory(category: EventCategory, page: Int, size: Int): Page<Event> =
        handleResponse { eventService.getEventsByCategory(category, page, size) }

    /** POSTs **/
    suspend fun createEvent(event: EventCreateRequest): Event =
        handleResponse { eventService.createEvent(event) }
    suspend fun rvspEvent(eventId: Int): Event =
        handleResponse { eventService.rsvpEvent(eventId) }
    suspend fun addNeighbourhoodToEvent(eventId: Int, neighbourhoodId: Int): Event =
        handleResponse { eventService.addNeighbourhoodToEvent(eventId, neighbourhoodId) }

    /** PATCHs **/
    suspend fun updateEvent(id: Int, event: EventPatchRequest): Event =
        handleResponse { eventService.updateEvent(id, event) }

    /** DELETEs **/
    suspend fun deleteEvent(id: Int): Event =
        handleResponse { eventService.deleteEvent(id) }
    suspend fun removeNeighbourhoodFromEvent(eventId: Int, neighbourhoodId: Int): Event =
        handleResponse { eventService.removeNeighbourhoodFromEvent(eventId, neighbourhoodId) }

    /** VOTES **/
    suspend fun getVotes(eventId: Int): Int =
        handleResponse { eventService.getVotes(eventId) }
    suspend fun upVote(eventId: Int): Int =
        handleResponse { eventService.upVote(eventId) }
    suspend fun downVote(eventId: Int): Int =
        handleResponse { eventService.downVote(eventId) }

    /** COMMENTS **/
    suspend fun getComments(eventId: UUID, page: Int, size: Int): Page<Comment> =
        handleResponse { eventService.getComments(eventId, page, size) }
    suspend fun comment(eventId: UUID, content: String): Comment =
        handleResponse { eventService.comment(eventId, content) }
}