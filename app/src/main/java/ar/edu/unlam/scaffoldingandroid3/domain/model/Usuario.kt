package ar.edu.unlam.scaffoldingandroid3.domain.model

data class Usuario(
    val name: String,
    val score: Int,
    val level: Int,
    // Faltan foto que se pueda cambiar (var) y una biografia (opcional)
)
