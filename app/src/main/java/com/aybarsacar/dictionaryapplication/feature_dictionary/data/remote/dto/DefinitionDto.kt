package com.aybarsacar.dictionaryapplication.feature_dictionary.data.remote.dto


import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.Definition
import com.google.gson.annotations.SerializedName


data class DefinitionDto(
  @SerializedName("antonyms")
  val antonyms: List<String>,
  @SerializedName("definition")
  val definition: String,
  @SerializedName("example")
  val example: String?,
  @SerializedName("synonyms")
  val synonyms: List<String>
) {

  // mapper function
  fun toDefinition(): Definition {
    return Definition(antonyms, definition, example, synonyms)
  }
}