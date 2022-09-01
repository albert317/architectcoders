package com.app.featureplaces.presentation.ui.listplaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.Error
import com.app.domain.Place
import com.app.featureplaces.presentation.data.toError
import com.app.featureplaces.usecases.GetPopularPlacesUseCase
import com.app.featureplaces.usecases.RequestPopularPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPlacesViewModel @Inject constructor(
    getPopularPlacesUseCase: GetPopularPlacesUseCase,
    private val requestPopularPlacesUseCase: RequestPopularPlacesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            //Flow<List<Place>>
            getPopularPlacesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { places -> _state.update { UiState(places = places) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularPlacesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val places: List<Place>? = null,
        val error: Error? = null
    )
}
