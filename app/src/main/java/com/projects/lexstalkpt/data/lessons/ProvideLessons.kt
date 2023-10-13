package com.projects.lexstalkpt.data.lessons

import com.projects.lexstalkpt.R

class ProvideLessons {

    fun provideBeginnerLectureItems(): MutableList<LessonItem> = mutableListOf(
            LessonItem(
                    1, 1,
                    "Vocabulario introductorio",
                    "lecture_1_introductory_vocabulary_introduction.txt",
                    "vocabulary_introductory_vocabulary.txt",
                    "introductory_vocabulary_sentences.txt",
                    R.drawable.icon_egg),

            LessonItem(
                    2, 1,
                    "Animales - primera parte",
                    "animals_1_introduction.txt",
                    "animals_1_vocabulary.txt",
                    "animals_1_sentences.txt",
                    R.drawable.animals_icon),

            LessonItem(
                    3, 1,
                    "Frutas - primera parte",
                    "fruits_1_introduction.txt",
                    "fruits_1_vocabulary.txt",
                    "fruits_1_sentences.txt",
                    R.drawable.fruit_icon_2),

            LessonItem(4, 1,
                    "Colores - primera parte",
                    "colors_1_introduction.txt",
                    "colors_1_vocabulary.txt",
                    "colors_1_sentences.txt",
                    R.drawable.colors_icon),

            LessonItem(5, 1,
                    "Números - primera parte",
                    "numbers_1_introduction.txt",
                    "numbers_1_vocabulary.txt",
                    "numbers_1_sentences.txt",
                    R.drawable.numbers_icon),

            LessonItem(6, 1,
                    "Saludos y presentaciones",
                    "lecture_1_presentations_introduction.txt",
                    "presentations_vocabulary.txt",
                    "presentations_sentences.txt",
                    R.drawable.hand_icon),

            LessonItem(7, 1,
                    "Pronombres personales",
                    "lecture_5_personal_pronouns_2_introduction.txt",
                    "personal_pronouns_1_vocabulary.txt",
                    "personal_pronouns_sentences.txt",
                    R.drawable.people_icon),

            LessonItem(8, 1,
                    "Comidas - primera parte",
                    "food_1_introduction.txt",
                    "food_1_vocabulary.txt",
                    "food_1_sentences.txt",
                    R.drawable.icon_food),


            LessonItem(9, 1,
                    "Verbo TO BE",
                    "to_be_introduction.txt",
                    "to_be_vocabulary.txt",
                    "to_be_sentences.txt",
                    R.drawable.to_be_icon),

            LessonItem(10, 1,
                    "Números - segunda parte",
                    "numbers_2_introduction.txt",
                    "numbers_2_vocabulary.txt",
                    "numbers_2_sentences.txt",
                    R.drawable.numbers_icon),
    )
}
