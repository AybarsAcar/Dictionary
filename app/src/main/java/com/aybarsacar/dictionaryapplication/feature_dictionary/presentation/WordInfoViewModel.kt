package com.aybarsacar.dictionaryapplication.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.dictionaryapplication.feature_dictionary.core.util.Resource
import com.aybarsacar.dictionaryapplication.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordInfoViewModel @Inject constructor(private val _getWordInfo: GetWordInfoUseCase) : ViewModel() {

  private val _searchQuery = mutableStateOf("")
  val searchQuery: State<String> = _searchQuery

  private val _state = mutableStateOf(WordInfoState())
  val state: State<WordInfoState> = _state

  private val _eventFlow = MutableSharedFlow<UIEvent>()
  val eventFlow = _eventFlow.asSharedFlow()

  // coroutine to cancel the coroutine Job - so we need to cache it, like Unity
  private var searchJob: Job? = null


  /**
   * will be triggered each time a character is typed
   */
  fun onSearch(query: String) {

    _searchQuery.value = query
    searchJob?.cancel()

    searchJob = viewModelScope.launch {
      delay(500L)

      _getWordInfo(word = query).onEach {

        when (it) {
          is Resource.Success -> {
            _state.value = state.value.copy(
              wordInfoItems = it.data ?: emptyList(),
              isLoading = false
            )
          }

          is Resource.Loading -> {
            _state.value = state.value.copy(
              wordInfoItems = it.data ?: emptyList(),
              isLoading = true
            )
          }

          is Resource.Error -> {
            _state.value = state.value.copy(
              wordInfoItems = it.data ?: emptyList(),
              isLoading = false
            )
            _eventFlow.emit(UIEvent.ShowSnackBar(it.message ?: "Unknown error"))
          }
        }
      }.launchIn(this)
    }
  }


  sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
  }
}