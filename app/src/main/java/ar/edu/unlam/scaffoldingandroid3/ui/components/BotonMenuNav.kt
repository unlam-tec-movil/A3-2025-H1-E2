package ar.edu.unlam.scaffoldingandroid3.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.scaffoldingandroid3.data.navigation.NavigationRoutes

@Composable
fun BotonMenuNav(
    controller: NavController,
    ruta: NavigationRoutes,
    icon: ImageVector,
    descripcion: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { controller.navigate(ruta.route) },
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            colors =
                ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray,
                ),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "NavButton",
                modifier = Modifier.size(35.dp),
            )
        }
        Text(
            text = descripcion,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}
