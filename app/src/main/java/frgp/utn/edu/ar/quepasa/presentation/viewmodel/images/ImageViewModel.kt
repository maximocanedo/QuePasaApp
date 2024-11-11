package frgp.utn.edu.ar.quepasa.presentation.viewmodel.images

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImageViewModel : ViewModel() {
    private val _selectedUris = MutableStateFlow<List<Uri>>(emptyList())
    val selectedUris = _selectedUris.asStateFlow()

    fun addImages(uris: List<Uri>) {
        _selectedUris.value += uris
    }

    fun clearImages() {
        _selectedUris.value = emptyList()
    }
}