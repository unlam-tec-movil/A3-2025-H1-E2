package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.data.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.ui.components.BotonMenuNav
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.MapScreenViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    controller: NavHostController,
    hasCameraPermission: Boolean,
    onRequestCameraPermission: Unit,
    modifier: Modifier = Modifier,
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState.value.mapUiState) {
            is MapScreenViewModel.MapScreenUi.Error ->
                ErrorScreen(
                    modifier =
                        Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                    error = "Error",
                )

            MapScreenViewModel.MapScreenUi.Loading -> LoadingScreen()
            is MapScreenViewModel.MapScreenUi.Success ->
                MapScreenSuccess(modifier, controller, viewModel.location, state.data)
        }
    }
}

@Composable
private fun MapScreenSuccess(
    modifier: Modifier,
    controller: NavHostController,
    location: Location?,
    data: List<Monumento>,
) {
    var state: Boolean by remember { mutableStateOf(false) }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            onClick = { state = !state },
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            Icon(
                imageVector = if (state) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (state) "Cerrar menu" else "Abrir menu",
                tint = Color.Black,
                modifier = Modifier.size(30.dp),
            )
        }
    }) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(visible = !state, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .animateEnterExit(
                                enter = slideInVertically(),
                                exit = slideOutVertically(),
                            ),
                ) {
                    MonumentMap(modifier = Modifier.fillMaxSize(), location, data)
                }
            }
            AnimatedVisibility(visible = state, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .animateEnterExit(
                                enter = slideInVertically(),
                                exit = slideOutVertically(),
                            ),
                ) {
                    NavMenu(controller)
                }
            }
        }
    }
}

@Composable
fun MonumentMap(
    modifier: Modifier,
    userLocation: Location?,
    data: List<Monumento>,
) {
    val cameraPositionState =
        rememberCameraPositionState {
            if (userLocation != null) {
                position =
                    CameraPosition.fromLatLngZoom(
                        LatLng(
                            userLocation.latitude,
                            userLocation.longitude,
                        ),
                        10f,
                    )
            }
        }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
    ) {
        // La idea es manejar el flujo de la camara al tocar el marker luego.
        // Eso se puede realizar con MarkerInfoWindowContent
        data.forEach {
            val dataState = rememberMarkerState(position = it.latLng)
            Marker(
                state = dataState,
                title = it.name,
            )
        }
    }
}

@Composable
fun NavMenu(navController: NavHostController) {
    // Se trata de ua columna compuesta por filas de BotonMenuNav(controller, ruta, icon, descripcion)
    // BotonMenuNav(controller = navController, ruta = NavigationRoutes.ProfileScreen, icon =
    // Icons.Default.AccountCircle, descripcion = "Perfil")

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
