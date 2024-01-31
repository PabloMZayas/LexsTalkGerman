package com.projects.lexstalkpt.presentation

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.lexstalkpt.R

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
fun MySimpleImage(@DrawableRes drawable: Int, size: Int, mirror: Boolean = false) {
    Image(modifier =
    Modifier.size(size.dp)
            .graphicsLayer(rotationY = if (mirror) 180f else 0f),
            painter = painterResource(id = drawable),
            contentDescription = "simple image",)
}

fun navigateToVocabulary(navController: NavHostController) {
    navController.navigate(Routes.LessonVocabularyScreen.route) { popUpTo(Routes.LessonVocabularyScreen.route) { inclusive = true } }
}

fun navigateToIntroduction(navController: NavHostController) {
    navController.navigate(Routes.LessonIntroductionScreen.route) { popUpTo(Routes.LessonIntroductionScreen.route) { inclusive = true } }
}

fun navigateToCards(navController: NavHostController) {
    navController.navigate(Routes.PlayingCardsScreen.route) { popUpTo(Routes.PlayingCardsScreen.route) { inclusive = true } }
}

fun navigateToTypeWord(navController: NavHostController) {
    navController.navigate(Routes.PlayingTypeWord.route) { popUpTo(Routes.PlayingTypeWord.route) { inclusive = true } }
}

fun navigateToMemoryGame(navController: NavHostController) {
    navController.navigate(Routes.PlayingMemoryScreen.route) { popUpTo(Routes.PlayingMemoryScreen.route) { inclusive = true } }
}

fun navigateToListenWords(navController: NavHostController) {
    navController.navigate(Routes.PlayingListenWordsScreen.route) { popUpTo(Routes.PlayingListenWordsScreen.route) { inclusive = true } }
}

fun navigateToWinnerDialog(navController: NavHostController) {
    navController.navigate(Routes.WinnerScreen.route) { popUpTo(Routes.WinnerScreen.route) { inclusive = true } }
}
