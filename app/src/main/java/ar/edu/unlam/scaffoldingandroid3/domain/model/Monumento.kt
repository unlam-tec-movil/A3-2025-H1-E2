package ar.edu.unlam.scaffoldingandroid3.domain.model

import com.google.android.gms.maps.model.LatLng

data class Monumento(
    val idMonumento: Int,
    val name: String,
    val latLng: LatLng,
    val descripcion: String,
    val score: Int,
)
