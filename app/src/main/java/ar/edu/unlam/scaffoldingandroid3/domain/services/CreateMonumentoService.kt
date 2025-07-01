package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.data.repository.LocalMonumentRepository
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.CreateMonumentoUseCase
import javax.inject.Inject

class CreateMonumentoService
    @Inject
    constructor(
        private val repository: LocalMonumentRepository,
    ) : CreateMonumentoUseCase {
        override suspend fun createMonumento(monumento: Monumento) {
            repository.addMonumento(monumento)
        }
    }
