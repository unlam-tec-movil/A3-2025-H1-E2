package ar.edu.unlam.scaffoldingandroid3.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApptopBar(
    currentRoute: String,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text =
                    when (currentRoute) {
                        NavigationRoutes.AlbumScreen.route -> "Álbum"
                        NavigationRoutes.ProfileScreen.route -> "Perfil"
                        NavigationRoutes.CreateMonument.route -> "Crear Monumento"
                        NavigationRoutes.AjustesScreen.route -> "Ajustes"
                        NavigationRoutes.NotificacionesScreen.route -> "Notificaciones"
                        NavigationRoutes.ComunidadScreen.route -> "Comunidad"
                        NavigationRoutes.MonumentosScreen.route -> "Monumentos"
                        else -> ""
                    },
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
    )
}
