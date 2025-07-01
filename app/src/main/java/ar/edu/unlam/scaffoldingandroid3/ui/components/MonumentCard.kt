package ar.edu.unlam.scaffoldingandroid3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.scaffoldingandroid3.domain.model.Monumento
import ar.edu.unlam.scaffoldingandroid3.ui.theme.BackgroundEnd
import ar.edu.unlam.scaffoldingandroid3.ui.theme.BackgroundStart

@Composable
fun MonumentCard(monumento: Monumento) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(BackgroundStart, BackgroundEnd),
                        start = Offset(0f, 0f),
                        end = Offset(500f, 500f),
                    ),
                    shape = RoundedCornerShape(16.dp),
                ),
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(18.dp))

            Text(
                text = monumento.name,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                style =
                    TextStyle(
                        shadow =
                            Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f,
                            ),
                    ),
            )

            Spacer(modifier = Modifier.size(30.dp))

            Text(
                text = monumento.descripcion,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
                style =
                    TextStyle(
                        shadow =
                            Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f,
                            ),
                    ),
            )

            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}
