package com.aybarsacar.dictionaryapplication.feature_dictionary.data.repository

import com.aybarsacar.dictionaryapplication.feature_dictionary.core.util.Resource
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.WordInfoDao
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.remote.DictionaryApi
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class WordInfoRepositoryImpl(private val api: DictionaryApi, private val dao: WordInfoDao) : WordInfoRepository {

  override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {

    emit(Resource.Loading())

    // get the word from the local device database
    val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }

    emit(Resource.Loading(data = wordInfos))

    // make the api request
    try {

      val remoteWordInfos = api.getWordInfo(word)

      // delete the wordinfos in the database for the word
      // and replace it with the recently fetched one
      dao.deleteWordInfos(remoteWordInfos.map { it.word })
      dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

    } catch (e: HttpException) {

      emit(
        Resource.Error(
          data = wordInfos,
          message = e.localizedMessage ?: "Oops, something went wrong!"
        )
      )

    } catch (e: IOException) {

      emit(
        Resource.Error(
          data = wordInfos,
          message = e.localizedMessage ?: "Couldn't reach the server. Please check your internet connection."
        )
      )
    }

    // always send from the local database - single source of truth principle
    val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }

    emit(Resource.Success(newWordInfos))
  }
}