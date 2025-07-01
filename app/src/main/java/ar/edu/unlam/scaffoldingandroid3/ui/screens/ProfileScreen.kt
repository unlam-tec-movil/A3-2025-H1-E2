package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import ar.edu.unlam.scaffoldingandroid3.data.repository.UserLocalRepository
import ar.edu.unlam.scaffoldingandroid3.ui.navigation.NavigationRoutes
import ar.edu.unlam.scaffoldingandroid3.ui.theme.BackgroundEnd
import ar.edu.unlam.scaffoldingandroid3.ui.theme.Gold
import ar.edu.unlam.scaffoldingandroid3.ui.theme.ScaffoldingAndroid3Theme
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    controller: NavController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val monumentos by viewModel.monumentos.collectAsState()
    val usuario = UserLocalRepository.usuario

    val monumentosTotales = monumentos.size
    val monumentosDelUsuario = usuario.monumentosDescubiertos
    val filas = monumentosTotales.div(3)

    // var state: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize().padding(innerPadding),
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))

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
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Monumentos Cazados",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = modifier.padding(start = 6.dp),
            )
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    monumentosDelUsuario.forEach {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Gold,
                                modifier = modifier.size(40.dp),
                            )
                            Text(
                                text = it.name,
                                modifier = modifier.padding(12.dp),
                            )
                        }
                    }
                }
            }
            Button(
                onClick = { controller.navigate(NavigationRoutes.AlbumScreen.route) },
                modifier =
                    Modifier
                        .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = BackgroundEnd,
                        contentColor = Color.White,
                    ),
            ) {
                Text(text = "Ver álbum")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ScaffoldingAndroid3Theme {
        val controller = NavHostController(context = LocalContext.current)
        ProfileScreen(
            controller,
            modifier = TODO(),
            innerPadding = TODO(),
            viewModel = TODO(),
        )
    }
}
