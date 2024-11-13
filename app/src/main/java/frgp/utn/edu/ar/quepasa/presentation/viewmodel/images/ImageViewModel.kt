package frgp.utn.edu.ar.quepasa.presentation.viewmodel.images

import android.net.Uri
import androidx.lifecycle.ViewModel
import frgp.utn.edu.ar.quepasa.data.model.media.EventPicture
import frgp.utn.edu.ar.quepasa.data.model.media.PostPicture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ImageViewModel : ViewModel() {
    private val _selectedUris = MutableStateFlow<List<Uri>>(emptyList())
    val selectedUris = _selectedUris.asStateFlow()

    private val _selectedUrls = MutableStateFlow<List<String>>(emptyList())
    val selectedUrls = _selectedUrls.asStateFlow()

    private val _selectedUrlsId = MutableStateFlow<List<UUID>>(emptyList())
    val selectedUrisId = _selectedUrlsId.asStateFlow()

    fun addImages(uris: List<Uri>) {
        _selectedUris.value += uris
    }

    fun clearImages() {
        _selectedUris.value = emptyList()
    }

    fun clearImage(uri: Uri) {
        var uriList = emptyList<Uri>()
        _selectedUris.value.forEach { selUri ->
            if(selUri.path != uri.path) uriList = uriList + selUri
        }
        _selectedUris.value = uriList
    }

    fun areUrisEmpty(): Boolean {
        return _selectedUris.value.isEmpty()
    }

    fun loadUrlsFromPostPictures(pictures: List<PostPicture>) {
        val urls: MutableList<String> = mutableListOf()
        val urlsId: MutableList<UUID> = mutableListOf()
        pictures.forEach { picture ->
            val url = "http://canedo.com.ar:8080/api/pictures/" + picture.id + "/view"

            urls.add(url)
            urlsId.add(picture.id)
            println("url $url")
        }

        _selectedUrls.value = urls
        _selectedUrlsId.value = urlsId
    }

    fun loadUrisFromEventPictures(pictures: List<EventPicture>) {

    }
}