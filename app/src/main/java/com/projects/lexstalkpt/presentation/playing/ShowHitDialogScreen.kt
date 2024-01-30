package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.MySimpleImage
import com.projects.lexstalkpt.presentation.MySpacer
import com.projects.lexstalkpt.presentation.navigateToCards
import com.projects.lexstalkpt.presentation.navigateToIntroduction
import com.projects.lexstalkpt.presentation.navigateToMemoryGame
import com.projects.lexstalkpt.presentation.navigateToTypeWord
import com.projects.lexstalkpt.presentation.navigateToVocabulary
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun DialogHit(show: Boolean, selectionsViewModel: SelectionsViewModel, navController: NavHostController, gameMode: Int = 0) {
    if (show) {
        Dialog(onDismissRequest = {  }) {
            Card(modifier = Modifier.border(1.dp, Color.Red)) {
                Column(modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom) {
                    HeaderShowHitDialog()
                    MySpacer(30)
                    ButtonAcceptShowHit(selectionsViewModel, gameMode, navController)
                }
            }
        }
    }
}

@Composable
fun HeaderShowHitDialog() {
    Row(Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
        MySimpleImage(drawable = R.drawable.icon_food_one, size = 45)
        MySpacer(15)
        Text(modifier = Modifier.weight(1f),
                text = "Bien hecho",
                fontSize = 18.sp,
                textAlign = TextAlign.Center)
        MySpacer(15)
        MySimpleImage(drawable = R.drawable.icon_food_one, size = 45)
    }
}

@Composable
fun ButtonAcceptShowHit(selectionsViewModel: SelectionsViewModel, gameMode: Int, navController: NavHostController) {
    Button(modifier = Modifier.fillMaxWidth(), onClick = {
        selectionsViewModel.hideDialogHit()
        navigateToNextQuestion(navController, gameMode)
    }) {
        Text(text = "Continuar")
    }
}

fun navigateToNextQuestion(navController: NavHostController, modeGame: Int) {
    when (modeGame) {
        0 -> { navigateToCards(navController) }
        1 -> { navigateToIntroduction(navController) }
        2 -> { navigateToVocabulary(navController) }
        4 -> { navigateToCards(navController) }
        5 -> { navigateToMemoryGame(navController) }
        7 -> { navigateToTypeWord(navController) }
    }
}
