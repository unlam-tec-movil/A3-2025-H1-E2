package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorScreen(
    modifier: Modifier,
    error: String,
) {
    Text(
        text = error,
        modifier = modifier,
        color = Color.Red,
    )
}
