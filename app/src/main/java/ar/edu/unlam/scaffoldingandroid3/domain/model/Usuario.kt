package ar.edu.unlam.scaffoldingandroid3.domain.model

data class Usuario(
    val name: String,
    var score: Int,
    var level: Int,
    val monumentosDescubiertos: MutableList<Monumento> = mutableListOf<Monumento>(),
    // Faltan foto que se pueda cambiar (var) y una biografia (opcional)
)
