package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.AlbumViewModel
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val photos by viewModel.photos.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(innerPadding),
    ) {
        items(photos) { photo ->
            Image(
                painter = rememberAsyncImagePainter(model = File(photo.filePath)),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
