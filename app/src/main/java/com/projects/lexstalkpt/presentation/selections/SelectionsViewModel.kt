package com.projects.lexstalkpt.presentation.selections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.lexstalkpt.data.lessons.LessonItem
import com.projects.lexstalkpt.domain.ProvideIntroductionsLessonsUseCase
import com.projects.lexstalkpt.domain.ProvideVocabularyFromTXTUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionsViewModel @Inject constructor(
        private val provideVocabularyFromTXTUseCase: ProvideVocabularyFromTXTUseCase,
        private val provideIntroductionsLessonsUseCase: ProvideIntroductionsLessonsUseCase
) : ViewModel() {

    var myVocabularyList by mutableStateOf(listOf<List<String>>())
        private set

    var myIntroduction by mutableStateOf(listOf<String>())
        private set

    var lives by mutableStateOf(0)
        private set

    var rightHits by mutableStateOf(0)
        private set

    var modeSelected by mutableStateOf(0)
        private set

    private var _lessonItemSelected = MutableLiveData<LessonItem>()
    val lessonItemSelected get() = _lessonItemSelected

    fun setSectionSelected(lessonItem: LessonItem) {
        _lessonItemSelected.value = lessonItem
    }

    fun setSelectedMode(mode: Int) { modeSelected = mode }

    fun setVocabulary() {
        val key = _lessonItemSelected.value!!.lessonVocabularyKey
        myVocabularyList = provideVocabularyFromTXTUseCase.provideVocabularyFromTxt(key)
    }

    fun setIntroduction() {
        val key = _lessonItemSelected.value!!.lessonIntroductionKey
        myIntroduction = provideIntroductionsLessonsUseCase.provideIntroductionFromTxt(key)
    }

    fun increaseRightHits() { rightHits += 1 }

    fun restartRightHits() { rightHits = 0 }

    fun decreaseLife() { lives -= 1 }

    fun restartLives() { lives = 2 }
}
