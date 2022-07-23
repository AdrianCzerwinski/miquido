package com.example.miquido.data.repository

import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.domain.model.Photo
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import com.example.miquido.utils.Constants.UNKNOWN_ERROR
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: PicsumApi
): Repository {

    override suspend fun getPhotos(page: Int): Response<List<Photo>> {
        return try {
            Response.Success(
                data = remote.getPhotos(page = page, limit = 20)
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: UNKNOWN_ERROR)
        }
    }
}

