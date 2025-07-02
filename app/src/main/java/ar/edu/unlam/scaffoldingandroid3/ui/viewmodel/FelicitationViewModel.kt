package ar.edu.unlam.scaffoldingandroid3.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FelicitationViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        val monumento: String = savedStateHandle["monumento"] ?: "Monumento"
        val puntos: Int = savedStateHandle["puntos"] ?: 0

        private val _animacionTerminada = MutableStateFlow(false)
        val animacionTerminada: StateFlow<Boolean> = _animacionTerminada

        init {
            viewModelScope.launch {
                delay(3000)
                _animacionTerminada.value = true
            }
        }
    }
