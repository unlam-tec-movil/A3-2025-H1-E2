package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val getMonumentosUseCase: GetMonumentosUseCase,
    ) : ViewModel() {
        private val _monumentos = MutableStateFlow<List<Monumento>>(emptyList())
        val monumentos: StateFlow<List<Monumento>> = _monumentos.asStateFlow()

        init {
            viewModelScope.launch {
                getMonumentosUseCase.getMonumentos().collect { data ->
                    _monumentos.value = data
                }
            }
        }
    }
