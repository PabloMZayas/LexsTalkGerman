package com.projects.lexstalkpt.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Lottie(size: Int, routeLottie: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(routeLottie))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever, speed = 0.5f)
    LottieAnimation(modifier = Modifier
            .size(size.dp)
            .padding(0.dp), composition = composition, progress = progress)
}