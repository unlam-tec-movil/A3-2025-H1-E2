package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.scaffoldingandroid3.ui.components.ImageCard
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.AlbumViewModel

@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val photos by viewModel.photos.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding).background(color = Color.Black),
    ) {
        items(photos) { photo ->
            ImageCard(photo = photo)
        }
    }
}
