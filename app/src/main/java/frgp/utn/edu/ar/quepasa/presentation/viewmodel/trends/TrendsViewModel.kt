package frgp.utn.edu.ar.quepasa.presentation.viewmodel.trends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import frgp.utn.edu.ar.quepasa.data.model.Trend
import frgp.utn.edu.ar.quepasa.domain.repository.TrendRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
open class TrendsViewModel @Inject constructor(
    private val trendRepository: TrendRepository
) : ViewModel() {

    private val _trends = MutableLiveData<List<Trend>>()
    open val trends: LiveData<List<Trend>> = _trends

    open fun loadTrends(barrio: Int, fechaBase: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedTrends = trendRepository.getTrends(barrio, fechaBase)
                _trends.postValue(fetchedTrends)
            } catch (e: Exception) {
                _trends.postValue(emptyList())
            }
        }
    }
}








