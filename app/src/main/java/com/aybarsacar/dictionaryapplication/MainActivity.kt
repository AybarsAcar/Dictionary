package com.aybarsacar.dictionaryapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.aybarsacar.dictionaryapplication.ui.theme.DictionaryApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      DictionaryApplicationTheme {
        Surface(color = MaterialTheme.colors.background) {


        }
      }
    }
  }
}