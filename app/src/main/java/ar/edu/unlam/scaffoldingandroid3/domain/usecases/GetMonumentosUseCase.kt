package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import kotlinx.coroutines.flow.Flow

interface GetMonumentosUseCase {
    suspend fun getMonumentos(): Flow<List<Monumento>>
}
