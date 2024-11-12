package frgp.utn.edu.ar.quepasa.presentation.viewmodel.media

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.Post
import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
import frgp.utn.edu.ar.quepasa.domain.repository.media.PostPictureRepository
import frgp.utn.edu.ar.quepasa.utils.pagination.Page
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _picture = MutableStateFlow<PostPicture?>(null)

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

    suspend fun upload(context: Context, uri: Uri, description: String) {
        try {
            val file: File? = getFileFromUri(context, uri)

            if(file != null) {

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

    fun getFileFromUri(context: Context, uri: Uri): File? {
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
            val columnIndex = cursor.getColumnIndex(android.provider.MediaStore.Images.Media.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}