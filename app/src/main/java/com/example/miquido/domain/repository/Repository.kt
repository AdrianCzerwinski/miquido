package com.example.miquido.domain.repository

import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.PhotoList
import com.example.miquido.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPhotos(page: Int): Response<PhotoList>
}