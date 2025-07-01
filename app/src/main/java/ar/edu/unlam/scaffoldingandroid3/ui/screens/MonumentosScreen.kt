package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.scaffoldingandroid3.ui.components.MonumentCard
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.MonumentosScreenViewModel

@Composable
fun MonumentosScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MonumentosScreenViewModel = hiltViewModel(),
) {
    val monumentos = viewModel.monumentos

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(80.dp))
        LazyColumn(
            modifier = Modifier.size(700.dp).background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                monumentos.forEach {
                    MonumentCard(it)
                }
            }
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}
