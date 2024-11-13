package frgp.utn.edu.ar.quepasa.presentation.viewmodel.media

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
import frgp.utn.edu.ar.quepasa.domain.repository.media.PostPictureRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.io.IOException
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class PostPictureViewModel @Inject constructor(
    private val repository: PostPictureRepository
): ViewModel() {
    private val _pictures = MutableStateFlow<Page<PostPicture>>(Page(content = emptyList(), totalElements = 0, totalPages = 0, pageNumber = 0))
    val pictures: StateFlow<Page<PostPicture>> get() = _pictures

    private val _picture = MutableStateFlow<PostPicture?>(null)
    val picture: StateFlow<PostPicture?> get() = _picture

    private val _post = MutableStateFlow<Post?>(null)

    private val _errorMessage = MutableStateFlow<String?>(null)

    suspend fun getPictureById(id: Int) {
        try {
            val picture = repository.getPictureById(id)
            _picture.value = picture
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getPicturesByPost(id: Int, page: Int, size: Int) {
        try {
            val pictures = repository.getPicturesByPost(id, page, size)
            _pictures.value = pictures
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun upload(context: Context, uri: Uri, post: Int) {
        try {
            val file: File? = getFileFromUri(context, uri)
            val description: String = getDescriptionFromUri(context, uri) ?: "No description"
            if (file != null) {
                println("File ${file.name}, path ${file.path}")
                repository.upload(file, post, description)
            }
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun deletePicture(id: Int) {
        try {
            repository.deletePicture(id)
        }
        catch(e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun setPost(post: Post) {
        _post.value = post
    }

    fun getPost(): Post? {
        return _post.value
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
        }
        catch (e: IOException) {
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