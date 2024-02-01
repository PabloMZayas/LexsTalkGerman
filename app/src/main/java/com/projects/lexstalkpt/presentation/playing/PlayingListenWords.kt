package com.projects.lexstalkpt.presentation.playing

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingListenWordsScreen(selectionsViewModel: SelectionsViewModel,
                             navController: NavHostController, readTextOutLoud: (String) -> Unit) {
    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel, 6)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally), "¿Qué palabra escuchas?")
        Spacer(modifier = Modifier.size(15.dp))
        ShowICanTHearNow()
        Spacer(modifier = Modifier.size(15.dp))
        ShowWordsToListenAndRead(selectionsViewModel, Modifier, readTextOutLoud)
    }
}

@Composable
fun ShowWordsToListenAndRead(selectionsViewModel: SelectionsViewModel, modifier: Modifier, readTextOutLoud: (String) -> Unit) {
    val vocabularyList by rememberSaveable { mutableStateOf(selectionsViewModel.myVocabularyList.shuffled().take(6)) }
    val context = LocalContext.current

    val wordsToRead by rememberSaveable { mutableStateOf(vocabularyList.map { it[1] }) }
    val wordsToListen by rememberSaveable { mutableStateOf(vocabularyList.map { it[0] }) }

    val wordsToReadShuffled by rememberSaveable { mutableStateOf(wordsToRead.shuffled()) }
    val wordsToListenShuffled by rememberSaveable { mutableStateOf(wordsToListen.shuffled()) }

    var selectedIndexGerman by rememberSaveable { mutableStateOf(-1) }
    var selectedIndexSpanish by rememberSaveable { mutableStateOf(-1) }

    var wordGermanSelected by rememberSaveable() { mutableStateOf("") }
    var wordSpanishSelected by rememberSaveable() { mutableStateOf("") }

    Row(modifier = modifier.fillMaxWidth()) {
        LazyColumn(modifier.weight(1f), content = {
            items(6) { index ->
                ItemReadWord(
                        modifier = Modifier.weight(1f),
                        word = wordsToListenShuffled[index],
                        isSelected = selectedIndexGerman == index,
                        isMatched = false,
                        ) {
                    selectedIndexGerman = index
                    wordGermanSelected = it
                    vocabularyList.forEach { words ->
                        if (words.contains(wordGermanSelected) && words.contains(wordSpanishSelected)) {
                            Toast.makeText(context, "ger: $wordGermanSelected sp: $wordSpanishSelected", Toast.LENGTH_SHORT).show()
                        }
                    }
                    readTextOutLoud(it)
                }
            }
        })

        LazyColumn(modifier.weight(1f), content = {
            items(6) { index ->
                ItemReadWord(
                        modifier = Modifier.weight(1f),
                        word = wordsToReadShuffled[index],
                        isSelected = selectedIndexSpanish == index,
                        isMatched = false,
                ) {
                    wordSpanishSelected = it
                    selectedIndexSpanish = index
                    vocabularyList.forEach { words ->
                        if (words.contains(wordGermanSelected) && words.contains(wordSpanishSelected)) {
                            Toast.makeText(context, "ger: $wordGermanSelected sp: $wordSpanishSelected", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }
}

@Composable
fun ItemReadWord(modifier: Modifier, word: String, isSelected: Boolean,  isMatched: Boolean, onClickListener: (String) -> Unit) {
    Card(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 5.dp)
                    .clickable(){
                        onClickListener(word)
                    },
            colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Red else if (isMatched) Color.Green else Color.Cyan,
            )
    ) {
        Column(
                Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    text = word.uppercase(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp
            )
        }
    }
}


@Composable
fun ShowICanTHearNow() {
    Text(text = "No puedo escuchar por el momento", textDecoration = TextDecoration.Underline)
}
