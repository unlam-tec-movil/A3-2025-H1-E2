package ar.edu.unlam.scaffoldingandroid3.data.repository

import ar.edu.unlam.scaffoldingandroid3.data.local.dao.MonumentoDao
import ar.edu.unlam.scaffoldingandroid3.data.local.entity.MonumentoEntity
import ar.edu.unlam.scaffoldingandroid3.data.usecases.MonumentRepository
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMonumentRepository
    @Inject
    constructor(
        private val dao: MonumentoDao,
    ) : MonumentRepository {
        private val localMonumentRepository =
            listOf<Monumento>(
                Monumento(
                    idMonumento = 1,
                    name = "Obelisco",
                    latLng = LatLng(-34.60362419480108, -58.38162366047333),
                    descripcion = "Obelisco de Buenos Aires, Argentina",
                    score = 300,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 2,
                    name = "Estatua Moron",
                    latLng = LatLng(-34.65116179199579, -58.62194483163555),
                    descripcion = "Estatua de San Martin de la Plaza Del Libertador Gran San Martin",
                    score = 100,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 3,
                    name = "Cabildo",
                    latLng = LatLng(-34.608858333333, -58.373755555556),
                    descripcion = "Cabildo de Buenos Aires, Argentina",
                    score = 300,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 4,
                    name = "Universidad de La Matanza",
                    latLng = LatLng(-34.670544343832475, -58.562783437847855),
                    descripcion = "Entrada de la UNLaM",
                    score = 100,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 5,
                    name = "Fuente de la UNLaM",
                    latLng = LatLng(-34.66998846598079, -58.56340734738425),
                    descripcion = "Fuente de la Universidad de La Matanza",
                    score = 250,
                    oculto = true,
                ),
                Monumento(
                    idMonumento = 6,
                    name = "Iglesia del Hospital Italiano",
                    latLng = LatLng(-34.66926837877978, -58.56729495924575),
                    descripcion = "Capilla del Sagrado Corazón",
                    score = 50,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 7,
                    name = "Parada nocturna del 242",
                    latLng = LatLng(-34.67137377327287, -58.563262764404136),
                    descripcion = "Tras pasar las 21hs esta es la parada del 242",
                    score = 100,
                    oculto = true,
                ),
                Monumento(
                    idMonumento = 8,
                    name = "Biblioteca de la UNLaM",
                    latLng = LatLng(-34.669326479644575, -58.56407934324374),
                    descripcion = "Biblioteca Leopoldo Marechal",
                    score = 100,
                    oculto = false,
                ),
                Monumento(
                    idMonumento = 9,
                    name = "Teatro de la UNLaM",
                    latLng = LatLng(-34.670385465272204, -58.56303273482913),
                    descripcion = "Teatro Universidad Nacional de La Matanza",
                    score = 100,
                    oculto = false,
                ),
            )

        override suspend fun getMonumentos(): Flow<List<Monumento>> =
            dao.getAll().map { entities ->
                val local = entities.map { it.toDomain() }
                localMonumentRepository + local
            }

        suspend fun addMonumento(monumento: Monumento) {
            dao.insert(monumento.toEntity())
        }

        private fun MonumentoEntity.toDomain(): Monumento =
            Monumento(
                idMonumento = id,
                name = name,
                latLng = LatLng(lat, lng),
                descripcion = descripcion,
                score = score,
                oculto = oculto,
            )

        private fun Monumento.toEntity(): MonumentoEntity =
            MonumentoEntity(
                name = name,
                lat = latLng.latitude,
                lng = latLng.longitude,
                descripcion = descripcion,
                score = score,
                oculto = oculto,
            )
    }
