package com.projects.lexstalkpt.presentation

sealed class Routes (val route: String) {
    object SelectSectionScreen: Routes("select_section")
    object SelectModeGameScreen: Routes("select_mode_game")
    object LessonVocabularyScreen: Routes("lesson_vocabulary_screen")
    object PlayingCardsScreen: Routes("playing_cards_screen")
    object WinnerScreen: Routes("winner_screen")
    object LoserScreen: Routes("loser_screen")

    /*object Pantalla5: Routes("pantalla5?name={name}") {
        fun createRoute(name: String) = "pantalla5?name=$name"
    }*/
}