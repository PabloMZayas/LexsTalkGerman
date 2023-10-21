package com.projects.lexstalkpt.presentation.selections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.presentation.Routes

@Composable
fun SelectModeGameScreen(
        navController: NavHostController,
        selectionsViewModel: SelectionsViewModel) {
    selectionsViewModel.setVocabulary()
    ObserveModeSelected(selectionsViewModel, navController)
    Column(Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(15.dp))
        TitleLesson(selectionsViewModel)
        Spacer(modifier = Modifier.size(15.dp))
        TopButtons(Modifier.align(Alignment.CenterHorizontally), selectionsViewModel)
        Spacer(modifier = Modifier.size(15.dp))
        ImageLesson(selectionsViewModel)
        Spacer(modifier = Modifier.size(15.dp))
        BottomButtons(Modifier.align(Alignment.CenterHorizontally), selectionsViewModel)
    }
}

@Composable
fun ObserveModeSelected(selectionsViewModel: SelectionsViewModel,
                        navController: NavHostController) {
    when (selectionsViewModel.modeSelected) {
        1 -> {}
        2 -> {
            navController.navigate(Routes.LessonVocabularyScreen.route)
            selectionsViewModel.setSelectedMode(0)
        }

        3 -> {
            navController.navigate(Routes.PlayingCardsScreen.route)
            selectionsViewModel.setSelectedMode(0)
        }

        7 -> {
            navController.navigate(Routes.PlayingTypeWord.route)
            selectionsViewModel.setSelectedMode(0)
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
    Text(text = "Toma una lección o escoge un desafío",
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp),
            textAlign = TextAlign.Center)
}

@Composable
fun TopButtons(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    OptionModeButton(text = "Tomar lección", modifier = modifier.fillMaxWidth(), selectionsViewModel, 1)
    OptionModeButton(text = "Vocabulario de la lección", modifier = modifier.fillMaxWidth(), selectionsViewModel, 2)
}

@Composable
fun ImageLesson(selectionsViewModel: SelectionsViewModel) {
    val lessonItemSelected by selectionsViewModel.lessonItemSelected.observeAsState()
    Image(painter = painterResource(id = lessonItemSelected!!.lessonDrawable),
            contentDescription = "lesson image", Modifier.size(100.dp))
}

@Composable
fun BottomButtons(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    ButtonFullChallenge(modifier, selectionsViewModel)
    CardsListeningButtons(modifier, selectionsViewModel)
    MemoryPronounceButtons(modifier, selectionsViewModel)
    WritingTraduceButtons(modifier, selectionsViewModel)
}

@Composable
fun OptionModeButton(text: String, modifier: Modifier, selectionsViewModel: SelectionsViewModel, modeGame: Int) {
    Button(modifier = modifier.padding(vertical = 4.dp),
            onClick = { selectionsViewModel.setSelectedMode(modeGame) },
            colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE9D8BF),
                    contentColor = Color.Black
            )) {
        Text(text = text.uppercase(),
                fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun ButtonFullChallenge(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    OptionModeButton(text = "desafío completo", modifier = modifier.fillMaxWidth(), selectionsViewModel, 10)
}

@Composable
fun CardsListeningButtons(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    Row(Modifier.padding(top = 10.dp)) {
        OptionModeButton(text = "Cartas",
                modifier = modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 3)
        OptionModeButton(text = "Escucha",
                modifier = modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 4)
    }
}

@Composable
fun MemoryPronounceButtons(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    Row(Modifier.padding(top = 10.dp)) {
        OptionModeButton(text = "Memoria",
                modifier = modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 5)
        OptionModeButton(text = "Pronuncia",
                modifier = modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 6)
    }
}

@Composable
fun WritingTraduceButtons(modifier: Modifier, selectionsViewModel: SelectionsViewModel) {
    Row(Modifier.padding(top = 10.dp)) {
        OptionModeButton(text = "Escritura",
                modifier = modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 7)
        OptionModeButton(text = "Traducción",
                modifier = modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                selectionsViewModel = selectionsViewModel, modeGame = 8)
    }
}
