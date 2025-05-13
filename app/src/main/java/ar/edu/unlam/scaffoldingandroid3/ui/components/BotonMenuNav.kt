package ar.edu.unlam.scaffoldingandroid3.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unlam.scaffoldingandroid3.data.navigation.NavigationRoutes

@Composable
fun BotonMenuNav(controller: NavController, ruta: NavigationRoutes, icon: ImageVector){
    Button(onClick = {controller.navigate(ruta.route)},
        shape = CircleShape,
        modifier = Modifier.size(64.dp),
        contentPadding = PaddingValues(0.dp)) {

        Icon(imageVector = icon, contentDescription = "NavButton")

    }
}