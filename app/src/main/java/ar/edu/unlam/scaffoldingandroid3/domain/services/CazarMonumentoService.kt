package ar.edu.unlam.scaffoldingandroid3.domain.services

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.CazarMonumentoUseCase
import javax.inject.Inject

class CazarMonumentoService
    @Inject
    constructor() : CazarMonumentoUseCase {
        override fun cazarMonumento(
            monumento: Monumento,
            usuario: Usuario,
        ) {
            usuario.monumentosDescubiertos.add(monumento)
        }
    }
