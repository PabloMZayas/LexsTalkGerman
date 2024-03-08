package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.presentation.MySpacer
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingListenWordsScreen(selectionsViewModel: SelectionsViewModel,
                             navController: NavHostController,
                             readTextOutLoud: (String) -> Unit) {
    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel, 6)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        MySpacer(15)
        TextInstructions(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                instruction = "Une las palabras correspondientes"
        )
        MySpacer(15)
        ShowWords(selectionsViewModel, Modifier, readTextOutLoud)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowWords(selectionsViewModel: SelectionsViewModel,
              modifier: Modifier,
              readTextOutLoud: (String) -> Unit) {
    val vocabularyList by rememberSaveable { mutableStateOf(selectionsViewModel.myVocabularyList.shuffled().take(6)) }
    val listItemCardsGerman = getGermanCardsForGame(vocabularyList)
    val listItemCardsSpanish = getSpanishCardsForGame(vocabularyList)

    Row(modifier = modifier.fillMaxWidth()) {
        LazyColumn(modifier.weight(1f), content = {
            listItemCardsGerman.forEach { cardItem ->
                stickyHeader {
                    NewCardItem(cardItem, modifier, readTextOutLoud, true)
                }
            }
        })

        LazyColumn(modifier.weight(1f), content = {
            listItemCardsSpanish.forEach { cardItem ->
                stickyHeader {
                    NewCardItem(cardItem, modifier, readTextOutLoud, false)
                }
            }
        })
    }
}

@Composable
fun NewCardItem(cardItem: CardItem, modifier: Modifier, readTextOutLoud: (String) -> Unit, isGerman: Boolean) {
    Card(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 5.dp)
            .clickable {
                cardItem.onSelectedChange(!cardItem.isSelected)
                cardItem.onFoundChange(!cardItem.isFound)
                if (isGerman) readTextOutLoud(cardItem.word)
            }, colors = CardDefaults.cardColors(
            containerColor = if (cardItem.isFound) Color.Green else if (cardItem.isSelected) Color.LightGray else Color.Cyan
    )
    ) {
        Column(
                Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = cardItem.word, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun getGermanCardsForGame(vocabularyList: List<List<String>>): List<CardItem> {
    return vocabularyList.map { listString ->
        var isSelected by rememberSaveable { mutableStateOf(false) }
        var isFound by rememberSaveable { mutableStateOf(false) }
        CardItem(
                word = listString[0],
                isSelected = isSelected,
                isFound = isFound,
                onSelectedChange = { isSelected = it },
                onFoundChange = { isFound = it }
        )
    }
}

@Composable
fun getSpanishCardsForGame(vocabularyList: List<List<String>>): List<CardItem> {
    return vocabularyList.map { listString ->
        var isSelected by rememberSaveable { mutableStateOf(false) }
        var isFound by rememberSaveable { mutableStateOf(false) }
        CardItem(
                listString[1],
                isSelected = isSelected,
                isFound = isFound,
                onSelectedChange = { isSelected = it },
                onFoundChange = { isFound = it }
        )
    }
}