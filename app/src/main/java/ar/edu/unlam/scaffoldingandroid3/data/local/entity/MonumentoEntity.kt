package ar.edu.unlam.scaffoldingandroid3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monumentos")
data class MonumentoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val lat: Double,
    val lng: Double,
    val descripcion: String,
    val score: Int,
    val oculto: Boolean,
)
