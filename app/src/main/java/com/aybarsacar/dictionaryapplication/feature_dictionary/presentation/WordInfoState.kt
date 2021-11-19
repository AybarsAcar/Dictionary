package com.aybarsacar.dictionaryapplication.feature_dictionary.presentation

import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo


/**
 * wrapper class for the UI State
 */
data class WordInfoState(
  val wordInfoItems: List<WordInfo> = emptyList(),
  val isLoading: Boolean = false
)