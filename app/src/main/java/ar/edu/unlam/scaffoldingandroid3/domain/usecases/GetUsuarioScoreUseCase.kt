package ar.edu.unlam.scaffoldingandroid3.domain.usecases

interface GetUsuarioScoreUseCase {
    fun calcularNivel(score: Int): Int
}
