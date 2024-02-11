package com.projects.lexstalkpt.domain

import com.projects.lexstalkpt.data.lessons.ProvideIntroductionsLessons
import javax.inject.Inject

class ProvideIntroductionsLessonsUseCase @Inject constructor(private val provideIntroductionsLessons: ProvideIntroductionsLessons) {

    fun provideIntroductionFromTxt(key: String) = provideIntroductionsLessons.generateIntroduction(key)

    fun provideGermanIntroductionFromTxt(germanKey: String): List<String> = provideIntroductionsLessons.generateIntroduction(germanKey)
}
