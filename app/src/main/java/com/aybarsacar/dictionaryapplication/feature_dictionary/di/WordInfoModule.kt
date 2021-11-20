package com.aybarsacar.dictionaryapplication.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.Converters
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.WordInfoDb
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.remote.DictionaryApi
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.util.GsonParser
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.repository.WordInfoRepository
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.use_case.GetWordInfoUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

  @Provides
  @Singleton
  fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfoUseCase {
    return GetWordInfoUseCase(repository)
  }


  @Provides
  @Singleton
  fun provideWordInfoRepository(api: DictionaryApi, db: WordInfoDb): WordInfoRepository {
    return WordInfoRepositoryImpl(api, db.dao)
  }


  @Provides
  @Singleton
  fun provideWordInfoDb(app: Application): WordInfoDb {
    return Room
      .databaseBuilder(app, WordInfoDb::class.java, "word_db")
      .addTypeConverter(Converters(GsonParser(Gson())))
      .build()
  }


  @Provides
  @Singleton
  fun provideDictionaryApi(): DictionaryApi {
    return Retrofit
      .Builder()
      .baseUrl(DictionaryApi.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(DictionaryApi::class.java)
  }
}