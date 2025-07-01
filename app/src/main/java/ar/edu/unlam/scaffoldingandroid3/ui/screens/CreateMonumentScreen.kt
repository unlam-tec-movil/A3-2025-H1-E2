package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.CreateMonumentViewModel
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.MapScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun CreateMonumentScreen(
    viewModel: CreateMonumentViewModel = hiltViewModel(),
    mapScreenViewModel: MapScreenViewModel,
    onNavigateBack: () -> Unit,
    userLocation: Location?,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.verificarNivelUsuario()
    }

    when (state) {
        is CreateMonumentViewModel.CreateMonumentUiState.Restricted -> {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Necesitas ser nivel 5 o superior para crear un monumento.",
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateBack) {
                    Text("Volver al inicio")
                }
            }
        }

        is CreateMonumentViewModel.CreateMonumentUiState.CanCreate -> {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .padding(innerPadding),
            ) {
                Text(
                    "¡Puedes agregar tu propio monumento!",
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "¡Recuerda que se tomará tu ubicación actual!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.nombre = it },
                    label = { Text("Nombre del monumento") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = { viewModel.descripcion = it },
                    label = { Text("Descripcion") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.crearMonumento(userLocation) },
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text("Crear monumento")
                }
            }
        }

        is CreateMonumentViewModel.CreateMonumentUiState.Error -> {
            val message = (state as CreateMonumentViewModel.CreateMonumentUiState.Error).message
            Text("Error: $message", color = Color.Red)
        }

        is CreateMonumentViewModel.CreateMonumentUiState.Created -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "¡Monumento creado exitosamente!", Toast.LENGTH_SHORT).show()
                delay(1500)
                onNavigateBack()
            }
        }

        CreateMonumentViewModel.CreateMonumentUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(32.dp))
        }
    }
}
