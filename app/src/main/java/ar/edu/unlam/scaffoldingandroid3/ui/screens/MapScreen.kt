package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.R
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.ui.components.BotonMenuNav
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.MapScreenViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.io.File

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
            viewModel.iniciarDeteccionSacudida()
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
                MapScreenSuccess(
                    modifier,
                    controller,
                    state.location,
                    state.data,
                    cameraPositionState,
                    viewModel,
                )
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
    viewModel: MapScreenViewModel,
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
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .zIndex(0f),
                    userLocation = location,
                    data = data,
                    cameraPositionState = cameraPositionState,
                    viewModel,
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
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .zIndex(1f),
                    content = { state ->
                        if (state) {
                            NavMenu(controller)
                        }
                    },
                    transitionSpec = {
                        slideInVertically(
                            initialOffsetY = { it },
                        ) togetherWith
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
    viewModel: MapScreenViewModel,
) {
    val context = LocalContext.current

    val photoUri = remember { mutableStateOf<Uri?>(null) }
    val photoFile = remember { mutableStateOf<File?>(null) }
    val pendingTakePicture = remember { mutableStateOf(false) }

    val mostrarOcultos by viewModel.mostrarOcultos
    val activarAnimacion by viewModel.activarAnimacionSensor

    val animatedRadius by animateFloatAsState(
        targetValue = if (activarAnimacion) 500f else 0f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (activarAnimacion) 0.35f else 0f,
        animationSpec = tween(800),
    )

    val takePictureLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "¡Monumento Cazado!", Toast.LENGTH_SHORT).show()
                photoFile.value?.let { viewModel.guardarFoto(it.absolutePath) }
            } else {
                Toast.makeText(context, "No se tomó la foto", Toast.LENGTH_SHORT).show()
            }
        }

    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted && pendingTakePicture.value) {
                photoUri.value?.let { uri ->
                    val intent =
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                            putExtra(MediaStore.EXTRA_OUTPUT, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                    takePictureLauncher.launch(intent)
                }
                pendingTakePicture.value = false
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f),
                durationMs = 1000,
            )
        }
    }

    val monumentosVisibles =
        remember(userLocation, data, mostrarOcultos) {
            data.filter { monumento ->
                val distancia =
                    userLocation?.let {
                        val destino =
                            Location("").apply {
                                latitude = monumento.latLng.latitude
                                longitude = monumento.latLng.longitude
                            }
                        it.distanceTo(destino)
                    } ?: Float.MAX_VALUE

                distancia < 500f && (!monumento.oculto || mostrarOcultos)
            }
        }

    val mapProperties = MapProperties(isMyLocationEnabled = userLocation != null)

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = MapUiSettings(zoomControlsEnabled = false),
    ) {
        userLocation?.let {
            Circle(
                center = LatLng(it.latitude, it.longitude),
                radius = 500.0,
                fillColor = Color(0x330062D2),
                strokeColor = Color(0xFF0062D2),
                strokeWidth = 2f,
            )

            if (activarAnimacion) {
                Circle(
                    center = LatLng(it.latitude, it.longitude),
                    radius = animatedRadius.toDouble(),
                    fillColor = Color(0x882E004F).copy(alpha = animatedAlpha),
                    strokeColor = Color(0xFF2E004F),
                    strokeWidth = 2f,
                )
            }
        }

        monumentosVisibles.forEach { monumento ->
            Marker(
                state = rememberMarkerState(position = monumento.latLng),
                title = monumento.name,
                icon =
                    if (monumento.oculto) {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                    } else {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    },
                onClick = {
                    if (viewModel.estaCerca(userLocation, monumento.latLng)) {
                        try {
                            val file = File(context.filesDir, "monumento_${System.currentTimeMillis()}.jpg")
                            val uri =
                                FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    file,
                                )
                            photoUri.value = uri
                            photoFile.value = file

                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA,
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                val intent =
                                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                                        putExtra(MediaStore.EXTRA_OUTPUT, uri)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                takePictureLauncher.launch(intent)
                            } else {
                                pendingTakePicture.value = true
                                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        } catch (e: Exception) {
                            Log.e("CameraError", "Error al abrir la cámara: ${e.message}", e)
                            Toast.makeText(context, "Error al abrir la cámara", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Acércate un poco más al monumento", Toast.LENGTH_SHORT).show()
                    }
                    true
                },
            )
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
