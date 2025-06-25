package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.data.repository.LocalMonumentRepository
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonumentosService
@Inject
constructor(
    private val monumentoRepository: LocalMonumentRepository,
) : GetMonumentosUseCase {
    override suspend fun getMonumentos(): Flow<List<Monumento>> =
        monumentoRepository.getMonumentos()
}
