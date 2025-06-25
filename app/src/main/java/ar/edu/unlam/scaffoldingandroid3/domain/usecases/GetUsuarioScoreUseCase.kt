package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsuarioScoreUseCase @Inject constructor(
    private val getMonumentosUseCase: GetMonumentosUseCase
) {
    suspend operator fun invoke(): Flow<Usuario> {
        val monumentosDescubiertos = setOf(1,2)

        return getMonumentosUseCase.getMonumentos().map { lista ->
            val descubiertos = lista.filter { it.idMonumento in monumentosDescubiertos }
            val puntaje = descubiertos.sumOf { it.score }
            val nivel = calcularNivel(puntaje)

            Usuario(
                name = "Lucas",
                score = puntaje,
                level = nivel,
                monumentosDescubiertos = descubiertos
            )
        }
    }

    private fun calcularNivel(score: Int): Int {
        return when {
            score >= 300 -> 5
            score >= 200 -> 4
            score >= 100 -> 3
            score >= 50 -> 2
            else -> 1
        }
    }
}
