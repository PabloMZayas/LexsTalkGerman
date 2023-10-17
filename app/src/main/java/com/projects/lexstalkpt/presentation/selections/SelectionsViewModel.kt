package com.projects.lexstalkpt.presentation.selections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.lexstalkpt.data.lessons.LessonItem
import com.projects.lexstalkpt.domain.ProvideVocabularyFromTXTUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionsViewModel @Inject constructor(
        private val provideVocabularyFromTXTUseCase: ProvideVocabularyFromTXTUseCase
) : ViewModel() {

    var myVocabularyList by mutableStateOf(listOf<List<String>>())
        private set

    var lives by mutableStateOf(0)
        private set

    var rightHits by mutableStateOf(0)
        private set

    private var _lessonItemSelected = MutableLiveData<LessonItem>()
    val lessonItemSelected get() = _lessonItemSelected

    private var _modeSelected = MutableLiveData<Int>()
    val modeSelected get() = _modeSelected

    fun setSectionSelected(lessonItem: LessonItem) {
        _lessonItemSelected.value = lessonItem
    }

    fun setSelectedMode(mode: Int) {
        _modeSelected.value = mode
    }

    fun setVocabulary() {
        val key = _lessonItemSelected.value!!.lessonVocabularyKey
        myVocabularyList = provideVocabularyFromTXTUseCase.provideVocabularyFromTxt(key)
    }

    fun increaseRightHits() {
        rightHits += 1
    }

    fun restartRightHits() {
        rightHits = 0
    }

    fun decreaseLife() {
        lives -= 1
    }

    fun restartLives() {
        lives = 2
    }
}
