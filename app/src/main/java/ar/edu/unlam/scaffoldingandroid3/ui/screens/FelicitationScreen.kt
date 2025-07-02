package ar.edu.unlam.scaffoldingandroid3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.scaffoldingandroid3.R
import ar.edu.unlam.scaffoldingandroid3.ui.viewmodel.FelicitationViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun FelicitationScreen(
    viewModel: FelicitationViewModel = hiltViewModel(),
    onContinue: () -> Unit,
) {
    val puedeContinuar by viewModel.animacionTerminada.collectAsState()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
    )

    var animarIcono by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        animarIcono = true
    }

    val iconSize by animateDpAsState(
        targetValue = if (animarIcono) 64.dp else 200.dp,
        animationSpec = tween(durationMillis = 1200),
    )
    val iconOffSetY by animateDpAsState(
        targetValue = if (animarIcono) (-180).dp else 0.dp,
        animationSpec = tween(durationMillis = 1200),
    )

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier =
                Modifier.fillMaxSize(),
        )

        Icon(
            Icons.Default.CheckCircle,
            tint = Color(0xFF4CAF50),
            contentDescription = "Check",
            modifier =
                Modifier
                    .size(iconSize)
                    .align(Alignment.Center)
                    .offset(y = iconOffSetY),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 24.dp).offset(y = 80.dp),
        ) {
            Text(
                text = "¡Felicitaciones!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00796B),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Has cazado el monumento:\n${viewModel.monumento}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "+${viewModel.puntos} puntos",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFD32F2F),
            )
            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(visible = puedeContinuar) {
                Button(onClick = onContinue) {
                    Text("Volver al mapa")
                }
            }
        }
    }
}
