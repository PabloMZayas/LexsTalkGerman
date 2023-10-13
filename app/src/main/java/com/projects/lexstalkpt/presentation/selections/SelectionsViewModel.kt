package com.projects.lexstalkpt.presentation.selections

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

    private var _vocabularyList = MutableLiveData<List<List<String>>>()
    val vocabularyList get() = _vocabularyList

    private var _lessonItemSelected = MutableLiveData<LessonItem>()
    val lessonItemSelected get() = _lessonItemSelected

    private var _rightHits = MutableLiveData(0)
    val rightHits get() = _rightHits

    private var _lives = MutableLiveData<Int>()
    val lives get() = _lives

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
        _vocabularyList.value = provideVocabularyFromTXTUseCase.provideVocabularyFromTxt(key)
    }

    fun updateHits() {
        _rightHits.value = _rightHits.value!!.plus(1)
    }

    fun restartRightHits() {
        _rightHits.value = 0
    }

    fun decreaseLife() {
        _lives.value = _lives.value?.minus(1)
    }
}
