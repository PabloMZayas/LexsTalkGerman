package com.projects.lexstalkpt.domain

import com.projects.lexstalkpt.data.lessons.ProvideVocabularyFromTXT
import javax.inject.Inject

class ProvideVocabularyFromTXTUseCase @Inject constructor(private val provideVocabularyFromTXT: ProvideVocabularyFromTXT) {

    fun provideVocabularyFromTxt(key: String) = provideVocabularyFromTXT.generateLists(key)
}