package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUsuarioScoreService
    @Inject
    constructor() : GetUsuarioScoreUseCase {
        override suspend operator fun invoke(): Flow<Usuario> {
            val usuarioHardcodeado = Usuario(name = "Guest", score = 0, level = 1, monumentosDescubiertos = emptyList())

            return flowOf(usuarioHardcodeado)
        }

        override fun calcularNivel(score: Int): Int =
            when {
                score >= 800 -> 5
                score >= 400 -> 4
                score >= 200 -> 3
                score >= 100 -> 2
                else -> 1
            }
    }
