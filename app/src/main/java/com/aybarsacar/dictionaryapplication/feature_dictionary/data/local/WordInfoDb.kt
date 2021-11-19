package com.aybarsacar.dictionaryapplication.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aybarsacar.dictionaryapplication.feature_dictionary.data.local.entity.WordInfoEntity


@Database(entities = [WordInfoEntity::class], version = 1)
@TypeConverters(Converters::class) // pass in the custom converters
abstract class WordInfoDb : RoomDatabase() {

  abstract val dao: WordInfoDao
}