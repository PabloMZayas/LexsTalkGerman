package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun WinnerScreen(navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    selectionsViewModel.restartRightHits()
    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        TextWinnerOrLoser(true)
        Spacer(modifier = Modifier.size(30.dp))
        LottieWinnerOrLoser(true)
        Spacer(modifier = Modifier.size(30.dp))
        ButtonAccept(navController)
    }
}

@Composable
fun ButtonAccept(navController: NavHostController) {
    val context = LocalContext.current
    Button(modifier = Modifier.fillMaxWidth(), onClick = {
        navController.navigateUp()
        navController.navigateUp()
    }) {
        Text(text = "Continuar")
    }
}

@Composable
fun LottieWinnerOrLoser(isWinner: Boolean) {
    val lottieToShow = if (isWinner) R.raw.mariachi else R.raw.donkey_one
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieToShow))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
    LottieAnimation(modifier = Modifier.size(140.dp), composition = composition, progress = progress)
}

@Composable
fun TextWinnerOrLoser(isWinner: Boolean) {
    Text(modifier = Modifier.fillMaxWidth(),
            text = if (isWinner) "¡FELICIDADES, HAS GANADO!" else "¡HAS PERDIDO, ANCIANO!",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            textAlign = TextAlign.Center)
}
