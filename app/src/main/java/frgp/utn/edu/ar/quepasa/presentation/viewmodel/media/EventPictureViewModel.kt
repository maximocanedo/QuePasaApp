package frgp.utn.edu.ar.quepasa.presentation.viewmodel.media

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.Event
import frgp.utn.edu.ar.quepasa.data.model.media.EventPicture
import frgp.utn.edu.ar.quepasa.domain.repository.media.EventPictureRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.io.IOException
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class EventPictureViewModel @Inject constructor(
    private val repository: EventPictureRepository
) : ViewModel() {
    private val _pictures = MutableStateFlow<Page<EventPicture>>(
        Page(
            content = emptyList(),
            totalElements = 0,
            totalPages = 0,
            pageNumber = 0
        )
    )
    val pictures = _pictures.asStateFlow()

    val _eventPictures = MutableStateFlow<List<EventPicture>>(emptyList())
    val eventPictures = _eventPictures.asStateFlow()

    private val _picture = MutableStateFlow<EventPicture?>(null)
    val picture = _picture.asStateFlow()

    private val _picturesIds = MutableStateFlow<List<UUID>>(emptyList())
    val picturesIds = _picturesIds.asStateFlow()

    private val _event = MutableStateFlow<Event?>(null)

    private val _errorMessage = MutableStateFlow<String?>(null)

    suspend fun getPictureById(id: UUID) {
        try {
            val picture = repository.getPictureById(id)
            _picture.value = picture
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPicturesByEvent(id: UUID, page: Int = 0, size: Int = 10) {
        try {
            val pictures = repository.getPicturesByEvent(id, page, size)
            val picturesIds: MutableList<UUID> = mutableListOf()
            pictures.content.forEach { picture ->
                picturesIds.add(picture.id)
            }
            _picturesIds.value = picturesIds
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun setEventsPicture(id: UUID) {
        try {
            val pictures = repository.getPicturesByEvent(id, 0, 10).content
            _eventPictures.value += pictures
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun clearEventPictures() {
        _eventPictures.value = emptyList()
    }

    suspend fun upload(context: Context, uri: Uri, event: UUID) {
        try {
            val file: File? = getFileFromUri(context, uri)
            val description: String = getDescriptionFromUri(context, uri) ?: "No description"
            if (file != null) {
                println("File ${file.name}, path ${file.path}")
                repository.upload(file, event, description)
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deletePicture(id: UUID) {
        try {
            repository.deletePicture(id)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun setEvent(event: Event) {
        _event.value = event
    }

    fun getEvent(): Event? {
        return _event.value
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver

        if (uri.scheme == "file") {
            return uri.path?.let { File(it) }
        }

        try {
            val fileName = getFileNameFromUri(context, uri) ?: "tempfile"
            val tempFile = File(context.cacheDir, fileName)

            val outputStream: OutputStream = FileOutputStream(tempFile)
            contentResolver.openInputStream(uri)?.copyTo(outputStream)

            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
        return null
    }

    private fun getDescriptionFromUri(context: Context, uri: Uri): String? {
        val contentResolver: ContentResolver = context.contentResolver
        var description: String? = null

        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                description = cursor.getString(nameIndex)
            }
        }

        return description
    }
}