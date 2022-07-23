package com.example.miquido.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.model.PhotoList
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var photoList by mutableStateOf(PhotoList())
        private set
    var pageNumber by mutableStateOf(1)
        private set
    var progressBarState by mutableStateOf(true)
        private set
    var messageState by mutableStateOf("")
        private set

    init {
        getPhotos(1)
    }

    private fun getPhotos(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            progressBarState = true
            when (val response = repository.getPhotos(page)) {
                is Response.Success -> {
                    photoList = response.data
                    progressBarState = false
                }
                is Response.Error -> {
                    messageState = response.message
                    progressBarState = false
                }
            }
        }
    }
}