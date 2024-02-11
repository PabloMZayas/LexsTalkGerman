package com.projects.lexstalkpt.presentation.playing

data class CardItem(val word: String, var isSelected: Boolean, var isFound: Boolean, var onSelectedChange: (Boolean) -> Unit, var onFoundChange: (Boolean) -> Unit)
