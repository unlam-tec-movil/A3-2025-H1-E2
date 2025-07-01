package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonumentosScreenViewModel
    @Inject
    constructor(
        private val getMonumentosUseCase: GetMonumentosUseCase,
    ) : ViewModel() {
        var monumentos: List<Monumento> = emptyList()

        init {
            getMonumentos()
        }

        private fun getMonumentos() {
            viewModelScope.launch {
                monumentos = getMonumentosUseCase.getMonumentos().first()
            }
        }
    }
