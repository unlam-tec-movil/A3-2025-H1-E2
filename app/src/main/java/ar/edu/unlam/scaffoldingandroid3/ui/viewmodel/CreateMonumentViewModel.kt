package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.CreateMonumentoUseCase
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CreateMonumentViewModel
    @Inject
    constructor(
        private val getUsuarioScoreUseCase: GetUsuarioScoreUseCase,
        private val crearMonumentoUseCase: CreateMonumentoUseCase,
    ) : ViewModel() {
        var nombre by mutableStateOf("")
        var descripcion by mutableStateOf("")

        private val _uiState = MutableStateFlow<CreateMonumentUiState>(CreateMonumentUiState.Loading)
        val uiState: StateFlow<CreateMonumentUiState> = _uiState

        fun verificarNivelUsuario() {
            viewModelScope.launch {
                getUsuarioScoreUseCase().collect { user ->
                    if (user.level >= 5) {
                        _uiState.value = CreateMonumentUiState.CanCreate
                    } else {
                        _uiState.value = CreateMonumentUiState.Restricted
                    }
                }
            }
        }

        fun crearMonumento(userLocation: Location?) {
            if (userLocation == null) {
                _uiState.value = CreateMonumentUiState.Error("Ubicación no disponible")
                return
            }

            val latLng = LatLng(userLocation.latitude, userLocation.longitude)

            val nuevo =
                Monumento(
                    idMonumento = System.currentTimeMillis().toInt(),
                    name = nombre,
                    descripcion = descripcion,
                    latLng = latLng,
                    score = 100,
                    oculto = false,
                )

            viewModelScope.launch {
                try {
                    crearMonumentoUseCase.createMonumento(nuevo)
                    _uiState.value = CreateMonumentUiState.Created
                } catch (e: Exception) {
                    _uiState.value = CreateMonumentUiState.Error("Error al crear monumento")
                }
            }
        }

        sealed class CreateMonumentUiState {
            object Loading : CreateMonumentUiState()

            object CanCreate : CreateMonumentUiState()

            object Restricted : CreateMonumentUiState()

            object Created : CreateMonumentUiState()

            data class Error(
                val message: String,
            ) : CreateMonumentUiState()
        }
    }
