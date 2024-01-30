package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
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
        Dialog(onDismissRequest = { }) {
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
                Card() {
                    Column(modifier = Modifier.border(1.dp, Color.Red)
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                        HeaderShowHitDialog()
                        MySpacer(10)
                        ButtonAcceptShowHit(selectionsViewModel, gameMode, navController)
                    }
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
        MySimpleImage(drawable = R.drawable.shocked_monkey, size = 45)
        MySpacer(15)
        Text(modifier = Modifier.weight(1f),
                text = "BIEN HECHO",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center)
        MySpacer(15)
        MySimpleImage(drawable = R.drawable.shocked_monkey, size = 45)
    }
}

@Composable
fun ButtonAcceptShowHit(selectionsViewModel: SelectionsViewModel, gameMode: Int, navController: NavHostController) {
    Button(modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_red),
                    contentColor = Color.Yellow
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
        selectionsViewModel.hideDialogHit()
        navigateToNextQuestion(navController, gameMode)
    }) {
        Text(text = "CONTINUAR")
    }
}

fun navigateToNextQuestion(navController: NavHostController, modeGame: Int) {
    when (modeGame) {
        0 -> {
            navigateToCards(navController)
        }

        1 -> {
            navigateToIntroduction(navController)
        }

        2 -> {
            navigateToVocabulary(navController)
        }

        4 -> {
            navigateToCards(navController)
        }

        5 -> {
            navigateToMemoryGame(navController)
        }

        7 -> {
            navigateToTypeWord(navController)
        }
    }
}
