package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetUsuarioScoreUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsuarioScoreService
    @Inject
    constructor(
        private val getMonumentosUseCase: GetMonumentosUseCase,
    ) : GetUsuarioScoreUseCase {
        override suspend operator fun invoke(): Flow<Usuario> {
            val monumentosDescubiertos = setOf(1, 2)

            return getMonumentosUseCase.getMonumentos().map { lista ->
                val descubiertos = lista.filter { it.idMonumento in monumentosDescubiertos }
                val puntaje = descubiertos.sumOf { it.score }
                val nivel = calcularNivel(puntaje)

                Usuario(
                    name = "Guest",
                    score = puntaje,
                    level = nivel,
                    monumentosDescubiertos = descubiertos,
                )
            }
        }

        override fun calcularNivel(score: Int): Int =
            when {
                score >= 400 -> 5
                score >= 200 -> 4
                score >= 100 -> 3
                score >= 50 -> 2
                else -> 1
            }
    }
