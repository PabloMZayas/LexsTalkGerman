package com.projects.lexstalkpt.presentation.playing

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.Routes
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingTypeWord(navController: NavHostController,
                       selectionsViewModel: SelectionsViewModel) {

    val myListVocabulary = selectionsViewModel.myVocabularyList.map {  listWords ->
        listWords.map {  word ->
            word.trim().lowercase()
        }
    }
    val shuffledList by remember { mutableStateOf(myListVocabulary.shuffled()) }
    val rightAnswers by remember { mutableStateOf(shuffledList[0]) }
    var userAnswer by remember { mutableStateOf("") }

    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally), "Escribe la palabra correcta")
        Spacer(modifier = Modifier.size(15.dp))
        CardShowWord(rightAnswers[1])
        Spacer(modifier = Modifier.size(35.dp))
        ImageLessonPlaying(selectionsViewModel)
        Spacer(modifier = Modifier.size(35.dp))
        TextFieldTypeWord() { userAnswer = it.trim().lowercase() }
        Spacer(modifier = Modifier.size(45.dp))
        ButtonCheckAnswerTypeWord(rightAnswers, userAnswer, navController, selectionsViewModel)
    }
}

@Composable
fun ButtonCheckAnswerTypeWord(rightAnswers: List<String>, userAnswer: String, navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    val context = LocalContext.current
    Button(onClick = {
        if (rightAnswers.contains(userAnswer)) //showHit(context, selectionsViewModel, navController)
        {
            selectionsViewModel.increaseRightHits()
            val rightHits = selectionsViewModel.rightHits
            if (rightHits > 9) {
                navigateToWinnerDialog(navController)
            } else {
                initMediaPlayer(R.raw.correct, context)
                Toast.makeText(context, "Bien hecho", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.PlayingTypeWord.route) { popUpTo(Routes.PlayingTypeWord.route) { inclusive = true } }
                //navigateToNextQuestion(selectionsViewModel, navController)
            }
        }
        else showError(context, selectionsViewModel, navController)
    }, Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)) {
        Text(text = "COMPROBAR", textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldTypeWord(onWordTyped: (String) -> Unit) {
    var word by remember { mutableStateOf("") }
    OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = word,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onValueChange = {
        word = it
        onWordTyped(word)
    })
}
