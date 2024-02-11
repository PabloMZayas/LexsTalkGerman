package com.projects.lexstalkpt.data.lessons

data class LessonItem(
        val id: Int = System.currentTimeMillis().hashCode(),
        val lessonCategory: Int = 0,
        val lessonTitle: String = "",
        val lessonIntroductionKey: String = "",
        val lessonVocabularyKey: String = "",
        val lessonSentencesKey: String = "",
        val lessonDrawable: Int = 0,
        val lessonGermanIntroductionKey: String = "",
        var lessonProgress: Int = 0
)
