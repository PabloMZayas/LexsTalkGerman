package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel


@Composable
fun ShowTimeAndMoney() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        LottieClock()
        Text(text = "00:00")
        Spacer(modifier = Modifier.size(50.dp))
        LottieCoins()
        Text(text = "10.50")
    }
}

@Composable
fun LottieCoins() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.coin_spin))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever, speed = 0.5f)
    LottieAnimation(modifier = Modifier.size(40.dp).padding(0.dp), composition = composition, progress = progress)
}

@Composable
fun LottieClock() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.sand_clock))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
    LottieAnimation(modifier = Modifier.size(40.dp).padding(0.dp), composition = composition, progress = progress)
}

@Composable
fun ShowProgress(selectionsViewModel: SelectionsViewModel) {
    val rightHits = selectionsViewModel.rightHits
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        LinearProgressIndicator(progress = rightHits.toFloat() / 10, Modifier.weight(1f))
        Text(text = "$rightHits/10", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun ShowLives(selectionsViewModel: SelectionsViewModel) {
    val lives = selectionsViewModel.lives
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        LifeForChallenge(lives = lives, numberOfIcon = 0)
        Spacer(modifier = Modifier.size(10.dp))
        LifeForChallenge(lives = lives, numberOfIcon = 1)
        Spacer(modifier = Modifier.size(50.dp))
        IconButton(onClick = {  }) { Icon(painter = painterResource(id = R.drawable.ic_sound_mute), contentDescription = "") }
    }
}

@Composable
fun LifeForChallenge(lives: Int, numberOfIcon: Int) {
    Icon(painter = painterResource(id = R.drawable.heart_one), contentDescription = "heart",
            tint = if (lives > numberOfIcon) Color.Red else Color.Black)
}

@Composable
fun TextInstructions(modifier: Modifier, instruction: String) {
    Text(modifier = modifier.fillMaxWidth(),
            text = instruction,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold)
}
