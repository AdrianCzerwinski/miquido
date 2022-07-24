package com.example.miquido.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import com.example.miquido.utils.Constants.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
        private set
    var messageBarState by mutableStateOf(Response.Error(""))
        private set
    var detailMessageBarState by mutableStateOf(Response.Error(""))
        private set

    init {
        getPhotos(1)
    }

    private fun getPhotos(page: Int) {
        viewModelScope.launch {
            state = if (state.page == 1) {
                state.copy(progressBarState = true)
            } else {
                state.copy(isLoading = true)
            }
            delay(1000)
            when (val response = repository.getPhotos(page)) {
                is Response.Success -> {
                    state = state.copy(
                        isLoading = false,
                        progressBarState = false,
                        photos = state.photos + response.data
                    )
                }
                is Response.Error -> {
                    state = state.copy(
                        isLoading = false,
                        progressBarState = false,
                        page = state.page - 1
                    )
                    messageBarState = Response.Error(message = response.message)
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

    fun detailError(e: String?) {
        detailMessageBarState = Response.Error(message = e ?: UNKNOWN_ERROR)
    }
}

data class ScreenState(
    val isLoading: Boolean = false,
    var photos: List<Photo> = emptyList(),
    val page: Int = 1,
    val progressBarState: Boolean = false
)