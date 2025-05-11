package ar.edu.unlam.scaffoldingandroid3.ui.screens

import android.graphics.drawable.Icon
import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ar.edu.unlam.scaffoldingandroid3.R
import kotlin.random.Random

@Composable
fun MapScreen(controller: NavHostController, modifier: Modifier = Modifier){

    Column (modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){


        //Mapa


        //Boton Menu
        //su simbolo es un "+", al presionarlo se abre el menu y cambia a un "x"

        var state: Boolean by remember { mutableStateOf(false) }

        Button(onClick = {state = !state},
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp)) {

            Icon(imageVector = if (state) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (state) "Cerrar menu" else "Abrir menu",
                tint = Color.Black)

        }




        }

}