package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.R
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.ui.components.BotonMenuNav
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.MapScreenViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    controller: NavHostController,
    // hasCameraPermission: Boolean,
    // onRequestCameraPermission: Unit,
    hasLocationPermission: Boolean,
    modifier: Modifier = Modifier,
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            viewModel.cargarUbicacionMonumentos(context, true)
            viewModel.comenzarActualizacionesUbicacion(context, true)
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.detenerUbicacion(context)
        }
    }

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
                MapScreenSuccess(modifier, controller, state.location, state.data, cameraPositionState)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MapScreenSuccess(
    modifier: Modifier,
    controller: NavHostController,
    location: Location?,
    data: List<Monumento>,
    cameraPositionState: CameraPositionState,
) {
    var contentState by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(
            onClick = {
                contentState = !contentState
            },
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            Icon(
                imageVector = if (contentState) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (contentState) "Cerrar menu" else "Abrir menu",
                tint = Color.Black,
                modifier = Modifier.size(30.dp),
            )
        }
    }) { innerPadding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MonumentMap(
                    modifier = Modifier.fillMaxSize().zIndex(0f),
                    userLocation = location,
                    data = data,
                    cameraPositionState = cameraPositionState,
                )
                // Esto es para evitar que se pueda interactuar con el mapa cuando se abre el menu
                if (contentState) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .zIndex(0.9f)
                                .pointerInput(Unit) {
                                    awaitPointerEventScope {
                                        while (true) {
                                            awaitPointerEvent()
                                        }
                                    }
                                },
                    )
                }

                AnimatedContent(
                    targetState = contentState,
                    modifier = Modifier.fillMaxSize().zIndex(1f),
                    content = { state ->
                        if (state) {
                            NavMenu(controller)
                        }
                    },
                    transitionSpec = {
                        slideInVertically(
                            initialOffsetY = { it },
                        ) with
                            slideOutVertically(
                                targetOffsetY = { 0 },
                            )
                    },
                )
            }
        }
    }
}

@Composable
fun MonumentMap(
    modifier: Modifier,
    userLocation: Location?,
    data: List<Monumento>,
    cameraPositionState: CameraPositionState,
) {
    /*val cameraPositionState =
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
        }*/

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    15f,
                ),
                durationMs = 1000,
            )
        }
    }

    val monumentosCercanos =
        remember(userLocation, data) {
            data.filter { monumento ->
                val destino =
                    Location("").apply {
                        latitude = monumento.latLng.latitude
                        longitude = monumento.latLng.longitude
                    }
                (userLocation?.distanceTo(destino) ?: Float.MAX_VALUE) < 500f
            }
        }

    val mapProperties =
        MapProperties(
            isMyLocationEnabled = userLocation != null,
        )

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
    ) {
        // Es un circulo celeste que le da la informacion visual al usuario de los monumentos que se encuentren cerca
        userLocation?.let {
            Circle(
                center = LatLng(it.latitude, it.longitude),
                radius = 500.0,
                fillColor = Color(0x330062D2),
                strokeColor = Color(0xFF0062D2),
                strokeWidth = 2f,
            )
        }

        // La idea es manejar el flujo de la camara al tocar el marker luego.
        // Eso se puede realizar con MarkerInfoWindowContent

        monumentosCercanos.forEach {
            if (!it.oculto) {
                Marker(
                    state = rememberMarkerState(position = it.latLng),
                    title = it.name,
                )
            }
        }
    }
}

@Composable
fun NavMenu(navController: NavHostController) {
    // Se trata de ua columna compuesta por filas de BotonMenuNav(controller, ruta, icon, descripcion)
    // BotonMenuNav(controller = navController, ruta = NavigationRoutes.ProfileScreen, icon =
    // Icons.Default.AccountCircle, descripcion = "Perfil")
    Box(
        modifier =
            Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.menu_background),
            contentDescription = "Menu Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
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
}
