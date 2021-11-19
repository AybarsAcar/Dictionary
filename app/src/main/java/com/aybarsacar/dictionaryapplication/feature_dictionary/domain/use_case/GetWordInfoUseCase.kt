package com.aybarsacar.dictionaryapplication.feature_dictionary.domain.use_case

import com.aybarsacar.dictionaryapplication.feature_dictionary.core.util.Resource
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetWordInfoUseCase(private val _repository: WordInfoRepository) {

  operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {

    if (word.isBlank()) {
      return flow {}
    }

    return _repository.getWordInfo(word)
  }
}