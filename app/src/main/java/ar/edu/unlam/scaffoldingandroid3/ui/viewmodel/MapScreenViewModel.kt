package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import android.content.Context
import android.location.Location
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import ar.edu.unlam.scaffoldingandroid3.ui.location.GetLocationUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

        fun cargarUbicacionMonumentos(
            context: Context,
            permisosConcedidos: Boolean,
        ) {
            viewModelScope.launch {
                val ubicacion = getLocationUseCase.getLocation(context, permisosConcedidos)
                _location.value = ubicacion

                val monumentos = getMonumentosUseCase.getMonumentos().first()
                _uiState.value =
                    MapScreenUiState(
                        mapUiState = MapScreenUi.Success(monumentos, ubicacion),
                    )
            }
        }
/*
        private fun getMonuments() {
            viewModelScope.launch {
                _uiState.value = MapScreenUiState(mapUiState = MapScreenUi.Loading)

                getMonumentosUseCase.getMonumentos().collect { data ->
                    _uiState.update { it.copy(mapUiState = MapScreenUi.Success(data, _location.value)) }
                }
            }
        }
*/

        fun comenzarActualizacionesUbicacion(
            context: Context,
            permisos: Boolean,
        ) {
            viewModelScope.launch {
                getLocationUseCase.getLocationStream(
                    context,
                    permisos,
                ) { nuevaUbicacion ->
                    _location.value = nuevaUbicacion

                    _uiState.update {
                        val actual = it.mapUiState
                        if (actual is MapScreenUi.Success) {
                            it.copy(mapUiState = actual.copy(location = nuevaUbicacion))
                        } else {
                            MapScreenUiState(mapUiState = MapScreenUi.Success(emptyList(), nuevaUbicacion))
                        }
                    }
                }
            }
        }

        fun detenerUbicacion(context: Context) {
            getLocationUseCase.detenerActualizaciones(context)
        }

        fun estaCerca(
            userLocation: Location?,
            monumentoLatLng: LatLng,
            rango: Float = 50f,
        ): Boolean {
            if (userLocation == null) return false

            val monumentLocation =
                Location("").apply {
                    latitude = monumentoLatLng.latitude
                    longitude = monumentoLatLng.longitude
                }

            val distancia = userLocation.distanceTo(monumentLocation)
            return distancia <= rango
        }

        @Immutable
        sealed interface MapScreenUi {
            data object Loading : MapScreenUi

            data class Success(
                val data: List<Monumento>,
                val location: Location?,
            ) : MapScreenUi

            data class Error(
                val message: String,
            ) : MapScreenUi
        }
    }
