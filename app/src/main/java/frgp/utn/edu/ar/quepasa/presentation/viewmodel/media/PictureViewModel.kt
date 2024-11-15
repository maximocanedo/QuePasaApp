package frgp.utn.edu.ar.quepasa.presentation.viewmodel.media

import androidx.lifecycle.ViewModel
import coil3.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.domain.repository.media.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val repository: PictureRepository
) : ViewModel() {
    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()
    fun setBitmap(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    private val _pictures = MutableStateFlow<List<Bitmap?>>(emptyList())
    val pictures = _pictures.asStateFlow()

    fun setPictureBitmap(pictureId: UUID) {
        repository.viewPicture(pictureId, onComplete = {
            _bitmap.value = it
        })
    }

    fun setPicturesBitmap(pictureIds: List<UUID>) {
        val bitmaps = mutableListOf<Bitmap?>()
        pictureIds.forEach { pictureId ->
            repository.viewPicture(pictureId, onComplete = {
                bitmaps.add(it)
                _pictures.value = bitmaps
            })
        }
    }
}