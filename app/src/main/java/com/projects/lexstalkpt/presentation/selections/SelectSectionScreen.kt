package com.projects.lexstalkpt.presentation.selections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.data.lessons.LessonItem
import com.projects.lexstalkpt.data.lessons.ProvideLessons
import com.projects.lexstalkpt.presentation.Lottie
import com.projects.lexstalkpt.presentation.Routes

@Composable
fun SelectSectionScreen(navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    Column(Modifier
            .fillMaxSize()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        MyHeader()
        MyLottieAnimation()
        TextSelectALesson()
        Spacer(modifier = Modifier.size(10.dp))
        MyLessonsList(Modifier
                .padding(horizontal = 10.dp)
                .weight(1f),
                navController,
                selectionsViewModel)
        Spacer(modifier = Modifier.size(10.dp))
        //BottomBar()
    }
}

@Composable
fun BottomBar() {
    var index by remember { mutableStateOf(0) }
    BottomAppBar(Modifier.height(50.dp)) {

        NavigationBarItem(selected = index == 1, onClick = { index = 1 },
                alwaysShowLabel = true,
                icon = {
                    Icon(imageVector = Icons.Default.Home,
                            contentDescription = "Inicio")
                },
                label = { Text(text = "") })
        NavigationBarItem(selected = index == 2, onClick = { index = 2 },
                alwaysShowLabel = true,
                icon = {
                    Icon(painterResource(id = R.drawable.ic_explanation),
                            contentDescription = "readings")
                },
                label = { Text(text = "") })
    }
}

@Composable
fun TextSelectALesson() {
    Text(text = "Selecciona una lección", fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
}

@Composable
fun MyLessonsList(modifier: Modifier, navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    val myItemsList = ProvideLessons().provideBeginnerLectureItems()
    LazyColumn(modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
        itemsIndexed(myItemsList) { index, item ->
            LessonItem(item, index, navController, selectionsViewModel)
        }
    }
}

@Composable
fun LessonItem(lessonItem: LessonItem, index: Int, navController: NavHostController, selectionsViewModel: SelectionsViewModel) {
    Card(Modifier.clickable {
        navController.navigate(Routes.SelectModeGameScreen.route)
        selectionsViewModel.setSectionSelected(lessonItem)
    }) {
        Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(
                    id = lessonItem.lessonDrawable),
                    contentDescription = "icon_lesson",
                    Modifier.size(30.dp))
            InfoLessonItem(lessonItem, Modifier.weight(1f), index)
        }
    }
}

@Composable
fun InfoLessonItem(lessonInfo: LessonItem, modifier: Modifier, index: Int) {
    Column(modifier = modifier.padding(start = 20.dp)) {
        Text(text = "Lección ${index + 1}",
                fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = lessonInfo.lessonTitle,
                fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = "Experiencia = ${lessonInfo.lessonProgress}",
                fontSize = 14.sp)
    }
}

@Composable
fun MyLottieAnimation() {
    Lottie(70, R.raw.monkey_hello)
}

@Composable
fun MyHeader() {
    Row() {
        Image(painterResource(id = R.drawable.germany),
                modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp),
                contentDescription = "flag_german")
        MySpinnerSelectLevel(Modifier.weight(1f))
        Icon(painterResource(id = R.drawable.manage_account),
                modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp),
                contentDescription = "flag_german")
    }
}

@Composable
fun MySpinnerSelectLevel(modifier: Modifier) {
    var selectedLevel by remember { mutableStateOf("Selecciona un nivel") }
    var isSpinnerExpanded by remember { mutableStateOf(false) }
    val levels = listOf("Principiante", "Elemental", "Intermedio", "Avanzado")

    Column(modifier = modifier
            .height(35.dp)
            .fillMaxWidth()) {
        BasicTextField(
                modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .border(BorderStroke(0.5.dp, Color.Black))
                        .align(Alignment.CenterHorizontally)
                        .clickable { isSpinnerExpanded = !isSpinnerExpanded },
                value = selectedLevel,
                onValueChange = { },
                enabled = false,
                readOnly = true) {
            Text(text = selectedLevel, modifier = Modifier.padding(4.dp), textAlign = TextAlign.Center)
        }

        DropdownMenu(expanded = isSpinnerExpanded,
                onDismissRequest = { isSpinnerExpanded = !isSpinnerExpanded },
                modifier = modifier.padding(horizontal = 75.dp)) {
            levels.forEach { level ->
                DropdownMenuItem(text = { Text(text = level) }, onClick = {
                    isSpinnerExpanded = false
                    selectedLevel = level
                }, contentPadding = PaddingValues(2.dp))
            }
        }
    }
}
