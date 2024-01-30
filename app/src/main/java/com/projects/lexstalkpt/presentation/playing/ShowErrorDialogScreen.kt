package com.projects.lexstalkpt.presentation.playing

import android.annotation.SuppressLint
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.projects.lexstalkpt.presentation.Routes
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun DialogError(show: Boolean,
                rightAnswerOptions: List<String>,
                yourAnswer: String,
                selectionsViewModel: SelectionsViewModel,
                navController: NavHostController) {
    if (show) {
        Dialog(onDismissRequest = { }) {
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
                Card() {
                    Column(modifier = Modifier
                            .border(1.dp, Color.Red)
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                        HeaderShowErrorDialog()
                        MySpacer(5)
                        ShowRightAnswer(rightAnswerOptions, yourAnswer)
                        MySpacer(5)
                        ButtonAcceptError(selectionsViewModel, navController)
                    }
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ShowRightAnswer(rightAnswerOptions: List<String>, yourAnswer: String) {
    val acceptedAnswers by rememberSaveable { mutableStateOf(mutableListOf<String>()) }
    rightAnswerOptions.forEachIndexed { index, answer ->
        if (index != 1) acceptedAnswers.add(answer)
    }
    Text(text = "Respuestas aceptadas:")
    acceptedAnswers.forEach {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            MySimpleImage(drawable = R.drawable.icon_check_blue, size = 20)
            MySpacer(5)
            Text(text = it)
        }
    }
    MySpacer(5)
    Text(text = "Tu respuesta:")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        MySimpleImage(drawable = R.drawable.ic_cross_red, size = 20)
        MySpacer(5)
        Text(text = yourAnswer)
    }
}

@Composable
fun ButtonAcceptError(selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    Button(modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_red),
                    contentColor = Color.Yellow
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                if (selectionsViewModel.lives < 1) navController.navigate(Routes.LoserScreen.route) { popUpTo(Routes.LoserScreen.route) { inclusive = true } }
                selectionsViewModel.hideErrorDialog()
            }) {
        Text(text = "ACEPTAR")
    }
}

@Composable
fun HeaderShowErrorDialog() {
    Row(Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
        MySimpleImage(drawable = R.drawable.monkeyface, size = 35, mirror = true)
        MySpacer(15)
        Text(modifier = Modifier.weight(1f),
                text = "ERROR",
                color = Color.Red,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center)
        MySpacer(15)
        MySimpleImage(drawable = R.drawable.monkeyface, size = 35)
    }
}