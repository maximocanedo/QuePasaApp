package frgp.utn.edu.ar.quepasa.presentation.viewmodel.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.dto.request.EventCreateRequest
import frgp.utn.edu.ar.quepasa.data.dto.request.EventPatchRequest
import frgp.utn.edu.ar.quepasa.data.dto.response.VoteCount
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.enums.Audience
import frgp.utn.edu.ar.quepasa.data.model.enums.EventCategory
import frgp.utn.edu.ar.quepasa.domain.repository.EventRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
import quepasa.api.validators.events.EventAddressValidator
import quepasa.api.validators.events.EventDateValidator
import quepasa.api.validators.events.EventDescriptionValidator
import quepasa.api.validators.events.EventTitleValidator
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    // Data fields Variables
    private val titleMutable = MutableStateFlow("")
    fun setTitle(x: String) {
        titleMutable.value = x
    }

    val title = titleMutable.asStateFlow()

    private val descriptionMutable = MutableStateFlow("")
    fun setDescription(x: String) {
        descriptionMutable.value = x
    }

    val description = descriptionMutable.asStateFlow()

    private val addressMutable = MutableStateFlow("")
    fun setAddress(x: String) {
        addressMutable.value = x
    }

    val address = addressMutable.asStateFlow()

    private val startMutable = MutableStateFlow(LocalDateTime.now())
    fun setStart(x: LocalDateTime) {
        startMutable.value = x
    }

    val start = startMutable.asStateFlow()

    private val endMutable = MutableStateFlow(LocalDateTime.now())
    fun setEnd(x: LocalDateTime) {
        endMutable.value = x
    }

    val end = endMutable.asStateFlow()

    private val neighbourhoodsMutable = MutableStateFlow(emptySet<Long>())
    fun setNeighbourhoods(x: Set<Long>) {
        neighbourhoodsMutable.value = x
    }

    val neighbourhoods = neighbourhoodsMutable.asStateFlow()

    private val neighbourhoodsNamesMutable = MutableStateFlow(emptyList<String>())
    fun setNeighbourhoodsNames(x: List<String>) {
        neighbourhoodsNamesMutable.value = x
    }

    val neighbourhoodsNames = neighbourhoodsNamesMutable.asStateFlow()

    private val categoryMutable = MutableStateFlow("EDUCATIVE")
    fun setCategory(x: String) {
        categoryMutable.value = x
    }

    val category = categoryMutable.asStateFlow()

    private val audienceMutable = MutableStateFlow("PUBLIC")
    fun setAudience(x: String) {
        audienceMutable.value = x
    }

    val audience = audienceMutable.asStateFlow()

    // Variables
    private val _events = MutableStateFlow<Page<Event>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val events: MutableStateFlow<Page<Event>> get() = _events

    private val _event = MutableStateFlow<Event?>(null)
    val event: MutableStateFlow<Event?> get() = _event

    private val _votes = MutableStateFlow<VoteCount?>(null)
    val votes: MutableStateFlow<VoteCount?> get() = _votes

    private val _comments = MutableStateFlow<Page<Comment>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val comments: MutableStateFlow<Page<Comment>> get() = _comments

    private val _comment = MutableStateFlow<Comment?>(null)
    val comment: MutableStateFlow<Comment?> get() = _comment

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: MutableStateFlow<String?> get() = _errorMessage

    private val _startDateErrorMessage = MutableStateFlow<String?>(null)
    val startDateErrorMessage: MutableStateFlow<String?> get() = _startDateErrorMessage
    fun setStartDateErrorMessage(x: String) {
        _startDateErrorMessage.value = x
    }

    private val _endDateErrorMessage = MutableStateFlow<String?>(null)
    val endDateErrorMessage: MutableStateFlow<String?> get() = _endDateErrorMessage
    fun setEndDateErrorMessage(x: String) {
        _endDateErrorMessage.value = x
    }

    /** Valid **/
    private val titleIsValidMutable = MutableStateFlow(false)
    val titleIsValid = titleIsValidMutable.asStateFlow()
    fun setTitleIsValid(x: Boolean) {
        titleIsValidMutable.value = x
    }

    private val descriptionIsValidMutable = MutableStateFlow(false)
    val descriptionIsValid = descriptionIsValidMutable.asStateFlow()
    fun setDescriptionIsValid(x: Boolean) {
        descriptionIsValidMutable.value = x
    }

    private val addressIsValidMutable = MutableStateFlow(false)
    val addressIsValid = addressIsValidMutable.asStateFlow()
    fun setAddressIsValid(x: Boolean) {
        addressIsValidMutable.value = x
    }

    private val startDateIsValidMutable = MutableStateFlow(false)
    val startDateIsValid = startDateIsValidMutable.asStateFlow()
    fun setStartDateIsValid(x: Boolean) {
        startDateIsValidMutable.value = x
    }

    private val endDateIsValidMutable = MutableStateFlow(false)
    val endDateIsValid = endDateIsValidMutable.asStateFlow()
    fun setEndDateIsValid(x: Boolean) {
        endDateIsValidMutable.value = x
    }

    private val neighbourhoodIsValidMutable = MutableStateFlow(false)
    val neighbourhoodIsValid = neighbourhoodIsValidMutable.asStateFlow()
    fun setNeighbourhoodIsValid(x: Boolean) {
        neighbourhoodIsValidMutable.value = x
    }

    init {
        viewModelScope.launch {
            getEvents(0, 10, true)
        }
    }

    /** GET **/
    suspend fun getEvents(page: Int, size: Int, active: Boolean) {
        try {
            val events = repository.getEvents(page = page, size = size, active = active)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEvents(query: String, page: Int, size: Int, activeOnly: Boolean, sort: String) {
        try {
            val events = repository.getEvents(query, page, size, activeOnly, sort)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEventById(id: Int) {
        try {
            val event = repository.getEventById(id)
            _event.value = event
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEventsByUser(username: String, page: Int, size: Int) {
        try {
            val events = repository.getEventsByUser(username, page, size)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEventsByAuthUser(page: Int, size: Int) {
        try {
            val events = repository.getEventsByAuthUser(page, size)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEventsByAudience(audience: Audience, page: Int, size: Int) {
        try {
            val events = repository.getEventsByAudience(audience, page, size)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getEventsByCategory(category: EventCategory, page: Int, size: Int) {
        try {
            val events = repository.getEventsByCategory(category, page, size)
            _events.value = events
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    /** POST **/
    suspend fun createEvent(event: EventCreateRequest) {
        try {
            val newEvent = repository.createEvent(event)
            _event.value = newEvent
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun rsvpEvent(eventId: Int) {
        try {
            val event = repository.rvspEvent(eventId)
            _event.value = event
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun addNeighbourhoodToEvent(eventId: Int, neighbourhoodId: Int) {
        try {
            val event = repository.addNeighbourhoodToEvent(eventId, neighbourhoodId)
            _event.value = event
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    /** PATCH **/
    suspend fun updateEvent(eventId: Int, event: EventPatchRequest) {
        try {
            val updatedEvent = repository.updateEvent(eventId, event)
            _event.value = updatedEvent
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    /** DELETE **/
    suspend fun deleteEvent(eventId: Int) {
        try {
            val deletedEvent = repository.deleteEvent(eventId)
            _event.value = deletedEvent
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun removeNeighbourhoodFromEvent(eventId: Int, neighbourhoodId: Int) {
        try {
            val event = repository.removeNeighbourhoodFromEvent(eventId, neighbourhoodId)
            _event.value = event
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    /** VOTES **/
    suspend fun getVotes(eventId: Int) {
        try {
            val newVotes = repository.getVotes(eventId)
            _votes.value = newVotes
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun upVote(eventId: Int) {
        try {
            val votes = repository.upVote(eventId)
            _votes.value = votes
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun downVote(eventId: Int) {
        try {
            val votes = repository.downVote(eventId)
            _votes.value = votes
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    /** COMMENTS **/
    suspend fun getComments(eventId: UUID, page: Int, size: Int) {
        try {
            val comments = repository.getComments(eventId, page, size)
            _comments.value = comments
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun comment(eventId: UUID, content: String) {
        try {
            val comment = repository.comment(eventId, content)
            _comment.value = comment
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun titleValidator(title: String): EventTitleValidator {
        return EventTitleValidator(title).isNotBlank().meetsLimits()
    }

    fun descriptionValidator(description: String): EventDescriptionValidator {
        return EventDescriptionValidator(description).isNotBlank().meetsLimits()
    }

    fun addressValidator(address: String): EventAddressValidator {
        return EventAddressValidator(address).isNotBlank()
    }
    fun startValidator(start: LocalDateTime): EventDateValidator {
        return EventDateValidator(start).isNotPast().isBefore(end.value)
    }

    fun endValidator(end: LocalDateTime): EventDateValidator {
        return EventDateValidator(end).isNotPast().isAfterStartDate(start.value)
    }

    fun neighbourhoodValidator(neighbourhoods: Set<Long>): Boolean {
        return neighbourhoods.isNotEmpty()
    }

    fun isEventValid(): Boolean {
        return titleIsValid.value
                && descriptionIsValid.value
                && addressIsValid.value
                && startDateIsValid.value
                && endDateIsValid.value
                && neighbourhoodIsValid.value
    }

    fun resetEvent() {
        titleMutable.value = ""
        descriptionMutable.value = ""
        addressMutable.value = ""
        startMutable.value = LocalDateTime.now()
        endMutable.value = LocalDateTime.now()
        neighbourhoodsMutable.value = emptySet()
        categoryMutable.value = "EDUCATIVE"
        audienceMutable.value = "PUBLIC"
    }
}