package ar.edu.unlam.scaffoldingandroid3.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class GetLocationService
    @Inject
    constructor() : GetLocationUseCase {
        private var currentCallback: LocationCallback? = null

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

        @SuppressLint("MissingPermission")
        override suspend fun getLocationStream(
            context: Context,
            isPermissionGranted: Boolean,
            onLocationUpdate: (Location) -> Unit,
        ) {
            if (!isPermissionGranted) return

            val fused = LocationServices.getFusedLocationProviderClient(context)

            val locationRequest =
                LocationRequest
                    .Builder(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        10_000L, // Cada 10 segundos
                    ).apply {
                        setMinUpdateIntervalMillis(5_000L) // No más de una vez cada 5 segundos
                        setWaitForAccurateLocation(false) // No espera precisión GPS
                    }.build()

            val callback =
                object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        result.lastLocation?.let { onLocationUpdate(it) }
                    }
                }

            currentCallback = callback

            fused.requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper(),
            )
        }

        override fun detenerActualizaciones(context: Context) {
            val fused = LocationServices.getFusedLocationProviderClient(context)
            currentCallback?.let {
                fused.removeLocationUpdates(it)
            }
            currentCallback = null
        }
    }
