package com.aybarsacar.dictionaryapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import com.aybarsacar.dictionaryapplication.feature_dictionary.presentation.WordInfoViewModel
import com.aybarsacar.dictionaryapplication.ui.theme.DictionaryApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      DictionaryApplicationTheme {
        Surface(color = MaterialTheme.colors.background) {

          val viewModel: WordInfoViewModel = hiltViewModel()
          val state = viewModel.state.value

          val scaffoldState = rememberScaffoldState()

          LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest { event ->
              when (event) {
                is WordInfoViewModel.UIEvent.ShowSnackBar -> {
                  scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
              }
            }
          }


          Scaffold(
            scaffoldState = scaffoldState
          ) {

            Box(
              modifier = Modifier.background(MaterialTheme.colors.background)
            ) {

              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(16.dp)
              ) {

                TextField(
                  value = viewModel.searchQuery.value,
                  onValueChange = viewModel::onSearch,
                  modifier = Modifier.fillMaxWidth(),
                  placeholder = {
                    Text(text = "Search...")
                  }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                  items(state.wordInfoItems.size) { i ->
                    val wordInfo = state.wordInfoItems[i]

                    if (i > 0) {
                      Spacer(modifier = Modifier.height(8.dp))
                    }

                    WordInfoItem(wordInfo = wordInfo)

                    if (i < state.wordInfoItems.size - 1) {
                      Divider()
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }


  @Composable
  fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier
  ) {

    Column(modifier = modifier) {

      Text(text = wordInfo.word, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

      Text(text = wordInfo.phonetic, fontWeight = FontWeight.Light)

      Spacer(modifier = Modifier.height(16.dp))

      Text(text = wordInfo.origin)

      wordInfo.meanings.forEach { meaning ->
        Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)

        meaning.definitions.forEachIndexed { i, definition ->

          Text(text = "${i + 1} ${definition.definition}")

          Spacer(modifier = Modifier.height(8.dp))

          definition.example?.let { example ->
            Text(text = "Example: $example")
          }

          Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}