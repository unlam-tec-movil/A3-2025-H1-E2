package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface GetUsuarioScoreUseCase {
    suspend operator fun invoke(): Flow<Usuario>

    fun calcularNivel(score: Int): Int
}
