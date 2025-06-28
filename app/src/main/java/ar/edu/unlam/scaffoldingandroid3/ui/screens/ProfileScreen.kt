package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.theme.Gold
import ar.edu.unlam.scaffoldingandroid3.ui.theme.ScaffoldingAndroid3Theme
import ar.edu.unlam.scaffoldingandroid3.ui.theme.Teal
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    controller: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val monumentos by viewModel.monumentos.collectAsState()
    val usuario by viewModel.usuario.collectAsState()

    val monumentosTotales = monumentos.size
    val monumentosActuales = usuario.monumentosDescubiertos

    // var state: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            Button(
                onClick = { controller.navigate(NavigationRoutes.AlbumScreen.route) },
                modifier = modifier.padding(12.dp),
                shape = RoundedCornerShape(20.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Teal,
                        contentColor = Color.White,
                    ),
            ) {
                Text(text = "Ver álbum")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = modifier.height(30.dp))

            Text(
                text = usuario.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Nivel: ${usuario.level} - Score: ${usuario.score}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = modifier.height(40.dp))
            Text(
                text = "Monumentos Cazados",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = modifier.padding(start = 6.dp),
            )

            Spacer(modifier = modifier.height(15.dp))

            Spacer(modifier = modifier.height(8.dp))

            FlowRow(
                modifier = modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                monumentosActuales.forEach {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Gold,
                            modifier =
                                modifier
                                    .size(40.dp),
                        )
                        Text(
                            text = it.name,
                            modifier =
                                modifier
                                    .padding(12.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ScaffoldingAndroid3Theme {
        val controller = NavHostController(context = LocalContext.current)
        ProfileScreen(controller)
    }
}
