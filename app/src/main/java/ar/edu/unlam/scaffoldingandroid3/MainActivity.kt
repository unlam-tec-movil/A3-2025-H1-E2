package ar.edu.unlam.scaffoldingandroid3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.screens.AjustesScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.ComunidadScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.CreateMonumentScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.MapScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.MonumentosScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.NotificacionesScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.ProfileScreen
import ar.edu.unlam.scaffoldingandroid3.ui.theme.ScaffoldingAndroid3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>

    private var hasFineLocationPermission = mutableStateOf(false)
    private var hasCameraPermission = mutableStateOf(false)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        locationPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted ->
                hasFineLocationPermission.value = isGranted
            }

        cameraPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted ->
                hasCameraPermission.value = isGranted
            }

        requestLocationPermission()

        setContent {
            val locationPermission by remember { hasFineLocationPermission }
            // val cameraPermission by remember { hasCameraPermission }

            val controller = rememberNavController()

            ScaffoldingAndroid3Theme {
                // val navBackStackEntry = controller.currentBackStackEntryAsState()
                // val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = controller,
                        startDestination = NavigationRoutes.MapScreen.route,
                    ) {
                        composable(NavigationRoutes.MapScreen.route) {
                            if (locationPermission) {
                                MapScreen(
                                    controller,
                                    // cameraPermission,
                                    // requestCameraPermission(),
                                    locationPermission,
                                )
                            }
                        }

                        composable(NavigationRoutes.ProfileScreen.route) {
                            ProfileScreen(controller)
                        }

                        composable(NavigationRoutes.CreateMonument.route) {
                            CreateMonumentScreen()
                        }

                        composable(NavigationRoutes.AjustesScreen.route) {
                            AjustesScreen()
                        }

                        composable(NavigationRoutes.NotificacionesScreen.route) {
                            NotificacionesScreen()
                        }

                        composable(NavigationRoutes.ComunidadScreen.route) {
                            ComunidadScreen()
                        }

                        composable(NavigationRoutes.MonumentosScreen.route) {
                            MonumentosScreen()
                        }

                        composable(NavigationRoutes.AlbumScreen.route) { }
                    }
                }
            }
        }
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

   /* private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    Esta funcion pide ambos permisos, la dejo comentada porque puede ser de utilidad aunque se pidan en casos separados

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.CAMERA,
            ),
        )
    }

    La dejo comentada porque puede ser de utilidad, la funcion revisa si se otorgo el permiso de ubicacion

    @Composable
    private fun isCoarseLocationPermissionGranted(): Boolean {
        val context = LocalContext.current
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }
     */
}
