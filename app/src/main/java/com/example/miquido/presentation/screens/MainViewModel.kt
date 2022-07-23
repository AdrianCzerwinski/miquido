package com.example.miquido.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _photoList: MutableState<List<Photo>> = mutableStateOf(emptyList())
    val photoList: State<List<Photo>> = _photoList

    private val _pageNumber: MutableState<Int> = mutableStateOf(1)
    val pageNumber: State<Int> = _pageNumber

    private val _progressBarState: MutableState<Boolean> = mutableStateOf(false)
    val progressBarState: State<Boolean> = _progressBarState

    private val _messageState: MutableState<String> = mutableStateOf("")
    val messageState: State<String> = _messageState

    init {
        getPhotos(1)
    }

    fun getPhotos(page: Int) {
        viewModelScope.launch {
            _progressBarState.value = true
            Log.d("PHOTO", "Started")
            when (val response = repository.getPhotos(page)) {
                is Response.Success -> {
                    _photoList.value = response.data
                    response.data?.forEach {
                        Log.d("PHOTO", it.id)
                    }
                    _progressBarState.value = false
                }
                is Response.Error -> {
                    _messageState.value = response.message
                    Log.d("PHOTO", response.message)
                    _progressBarState.value = false
                }
            }
        }
    }

    private fun pageUp() {
        _pageNumber.value++
    }
}