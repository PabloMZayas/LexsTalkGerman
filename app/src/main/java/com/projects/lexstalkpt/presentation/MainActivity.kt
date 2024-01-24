package com.projects.lexstalkpt.presentation

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.lexstalkpt.presentation.playing.PlayingCardsScreen
import com.projects.lexstalkpt.presentation.playing.WinnerScreen
import com.projects.lexstalkpt.presentation.playing.LoserScreen
import com.projects.lexstalkpt.presentation.playing.PlayingTypeWord
import com.projects.lexstalkpt.presentation.selections.SelectModeGameScreen
import com.projects.lexstalkpt.presentation.selections.SelectSectionScreen
import com.projects.lexstalkpt.presentation.selections.SelectionsViewModel
import com.projects.lexstalkpt.presentation.studying.LessonIntroductionScreen
import com.projects.lexstalkpt.presentation.studying.LessonVocabularyScreen
import com.projects.lexstalkpt.ui.theme.LexsTalkPtTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var ttsForPlaying: TextToSpeech
    private val selectionsViewModel: SelectionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTtsForPlaying()
        setContent {
            LexsTalkPtTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                            startDestination = Routes.SelectSectionScreen.route){
                        composable(Routes.SelectSectionScreen.route) {
                            SelectSectionScreen(navController, selectionsViewModel)
                        }
                        composable(Routes.SelectModeGameScreen.route) {
                            selectionsViewModel.restartLives()
                            selectionsViewModel.restartRightHits()
                            SelectModeGameScreen(navController, selectionsViewModel)
                        }
                        composable(Routes.LessonVocabularyScreen.route) {
                            LessonVocabularyScreen(navController, selectionsViewModel) { readTextOutLoud(it) }
                        }
                        composable(Routes.LessonIntroductionScreen.route) {
                            LessonIntroductionScreen(navController, selectionsViewModel)
                        }
                        composable(Routes.PlayingCardsScreen.route) {
                            PlayingCardsScreen(navController, selectionsViewModel) { readTextOutLoud (it) }
                        }
                        composable(Routes.PlayingTypeWord.route) {
                            PlayingTypeWord(navController, selectionsViewModel)
                        }
                        composable(Routes.WinnerScreen.route) {
                            WinnerScreen(navController, selectionsViewModel)
                        }
                        composable(Routes.LoserScreen.route) {
                            LoserScreen(navController, selectionsViewModel)
                        }
                    }
                }
            }
        }
    }

    private fun readTextOutLoud(it: String) {
        ttsForPlaying.speak(it, TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())
    }

    private fun initTtsForPlaying() {
        ttsForPlaying = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsForPlaying.language = Locale.GERMAN
            }
        }
    }
}
