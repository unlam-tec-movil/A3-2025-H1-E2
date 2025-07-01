package ar.edu.unlam.scaffoldingandroid3.domain.usecases

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario

interface CazarMonumentoUseCase {
    fun cazarMonumento(
        monumento: Monumento,
        usuario: Usuario,
    )
}
