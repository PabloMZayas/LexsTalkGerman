package com.projects.lexstalkpt.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.playing.navigateToCards
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun Lottie(size: Int, routeLottie: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(routeLottie))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever, speed = 0.5f)
    LottieAnimation(modifier = Modifier
            .size(size.dp)
            .padding(0.dp), composition = composition, progress = progress)
}

fun initMediaPlayer(media: Int, context: Context) {
    val mediaPlayer = MediaPlayer.create(context, media)
    mediaPlayer.setVolume(0.2f, 0.2f)
    mediaPlayer.start()
}

@Composable
fun InitMediaPlayerBackground() {
    val context = LocalContext.current
    val myMedia = R.raw.playing_castle

    DisposableEffect(context) {
        val mediaPlayer = MediaPlayer.create(context, myMedia)
        mediaPlayer.setVolume(0.2f, 0.2f)
        mediaPlayer.start()

        onDispose {
            mediaPlayer.release()
        }
    }
}

@Composable
fun MySpacer(size: Int = 20) {
    Spacer(modifier = Modifier.size(size.dp))
}

@Composable
fun MySimpleImage(@DrawableRes drawable: Int, size: Int) {
    Image(modifier = Modifier.size(size.dp),
            painter = painterResource(id = drawable),
            contentDescription = "simple image")
}
