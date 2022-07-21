package com.example.miquido.domain.repository

import com.example.miquido.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getPhotos(page: Int): Flow<List<Photo>>
}