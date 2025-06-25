package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val getMonumentosUseCase: GetMonumentosUseCase,
    private val getUsuarioScoreUseCase: GetUsuarioScoreUseCase
) : ViewModel() {

    private val _monumentos = MutableStateFlow<List<Monumento>>(emptyList())
    val monumentos:  StateFlow<List<Monumento>> = _monumentos.asStateFlow()

    private val _usuario = MutableStateFlow(
        Usuario(name = "", score = 0, level = 1, emptyList())
    )

    val usuario: StateFlow<Usuario> = _usuario.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                getMonumentosUseCase.getMonumentos().collect { data ->
                    _monumentos.value = data
                }
            }
            launch {
                getUsuarioScoreUseCase().collect { user ->
                    _usuario.value = user
                }
            }
        }
    }
}
