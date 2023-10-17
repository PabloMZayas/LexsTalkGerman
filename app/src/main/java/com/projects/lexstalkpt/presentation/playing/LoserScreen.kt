package com.projects.lexstalkpt.presentation.playing

import androidx.compose.foundation.layout.Arrangement
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
fun LoserScreen(navController: NavHostController, selectionsViewModel:SelectionsViewModel) {
    selectionsViewModel.restartRightHits()
    Column(Modifier
            .fillMaxSize()
            .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
        TextWinnerOrLoser(false)
        Spacer(modifier = Modifier.size(30.dp))
        LottieWinnerOrLoser(false)
        Spacer(modifier = Modifier.size(30.dp))
        ButtonAccept(navController)
    }
}
