package ar.edu.unlam.scaffoldingandroid3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ar.edu.unlam.scaffoldingandroid3.data.local.PhotoEntity
import ar.edu.unlam.scaffoldingandroid3.ui.theme.BackgroundEnd
import ar.edu.unlam.scaffoldingandroid3.ui.theme.BackgroundStart
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun ImageCard(
    modifier: Modifier = Modifier.fillMaxWidth().height(400.dp),
    photo: PhotoEntity,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
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
        Image(
            painter = rememberAsyncImagePainter(model = File(photo.filePath)),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}
