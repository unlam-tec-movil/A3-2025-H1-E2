package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.data.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.components.BotonMenuNav
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    controller: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        var state: Boolean by remember { mutableStateOf(false) }

        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(725.dp),
        ) {
            // Mapa
            if (state) {
                NavMenu(controller)
            } else {
                MonumentMap(modifier)
            }
        }

        // Boton Menu
        // su simbolo es un "+", al presionarlo se abre el menu y cambia a un "x"

        Button(
            onClick = { state = !state },
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            colors =
                ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray,
                ),
        ) {
            Icon(
                imageVector = if (state) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (state) "Cerrar menu" else "Abrir menu",
                tint = Color.Black,
                modifier = Modifier.size(30.dp),
            )
        }
    }
}

@Composable
fun MonumentMap(modifier: Modifier) {
    val cameraPositionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(-34.0, 151.0), 10f)
        }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
    ) {
        // Es un ejemplo
        @Composable {
            Marker(
                state = MarkerState(position = LatLng(-34.0, 151.0)),
                title = "Sídney",
                snippet = "Marker en Sídney",
            )
        }
    }
}

@Composable
fun NavMenu(navController: NavHostController) {
    // Se trata de ua columna compuesta por filas de BotonMenuNav(controller, ruta, icon, descripcion)
    // BotonMenuNav(controller = navController, ruta = NavigationRoutes.ProfileScreen, icon =
    // Icons.Default.AccountCircle, descripcion = "Perfil")

    val iconos =
        listOf<ImageVector>(
            Icons.Default.AccountCircle,
            Icons.Default.Create,
            Icons.Default.Settings,
            Icons.Default.Notifications,
            Icons.Default.Share,
            Icons.Default.Place,
            Icons.Default.Favorite,
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(30.dp),
    ) {
        Spacer(modifier = Modifier.size(70.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.ProfileScreen,
                icon = Icons.Default.AccountCircle,
                descripcion = "Perfil",
            )
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.CreateMonument,
                icon = Icons.Default.Create,
                descripcion = "Crear",
            )
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.AjustesScreen,
                icon = Icons.Default.Settings,
                descripcion = "Ajustes",
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.NotificacionesScreen,
                icon = Icons.Default.Notifications,
                descripcion = "Notificaciones",
            )
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.ComunidadScreen,
                icon = Icons.Default.Share,
                descripcion = "Comunidad",
            )
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.MonumentosScreen,
                icon = Icons.Default.Place,
                descripcion = "Monumentos",
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            BotonMenuNav(
                controller = navController,
                ruta = NavigationRoutes.AlbumScreen,
                icon = Icons.Default.Favorite,
                descripcion = "Album",
            )
        }
    }
}

@Preview
@Composable
fun NavMenuPreview() {
    val controller = NavHostController(context = LocalContext.current)
    NavMenu(controller)
}
