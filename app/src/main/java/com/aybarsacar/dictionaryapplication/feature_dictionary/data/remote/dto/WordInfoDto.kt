package com.aybarsacar.dictionaryapplication.feature_dictionary.data.remote.dto


import com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.entity.WordInfoEntity
import com.google.gson.annotations.SerializedName


data class WordInfoDto(
  @SerializedName("meanings")
  val meanings: List<MeaningDto>,
  @SerializedName("origin")
  val origin: String,
  @SerializedName("phonetic")
  val phonetic: String,
  @SerializedName("phonetics")
  val phonetics: List<PhoneticDto>,
  @SerializedName("word")
  val word: String
) {

  fun toWordInfoEntity(): WordInfoEntity {
    return WordInfoEntity(word, phonetic, origin, meanings.map { it.toMeaning() })
  }
}