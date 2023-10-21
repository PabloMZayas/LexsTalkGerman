package com.projects.lexstalkpt.presentation.playing

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally), "Selecciona el recuadro correcto")
        Spacer(modifier = Modifier.size(15.dp))
        CardShowWord(rightAnswer)
        Spacer(modifier = Modifier.size(25.dp))
        ImageLessonPlaying(selectionsViewModel)
        Spacer(modifier = Modifier.size(25.dp))
        OptionsButtons(myOptions) {
            userAnswer = it
            readTextOutLoud(it)
        }
        Spacer(modifier = Modifier.size(25.dp))
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
        navigateToWinnerDialog(navController)
    } else {
        Toast.makeText(context, "Bien hecho", Toast.LENGTH_SHORT).show()
        //navController.navigate(Routes.PlayingTypeWord.route) { popUpTo(Routes.PlayingTypeWord.route) { inclusive = true } }
        navigateToNextQuestion(selectionsViewModel, navController)
    }
}

fun navigateToNextQuestion(selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    when (selectionsViewModel.modeSelected) {
        0 -> { navigateToCards(navController) }
        1 -> { navigateToVocabulary(navController) }
        2 -> { navigateToCards(navController) }
        7 -> { navigateToTypeWord(navController) }
    }
}

fun navigateToVocabulary(navController: NavHostController) {
    navController.navigate(Routes.LessonVocabularyScreen.route) { popUpTo(Routes.LessonVocabularyScreen.route) { inclusive = true } }
}

fun navigateToCards(navController: NavHostController) {
    navController.navigate(Routes.PlayingCardsScreen.route) { popUpTo(Routes.PlayingCardsScreen.route) { inclusive = true } }
}

fun navigateToTypeWord(navController: NavHostController) {
    navController.navigate(Routes.PlayingTypeWord.route) { popUpTo(Routes.PlayingTypeWord.route) { inclusive = true } }
}

fun navigateToWinnerDialog(navController: NavHostController) {
    navController.navigate(Routes.WinnerScreen.route) { popUpTo(Routes.WinnerScreen.route) { inclusive = true } }
}

@Composable
fun OptionsButtons(myShuffledList: List<List<String>>,
                   onClick: (String) -> Unit) {

    var optionSelected by remember { mutableStateOf(0) }
    Row(Modifier
            .padding(top = 10.dp)) {
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[0][0], 1, optionSelected) {
            optionSelected = 1
            onClick(myShuffledList[0][0])
        }
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[1][0], 2, optionSelected) {
            optionSelected = 2
            onClick(myShuffledList[1][0])
        }
    }

    Row(Modifier
            .padding(top = 10.dp)) {
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[2][0], 3, optionSelected) {
            optionSelected = 3
            onClick(myShuffledList[2][0])
        }
        ButtonOption(Modifier
                .weight(1f)
                .padding(start = 10.dp), text = myShuffledList[3][0], 4, optionSelected) {
            optionSelected = 4
            onClick(myShuffledList[3][0])
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
fun CardShowWord(rightAnswer: String) {
    Card(Modifier
            .fillMaxWidth()
            .height(140.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB8C2E0)
    )) {
        Text(text = rightAnswer.uppercase(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}
