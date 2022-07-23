package com.example.miquido.data.remote

import com.example.miquido.domain.model.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {


    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ):List<Photo>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }
}