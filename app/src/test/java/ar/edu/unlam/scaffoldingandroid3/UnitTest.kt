package ar.edu.unlam.scaffoldingandroid3

import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.domain.model.Usuario
import ar.edu.unlam.scaffoldingandroid3.domain.services.CazarMonumentoService
import com.google.android.gms.maps.model.LatLng
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UnitTest {
    @Test
    fun cazar_monumento_es_true() {
        val usuario = Usuario(name = "Prueba", level = 1, score = 0)

        val monumento =
            Monumento(
                idMonumento = 1,
                name = "Obelisco",
                latLng = LatLng(-34.60362419480108, -58.38162366047333),
                descripcion = "Obelisco de Buenos Aires, Argentina",
                score = 150,
                oculto = false,
            )

        val service = CazarMonumentoService()

        service.cazarMonumento(monumento, usuario)

        val resultadoEsperable = 1
        val resultadoFinal = usuario.monumentosDescubiertos.size

        assertEquals(resultadoEsperable, resultadoFinal)
    }
}
