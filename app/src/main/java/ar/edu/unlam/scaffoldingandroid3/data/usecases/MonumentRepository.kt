package ar.edu.unlam.scaffoldingandroid3.data.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import kotlinx.coroutines.flow.Flow

interface MonumentRepository {
    suspend fun getMonumentos(): Flow<List<Monumento>>
}
