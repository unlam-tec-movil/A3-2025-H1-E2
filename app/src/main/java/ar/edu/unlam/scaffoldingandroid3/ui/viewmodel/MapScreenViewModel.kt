package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import android.content.Context
import android.location.Location
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetLocationUseCase
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
        private val getLocationUseCase: GetLocationUseCase,
    ) : ViewModel() {
        data class MapScreenUiState(
            val mapUiState: MapScreenUi = MapScreenUi.Loading,
        )

        private val _uiState = MutableStateFlow(MapScreenUiState())
        val uiState get() = _uiState.asStateFlow()

        private val _location = mutableStateOf<Location?>(null)
        val location get() = _location.value

        init {
            getMonumentos()
        }

        fun getLocation() {
            viewModelScope.launch {
                // Es true el permiso porque se valida al iniciar la app
                val result = getLocationUseCase.getLocation(context = this as Context, true)
                if (result != null) {
                    _location.value = result
                }
            }
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
