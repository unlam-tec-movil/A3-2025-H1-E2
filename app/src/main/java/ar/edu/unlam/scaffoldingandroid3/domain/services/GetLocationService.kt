package ar.edu.unlam.scaffoldingandroid3.domain.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetLocationUseCase
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class GetLocationService
@Inject
constructor() : GetLocationUseCase {
    @SuppressLint("MissingPermission")
    override suspend fun getLocation(
        context: Context,
        isPermissionGranted: Boolean,
    ): Location? {
        val administradorDeSensorGPS = LocationServices.getFusedLocationProviderClient(context)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isPermissionGranted || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            administradorDeSensorGPS.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.resume(null)
                }
            }
        }
    }
}
