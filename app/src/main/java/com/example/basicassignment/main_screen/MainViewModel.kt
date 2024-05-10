package com.example.basicassignment.main_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicassignment.repository.Repository
import com.example.basicassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _state = mutableStateOf(MainStates())
    val state: State<MainStates> = _state

    init {
        getAllVideos()
    }

    private fun getAllVideos() = viewModelScope.launch {

        repository.getVideoData().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(allVideos = result.data!!)
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d("viewModel", "registerUser: success")
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isError = result.message)
                }
            }
        }
    }

    private fun getFilteredVideos(searchText: String) = viewModelScope.launch {

        repository.getFilteredVideoData(searchText = searchText).collect { result ->
            when (result) {
                is Resource.Loading -> {
                   //_state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(filteredVideos = result.data!!)
                    Log.d("viewModel", "registerUser: success")
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isError = result.message)
                }
            }
        }
    }

    fun onEvent(event: MainEvents) {
        when (event){
            is MainEvents.GetFilteredVideoList -> {
                getFilteredVideos(event.searchText)
            }
        }
    }
}