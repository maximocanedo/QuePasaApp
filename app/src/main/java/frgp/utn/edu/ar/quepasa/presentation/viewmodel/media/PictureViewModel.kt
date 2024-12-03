package frgp.utn.edu.ar.quepasa.presentation.viewmodel.media

import androidx.lifecycle.ViewModel
import coil3.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.EventPictureDTO
import frgp.utn.edu.ar.quepasa.domain.repository.media.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureRepository
) : ViewModel() {
    private val _pictures = MutableStateFlow<List<Bitmap?>>(emptyList())
    val pictures = _pictures.asStateFlow()

    private val _picturesId = MutableStateFlow<List<UUID>>(emptyList())
    val picturesId = _picturesId.asStateFlow()

    private val _picturesForDeletion = MutableStateFlow<List<UUID>>(emptyList())
    val picturesForDeletion = _picturesForDeletion.asStateFlow()

    private var _pictureCount = MutableStateFlow(0)
    val pictureCount: StateFlow<Int> get() = _pictureCount

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()
    fun setBitmap(bitmap: Bitmap?) {
        _bitmap.value = bitmap
    }

    private val _eventPictureDTO = MutableStateFlow<List<EventPictureDTO?>>(emptyList())
    val eventPictureDTO = _eventPictureDTO.asStateFlow()
    fun setEventPictureDTO(eventPictureDTO: List<EventPictureDTO?>) {
        _eventPictureDTO.value = eventPictureDTO
    }

    fun clearBitmap(bitmap: Bitmap) {
        val bitmaps: MutableList<Bitmap> = mutableListOf()
        _pictures.value.filter { it != bitmap }.forEach { bit ->
            if(bit != null) {
                bitmaps.add(bit)
            }
        }
        _pictures.value = bitmaps
        _pictureCount.value = _pictures.value.size
    }

    fun setPicturesBitmap(pictureIds: List<UUID>) {
        val bitmaps = mutableListOf<Bitmap?>()
        val pictureIdsProcessed = mutableListOf<UUID>()

        pictureIds.forEach { pictureId ->
            repository.viewPicture(pictureId, onComplete = { bitmap ->
                bitmaps.add(bitmap)
                pictureIdsProcessed.add(pictureId)

                if (pictureIdsProcessed.size == pictureIds.size) {
                    _pictures.value = bitmaps
                    _picturesId.value = pictureIdsProcessed
                }
            })
        }
        _pictureCount.value = _pictures.value.size
    }

    fun arePicturesForDeletionEmpty(): Boolean {
        return _picturesForDeletion.value.isEmpty()
    }

    fun flagPictureForDeletion(pictureId: UUID) {
        _picturesForDeletion.value += pictureId
    }

    fun setPictureEvents(pictureId: UUID, eventId: UUID) {
        repository.viewPicture(pictureId, onComplete = {
            _bitmap.value = it
            _eventPictureDTO.value += EventPictureDTO(eventId, it)
        })
    }
}