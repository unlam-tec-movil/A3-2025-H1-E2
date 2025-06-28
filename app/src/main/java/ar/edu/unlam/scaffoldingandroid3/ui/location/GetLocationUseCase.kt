package ar.edu.unlam.scaffoldingandroid3.ui.location

import android.content.Context
import android.location.Location

interface GetLocationUseCase {
    suspend fun getLocation(
        context: Context,
        isPermissionGranted: Boolean,
    ): Location?

    suspend fun getLocationStream(
        context: Context,
        isPermissionGranted: Boolean,
        onLocationUpdate: (Location) -> Unit,
    )

    fun detenerActualizaciones(context: Context)
}
