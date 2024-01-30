package com.projects.lexstalkpt.presentation.studying

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.selections.ImageLesson
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun LessonVocabularyScreen(navController: NavHostController,
                           selectionsViewModel: SelectionsViewModel,
                           readTextOutLoud: (String) -> Unit) {
    selectionsViewModel.setVocabulary()
    Column(Modifier
            .fillMaxSize()
            .padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TitleLesson(selectionsViewModel = selectionsViewModel)
        InstructionGame()
        Spacer(modifier = Modifier.size(15.dp))
        ImageLesson(selectionsViewModel = selectionsViewModel)
        Spacer(modifier = Modifier.size(15.dp))
        ListVocabulary(selectionsViewModel, Modifier.weight(1f), readTextOutLoud)
        ButtonGoToChallenge()
    }
}

@Composable
fun InstructionGame() {
    Text(text = "Presiona la palabra para escucharla",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp),
            textAlign = TextAlign.Center)
}

@Composable
fun ButtonGoToChallenge() {
    Button(modifier = Modifier.fillMaxWidth(),
            onClick = {  },
            border = ButtonDefaults.outlinedButtonBorder,
            colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_red),
                    contentColor = Color.Yellow
            ),
            shape = RoundedCornerShape(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "DESAFÍO",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(1f))
            Icon(painter = painterResource(id = R.drawable.icon_swordman), contentDescription = "iconButton" )
        }
    }
}

@Composable
fun ListVocabulary(selectionsViewModel: SelectionsViewModel, modifier: Modifier, readTextOutLoud: (String) -> Unit) {
    val vocabularyList = selectionsViewModel.myVocabularyList
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2), content = {
        items(vocabularyList) { itemVocabulary ->
            ItemVocabulary(itemVocabulary, readTextOutLoud)
        }
    })
}

@Composable
fun ItemVocabulary(itemVocabulary: List<String>, readTextOutLoud: (String) -> Unit) {
    var isAnimationFinished by rememberSaveable { mutableStateOf(true) }
    var firstColor by rememberSaveable { mutableStateOf(false) }
    val realColor by animateColorAsState(
            targetValue = if (firstColor) Color.Green else Color.Cyan,
            animationSpec = tween(1000),
            finishedListener = {
                if (isAnimationFinished) {
                    firstColor = !firstColor
                }
                isAnimationFinished = !isAnimationFinished
            }
    )
    Card(Modifier.padding(5.dp).clickable {
                firstColor = !firstColor
                readTextOutLoud(itemVocabulary[0])
            }, colors = CardDefaults.cardColors(
                containerColor = realColor
            )
    ) {
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = itemVocabulary[1].trim().uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            Text(text = itemVocabulary[0].trim().uppercase(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
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
}
