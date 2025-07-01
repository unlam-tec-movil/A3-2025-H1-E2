package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import javax.inject.Inject

class GetUsuarioScoreService
    @Inject
    constructor() : GetUsuarioScoreUseCase {
        override fun calcularNivel(score: Int): Int =
            when {
                score >= 800 -> 5
                score >= 400 -> 4
                score >= 200 -> 3
                score >= 100 -> 2
                else -> 1
            }
    }
