package com.projects.lexstalkpt.presentation.playing

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.MySimpleImage
import com.projects.lexstalkpt.presentation.MySpacer
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel
import com.projects.lexstalkpt.presentation.studying.TextBoldAsterisks

@Composable
fun PlayingGrammaticalCasesScreen(selectionsViewModel: SelectionsViewModel,
                                  navController: NavHostController,
                                  readTextGermanOutLoud: (String) -> Unit) {

    val myOptions by rememberSaveable { mutableStateOf(listOf("genitivo", "dativo", "acusativo", "nominativo")) }
    var rightAnswer by rememberSaveable { mutableStateOf("") }
    var userAnswer by rememberSaveable { mutableStateOf("") }
    var germanSentence by rememberSaveable { mutableStateOf("") }

    ObserveIfDialogsAreShowing(selectionsViewModel, navController, mutableListOf(rightAnswer), userAnswer, 8)

    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        MySpacer(15)
        TextSpecialInstructions(selectionsViewModel) { pairRightAnswerAndGermanSentence ->
            rightAnswer = pairRightAnswerAndGermanSentence.first
            germanSentence = pairRightAnswerAndGermanSentence.second
        }
        MySpacer(5)
        ListenGermanSentence(germanSentence, readTextGermanOutLoud)
        ImageLessonPlaying(selectionsViewModel)
        MySpacer(25)
        OptionsButtonsGrammaticalCases(myOptions) {
            userAnswer = it
        }
        MySpacer(45)
        ButtonCheckAnswer(listOf(rightAnswer), userAnswer, navController, selectionsViewModel)
    }
}

@Composable
fun ListenGermanSentence(germanSentence: String, readTextGermanOutLoud: (String) -> Unit) {
    Row(Modifier.clickable { readTextGermanOutLoud(germanSentence) },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Escuchar oración", textDecoration = TextDecoration.Underline)
        MySpacer(4)
        IconButton(onClick = { readTextGermanOutLoud(germanSentence) }) {
            MySimpleImage(drawable = R.drawable.ic_sound_unmute, size = 25)
        }
    }
}

@Composable
fun OptionsButtonsGrammaticalCases(myOptions: List<String>, onClick: (String) -> Unit) {
    var optionSelected by rememberSaveable { mutableStateOf(-1) }
    for (i in 0..3) {
        ButtonOptionGrammaticalCase(text = myOptions[i], optionSelected = i, optionButton = optionSelected) {
            optionSelected = i
            onClick(myOptions[i])
        }
    }
}

@Composable
fun ButtonOptionGrammaticalCase(text: String, optionSelected: Int, optionButton: Int, onClick: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(), onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            containerColor = if (optionSelected == optionButton) Color(0xFF6D167E) else Color(0xFFB8C2E0),
            contentColor = if (optionSelected == optionButton) Color(0xFFFDFAFE) else Color(0xFF010512),
    )) {
        Text(text = text.uppercase(), fontSize = 12.sp)
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun TextSpecialInstructions(selectionsViewModel: SelectionsViewModel, onClickReturnAnswerAndGermanSentence: (Pair<String, String>) -> Unit) {
    val mySentencesList by rememberSaveable {
        mutableStateOf(selectionsViewModel.myVocabularyList.shuffled()[0].toMutableList())
    }
    var mySentence by rememberSaveable { mutableStateOf("") }

    mySentence = "¿Cuál caso gramatical corresponde a la oración: \n*" + mySentencesList[1] + "*\n(${mySentencesList[0].trim()})" + ",\nconsiderando el sustantivo *${mySentencesList[3]}*?"

    onClickReturnAnswerAndGermanSentence(Pair(mySentencesList[2].trim(), mySentencesList[0].trim()))

    TextBoldAsterisks(tuString = mySentence, 20, false)
}
