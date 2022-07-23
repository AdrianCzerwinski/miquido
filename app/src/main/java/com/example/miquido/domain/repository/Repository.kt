package com.example.miquido.domain.repository

import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response

interface Repository {
    suspend fun getPhotos(page: Int): Response<List<Photo>>
}