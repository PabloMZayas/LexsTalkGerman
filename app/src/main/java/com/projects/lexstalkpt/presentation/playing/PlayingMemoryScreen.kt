package com.projects.lexstalkpt.presentation.playing

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.InitMediaPlayerBackground
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingScreenMemory(selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    InitMediaPlayerBackground()
    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally), "Encuentra los pares \uD83C\uDDF2\uD83C\uDDFD / \uD83C\uDDE9\uD83C\uDDEA")
        Spacer(modifier = Modifier.size(15.dp))
        ShowCards(selectionsViewModel, Modifier)
    }
}

@Composable
fun ShowCards(selectionsViewModel: SelectionsViewModel, modifier: Modifier) {
    val lastWordsOpened: MutableList<String> = mutableListOf()
    val vocabularyList = selectionsViewModel.myVocabularyList.shuffled().take(6)
    val myCompleteVocabularyList = mutableListOf<String>()
    var rightAnswer = ""

    vocabularyList.forEach {
        myCompleteVocabularyList.add(it[0])
        myCompleteVocabularyList.add(it[1])
    }
    val myShuffledList = myCompleteVocabularyList.shuffled()
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2), content = {
        items(myShuffledList) { word ->
            vocabularyList.forEachIndexed { _, words ->
                if (words[0] == word) rightAnswer = words[1]
                if (words[1] == word) rightAnswer = words[0]
            }
            ItemMemory(word, rightAnswer, lastWordsOpened)
        }
    })
}

@Composable
fun ItemMemory(word: String, rightWord: String, lastWordsOpened: MutableList<String>) {
    var isOpen by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Card(Modifier
            .clickable {
                isOpen = !isOpen
                lastWordsOpened.add(word)
                if (lastWordsOpened.size == 2) {
                    if (lastWordsOpened[0] == rightWord)
                        Toast.makeText(context, "COOL", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                    lastWordsOpened.clear()
                }
            }
            .padding(horizontal = 5.dp, vertical = 15.dp),
            colors = CardDefaults.cardColors(
            containerColor = Color.Cyan
    )
    ) {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (isOpen) Text(text = word.uppercase(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 14.sp)
            if (!isOpen) Text(text = "?", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 20.sp)
        }
    }
}
