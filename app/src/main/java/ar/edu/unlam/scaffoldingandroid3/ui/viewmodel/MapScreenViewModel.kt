package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
    @Inject
    constructor(
        private val getMonumentosUseCase: GetMonumentosUseCase,
    ) : ViewModel() {
        data class MapScreenUiState(
            val mapUiState: MapScreenUi = MapScreenUi.Loading,
        )

        private val _uiState = MutableStateFlow(MapScreenUiState())
        val uiState get() = _uiState.asStateFlow()

        init {
            getMonumentos()
        }

        private fun getMonumentos() {
            viewModelScope.launch {
                _uiState.value = MapScreenUiState(mapUiState = MapScreenUi.Loading)

                getMonumentosUseCase.getMonumentos().collect { data ->
                    _uiState.update { it.copy(mapUiState = MapScreenUi.Success(data)) }
                }
            }
        }

        @Immutable
        sealed interface MapScreenUi {
            data object Loading : MapScreenUi

            data class Success(
                val data: List<Monumento>,
            ) : MapScreenUi

            data class Error(
                val message: String,
            ) : MapScreenUi
        }
    }
