package ar.edu.unlam.scaffoldingandroid3.data.repository

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.MonumentRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocalMonumentRepository
@Inject
constructor() : MonumentRepository {
    private val localMonumentRepository =
        listOf<Monumento>(
            Monumento(
                idMonumento = 1,
                name = "Obelisco",
                latLng = LatLng(-34.60362419480108, -58.38162366047333),
                descripcion = "Obelisco de Buenos Aires, Argentina",
                score = 150,
            ),
            Monumento(
                idMonumento = 2,
                name = "Estatua Moron",
                latLng = LatLng(-34.65116179199579, -58.62194483163555),
                descripcion = "Estatua de San Martin de la Plaza Del Libertador Gran San Martin",
                score = 200,
            ),
            Monumento(
                idMonumento = 3,
                name = "Cabildo",
                latLng = LatLng(-34.608858333333, -58.373755555556),
                descripcion = "Cabildo de Buenos Aires, Argentina",
                score = 200,
            ),
        )

    override suspend fun getMonumentos(): Flow<List<Monumento>> {
        val data = localMonumentRepository
        return flowOf(data)
    }
}
