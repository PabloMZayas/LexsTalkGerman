package com.projects.lexstalkpt.presentation.studying

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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projects.lexstalkpt.R
import com.projects.lexstalkpt.presentation.Routes
import com.projects.lexstalkpt.presentation.selections.ImageLesson
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun LessonIntroductionScreen(navController: NavController, selectionsViewModel: SelectionsViewModel) {
    selectionsViewModel.setIntroduction()

    Column(Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        TitleLesson(selectionsViewModel = selectionsViewModel)
        Spacer(modifier = Modifier.size(20.dp))
        ImageLesson(selectionsViewModel = selectionsViewModel)
        Spacer(modifier = Modifier.size(10.dp))
        ShowLesson(selectionsViewModel)
        Spacer(modifier = Modifier.size(40.dp))
        GoToVocabulary(navController)
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
fun ShowLesson(selectionsViewModel: SelectionsViewModel) {
    val myIntroduction = selectionsViewModel.myIntroduction
    var fullIntroduction = ""
    myIntroduction.forEach {
        fullIntroduction += "$it\n\n"
    }
    TextBoldAsterisks(tuString = fullIntroduction, 20)
}

@Composable
fun TextBoldAsterisks(tuString: String, fontSize: Int = 0) {
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
            textAlign = TextAlign.Justify,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.type_wr))
    )
}

