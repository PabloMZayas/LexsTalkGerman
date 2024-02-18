package com.projects.lexstalkpt.presentation.studying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.MySimpleImage
import com.projects.lexstalkpt.presentation.MySpacer
import com.projects.lexstalkpt.presentation.Routes
import com.projects.lexstalkpt.presentation.selections.ImageLesson
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun LessonIntroductionScreen(navController: NavController,
                             selectionsViewModel: SelectionsViewModel,
                             readTextOutLoudGerman: (String) -> Unit,
                             readTextOutLoudSpanish: (String) -> Unit) {
    selectionsViewModel.setIntroduction()

    var isGerman by rememberSaveable { mutableStateOf(false) }
    var isListening by rememberSaveable { mutableStateOf(false) }

    Column(Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        IconChangeToGerman({ isGerman = !isGerman }, { isListening = !isListening }, isListening)
        TitleLesson(selectionsViewModel = selectionsViewModel)
        Spacer(modifier = Modifier.size(20.dp))
        ImageLesson(selectionsViewModel = selectionsViewModel)
        Spacer(modifier = Modifier.size(10.dp))
        ShowLesson(selectionsViewModel, isGerman, isListening, readTextOutLoudGerman, readTextOutLoudSpanish)
        Spacer(modifier = Modifier.size(20.dp))
        GoToVocabulary(navController)
    }
}

@Composable
fun IconChangeToGerman(onChangeLanguage: () -> Unit, onListenLesson: () -> Unit, isListening: Boolean) {
    Row(modifier = Modifier
            .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        MySpacer(55)
        MySimpleImage(drawable = R.drawable.icon_flag_germany, size = 30)
        MySpacer(15)
        Text(modifier = Modifier.clickable {
            onChangeLanguage()
        }, text = "Traducir", textDecoration = TextDecoration.Underline, textAlign = TextAlign.Center)
        MySpacer(15)
        MySimpleImage(drawable = R.drawable.icon_flag_mexico_circle, size = 30)
        MySpacer(15)
        IconButton(onClick = { onListenLesson() }) {
            MySimpleImage(drawable = if (isListening) R.drawable.ic_sound_mute else R.drawable.ic_sound_unmute,
                    size = 30)
        }
    }
}

@Composable
fun GoToVocabulary(navController: NavController) {
    Button(onClick = {
        navController.navigate(Routes.LessonVocabularyScreen.route)
    }) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "VOCABULARIO",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                            .padding(vertical = 4.dp)
                            .weight(1f))
            Icon(painter = painterResource(id = R.drawable.icon_globalization), contentDescription = "iconButton")
        }
    }
}

@Composable
fun ShowLesson(selectionsViewModel: SelectionsViewModel, isGerman: Boolean, isListening: Boolean, readTextOutLoudGerman: (String) -> Unit, readTextOutLoudSpanish: (String) -> Unit) {
    val myIntroduction = if (isGerman) selectionsViewModel.myGermanIntroduction else selectionsViewModel.myIntroduction
    var fullIntroduction = ""
    myIntroduction.forEach {
        fullIntroduction += "$it\n\n"
    }
    if (isListening) {
        if (isGerman) readTextOutLoudGerman(fullIntroduction)
        else readTextOutLoudSpanish(fullIntroduction)
    } else {
        readTextOutLoudGerman("")
        readTextOutLoudSpanish("")
    }
    TextBoldAsterisks(tuString = fullIntroduction, 20)
}

@Composable
fun TextBoldAsterisks(tuString: String, fontSize: Int = 0, isJustified: Boolean = true) {
    val annotatedString = buildAnnotatedString {
        val boldChar = '*'

        tuString.split(boldChar).forEachIndexed { index, substring ->
            if (index % 2 == 0) {
                withStyle(style = SpanStyle(
                        fontSize = LocalTextStyle.current.fontSize,
                        fontFamily = LocalTextStyle.current.fontFamily,
                        color = LocalTextStyle.current.color
                )) {
                    append(substring)
                }
            } else {
                withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = if (fontSize != 0) fontSize.sp else LocalTextStyle.current.fontSize,
                        fontFamily = LocalTextStyle.current.fontFamily,
                        color = LocalTextStyle.current.color
                )) {
                    append(substring)
                }
            }
        }
    }

    Text(
            text = annotatedString,
            textAlign = if (isJustified) TextAlign.Justify else TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.type_wr))
    )
}

