package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.data.repository.UserLocalRepository
import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUsuarioScoreService
    @Inject
    constructor() : GetUsuarioScoreUseCase {
        override suspend fun invoke(): Flow<Usuario> = flowOf(UserLocalRepository.usuario)

        override fun calcularNivel(score: Int): Int =
            when {
                score >= 800 -> 5
                score >= 400 -> 4
                score >= 200 -> 3
                score >= 100 -> 2
                else -> 1
            }
    }
