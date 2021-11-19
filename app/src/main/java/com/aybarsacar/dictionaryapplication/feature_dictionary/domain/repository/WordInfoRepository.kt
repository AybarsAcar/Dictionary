package com.aybarsacar.dictionaryapplication.feature_dictionary.domain.repository

import com.aybarsacar.dictionaryapplication.feature_dictionary.core.util.Resource
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow


interface WordInfoRepository {

  fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}