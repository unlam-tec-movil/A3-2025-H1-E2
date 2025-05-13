package ar.edu.unlam.scaffoldingandroid3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.scaffoldingandroid3.data.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.screens.MapScreen
import ar.edu.unlam.scaffoldingandroid3.ui.screens.ProfileScreen
import ar.edu.unlam.scaffoldingandroid3.ui.theme.ScaffoldingAndroid3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val controller = rememberNavController()

            ScaffoldingAndroid3Theme {

                val navBackStackEntry = controller.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController = controller, startDestination = NavigationRoutes.MapScreen.route) {

                     composable (NavigationRoutes.MapScreen.route){

                         MapScreen(controller)

                     }

                        composable (NavigationRoutes.ProfileScreen.route) {

                            ProfileScreen()


                        }

                    }
                }
            }
        }
    }
}
