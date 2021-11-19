package com.aybarsacar.dictionaryapplication.feature_dictionary.data.util

import java.lang.reflect.Type

// wrapper interface our Gson library to make the app library independent
interface JsonParser {

  fun <T> fromJson(json: String, type: Type): T?

  fun <T> toJson(obj: T, type: Type): String?
}