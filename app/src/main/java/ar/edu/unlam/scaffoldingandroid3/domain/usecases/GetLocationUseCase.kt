package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import android.content.Context
import android.location.Location

interface GetLocationUseCase {
    suspend fun getLocation(
        context: Context,
        isPermissionGranted: Boolean,
    ): Location?
}
