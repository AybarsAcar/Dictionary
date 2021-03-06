package com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.Meaning
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo


@Entity
data class WordInfoEntity(
  val word: String,
  val phonetic: String,
  val origin: String,
  val meanings: List<Meaning>,

  @PrimaryKey val id: Int? = null
) {

  // mapper function
  fun toWordInfo(): WordInfo {
    return WordInfo(meanings, origin, phonetic, word)
  }
}