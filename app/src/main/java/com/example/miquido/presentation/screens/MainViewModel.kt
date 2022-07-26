package com.example.miquido.presentation.screens

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

    var listScreenState by mutableStateOf(ScreenState())
        private set
    var listErrorState by mutableStateOf(Response.Error(""))
        private set
    var detailErrorState by mutableStateOf(Response.Error(""))
        private set

    init {
        loadPhotos(1)
    }

    fun loadPhotos(page: Int) {
        viewModelScope.launch {
            listScreenState = if (listScreenState.page == 1) {
                listScreenState.copy(progressBarState = true)
            } else {
                listScreenState.copy(isLoading = true)
            }
            delay(1000)
            when (val response = repository.getPhotos(page)) {
                is Response.Success -> {
                    listScreenState = listScreenState.copy(
                        isLoading = false,
                        progressBarState = false,
                        photos = listScreenState.photos + response.data
                    )
                }
                is Response.Error -> {
                    listScreenState = listScreenState.copy(
                        isLoading = false,
                        progressBarState = false,
                        page = listScreenState.page - 1
                    )
                    listErrorState = Response.Error(message = response.message)
                }
            }
        }
    }

    fun pageUpAndLoadPhotos() {
        val page = listScreenState.page + 1
        listScreenState = listScreenState.copy(page = page)
        loadPhotos(page)
    }

    fun detailErrorToDetailsScreen(e: String?) {
        detailErrorState = Response.Error(message = e ?: UNKNOWN_ERROR)
    }
}

data class ScreenState(
    val isLoading: Boolean = false,
    var photos: List<Photo> = emptyList(),
    val page: Int = 1,
    val progressBarState: Boolean = false
)