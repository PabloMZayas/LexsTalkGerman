package com.projects.lexstalkpt.presentation.studying

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun LessonVocabularyScreen(navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    selectionsViewModel.setVocabulary()
    Column(Modifier
            .fillMaxSize()
            .padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TitleLesson(selectionsViewModel = selectionsViewModel)
        ListVocabulary(selectionsViewModel, Modifier.weight(1f))
        ButtonGoToChallenge()
    }
}

@Composable
fun ButtonGoToChallenge() {
    Button(modifier = Modifier.fillMaxWidth(), onClick = {  }) {
        Text(text = "Ir al desafío")
    }
}

@Composable
fun ListVocabulary(selectionsViewModel: SelectionsViewModel, modifier: Modifier) {
    val vocabularyList = selectionsViewModel.myVocabularyList
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2), content = {
        items(vocabularyList) { itemVocabulary ->
            ItemVocabulary(itemVocabulary)
        }
    })
}

@Composable
fun ItemVocabulary(itemVocabulary: List<String>) {
    Card(Modifier.padding(5.dp)) {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = itemVocabulary[0].trim(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Text(text = itemVocabulary[1].trim(), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun TitleLesson(selectionsViewModel: SelectionsViewModel) {
    val lessonItemSelected by selectionsViewModel.lessonItemSelected.observeAsState()

    Text(modifier = Modifier.padding(horizontal = 40.dp),
            text = lessonItemSelected!!.lessonTitle,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center)
    Text(text = "Presiona la palabra para escucharla",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp),
            textAlign = TextAlign.Center)
}