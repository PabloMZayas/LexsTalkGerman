package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel

@Composable
fun PlayingScreenMemory(selectionsViewModel: SelectionsViewModel, navController: NavHostController) {
    Column(Modifier.fillMaxSize().padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ShowProgress(selectionsViewModel)
        ShowLives(selectionsViewModel)
        ShowTimeAndMoney()
        Spacer(modifier = Modifier.size(15.dp))
        TextInstructions(Modifier.align(Alignment.CenterHorizontally), "Encuentra los pares \uD83C\uDDF2\uD83C\uDDFD / \uD83C\uDDE9\uD83C\uDDEA")
        Spacer(modifier = Modifier.size(15.dp))
    }
}