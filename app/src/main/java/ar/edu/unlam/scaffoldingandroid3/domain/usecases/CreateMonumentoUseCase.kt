package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento

interface CreateMonumentoUseCase {
    suspend fun createMonumento(monumento: Monumento)
}
