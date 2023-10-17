package com.projects.lexstalkpt.presentation.playing

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.projects.lexstalkpt.presentation.Routes
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingCardsScreen(navController: NavHostController,
                       selectionsViewModel: SelectionsViewModel,
                       readTextOutLoud: (String) -> Unit) {

    val myListVocabulary = selectionsViewModel.myVocabularyList
    val shuffledList by remember { mutableStateOf(myListVocabulary.shuffled()) }
    val myOptions by remember { mutableStateOf(listOf(shuffledList[0], shuffledList[1], shuffledList[2], shuffledList[3]).shuffled()) }
    val rightAnswer by remember { mutableStateOf(shuffledList[0][1]) }
    var userAnswer by remember { mutableStateOf("") }

    Column(Modifier
            .fillMaxSize()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(15.dp))
        CardShowWord(rightAnswer)
        Spacer(modifier = Modifier.size(35.dp))
        ImageLessonPlaying(selectionsViewModel)
        Spacer(modifier = Modifier.size(35.dp))
        OptionsButtons(myOptions) {
            userAnswer = it
            readTextOutLoud(it)
        }
        Spacer(modifier = Modifier.size(45.dp))
        ButtonCheckAnswer(shuffledList[0], userAnswer, navController, selectionsViewModel)
    }
}

@Composable
fun ImageLessonPlaying(selectionsViewModel: SelectionsViewModel) {
    val lessonItemSelected by selectionsViewModel.lessonItemSelected.observeAsState()

    Image(painter = painterResource(id = lessonItemSelected!!.lessonDrawable),
            contentDescription = "", modifier = Modifier.size(60.dp))
}

@Composable
fun ButtonCheckAnswer(listOptions: List<String>, userAnswer: String, navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    val context = LocalContext.current
    Button(onClick = {
        if (listOptions.contains(userAnswer)) showHit(context, selectionsViewModel, navController)
        else showError(context, selectionsViewModel, navController)
    }, Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)) {
        Text(text = "COMPROBAR", textAlign = TextAlign.Center)
    }
}

fun showError(context: Context, selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    selectionsViewModel.decreaseLife()
    if (selectionsViewModel.lives < 1) navController.navigate(Routes.LoserScreen.route) { popUpTo(Routes.LoserScreen.route) { inclusive = true } }
    Toast.makeText(context, "Intenta de nuevo", Toast.LENGTH_SHORT).show()
}

fun showHit(context: Context, selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    selectionsViewModel.increaseRightHits()
    val rightHits = selectionsViewModel.rightHits
    if (rightHits > 9) {
        navController.navigate(Routes.WinnerScreen.route) { popUpTo(Routes.WinnerScreen.route) { inclusive = true } }
    } else {
        Toast.makeText(context, "Bien hecho", Toast.LENGTH_SHORT).show()
        navController.navigate(Routes.PlayingCardsScreen.route) { popUpTo(Routes.PlayingCardsScreen.route) { inclusive = true } }
    }
}

@Composable
fun OptionsButtons(myShuffledList: List<List<String>>,
                   function: (String) -> Unit) {

    var optionSelected by remember { mutableStateOf(0) }
    Row(Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp)) {
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[0][0], 1, optionSelected) {
            optionSelected = 1
            function(myShuffledList[0][0])
        }
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[1][0], 2, optionSelected) {
            optionSelected = 2
            function(myShuffledList[1][0])
        }
    }

    Row(Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp)) {
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[2][0], 3, optionSelected) {
            optionSelected = 3
            function(myShuffledList[2][0])
        }
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[3][0], 4, optionSelected) {
            optionSelected = 4
            function(myShuffledList[3][0])
        }
    }
}

@Composable
fun ButtonOption(modifier: Modifier, text: String, optionSelected: Int, optionButton: Int, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            containerColor = if (optionSelected == optionButton) Color(0xFF6D167E) else Color(0xFFB8C2E0),
            contentColor = if (optionSelected == optionButton) Color(0xFFFDFAFE) else Color(0xFF010512),
    )) {
        Text(text = text.uppercase())
    }
}

@Composable
fun TextInstructions(modifier: Modifier) {
    Text(modifier = modifier.fillMaxWidth(),
            text = "Selecciona el recuadro correcto",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold)
}

@Composable
fun CardShowWord(rightAnswer: String) {
    Card(Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(180.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB8C2E0)
    )) {
        Text(text = rightAnswer.uppercase(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 75.dp),
                fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

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

    LottieAnimation(
            modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp),
            composition = composition,
            progress = progress
    )
}

@Composable
fun LottieClock() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.sand_clock))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
            modifier = Modifier
                    .size(40.dp)
                    .padding(0.dp),
            composition = composition,
            progress = progress
    )
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
