package frgp.utn.edu.ar.quepasa.presentation.viewmodel.images

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImageViewModel : ViewModel() {
    private val _selectedUris = MutableStateFlow<List<Uri>>(emptyList())
    val selectedUris = _selectedUris.asStateFlow()

    fun addImages(uris: List<Uri>) {
        println(uris[0].path)
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
}