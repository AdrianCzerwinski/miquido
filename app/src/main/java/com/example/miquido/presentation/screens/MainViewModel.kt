package com.example.miquido.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(ScreenState())

    init {
        getPhotos(1)
    }

    private fun getPhotos(page: Int) {
        viewModelScope.launch {
            if(state.page == 1) {
                state = state.copy(progressBarState = true)
            } else {
                state = state.copy(isLoading = true)
            }
            delay(1000)
            when (val response = repository.getPhotos(page)) {
                is Response.Success -> {
                    state = state.copy(
                        isLoading = false,
                        progressBarState = false,
                        photos = state.photos + response.data
                    )
                    Log.d("PHOTOS", "New size = ${state.photos.size}")

                }
                is Response.Error -> {
                    state = state.copy(
                        error = Response.Error(message = response.message)
                    )
                }
            }
        }
    }

    fun pageUp() {
        val page = state.page + 1
        Log.d("PHOTOS", "Next Page: $page")
        state = state.copy(page = page)
        getPhotos(page)
    }
}

data class ScreenState(
    val isLoading: Boolean = false,
    var photos: List<Photo> = emptyList(),
    val error: Response.Error = Response.Error(message = ""),
    val page: Int = 2,
    val progressBarState: Boolean = false
)