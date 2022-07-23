package com.example.miquido.data.repository

import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.domain.model.PhotoList
import com.example.miquido.domain.model.Response
import com.example.miquido.domain.repository.Repository
import com.example.miquido.utils.Constants.UNKNOWN_ERROR
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: PicsumApi
): Repository {

    override suspend fun getPhotos(page: Int): Response<PhotoList> {
        return try {
            Response.Success(
                data = remote.getPhotos(page)
            )
        } catch (e: Exception) {
            Response.Error(e.message ?: UNKNOWN_ERROR)
        }
    }
}

