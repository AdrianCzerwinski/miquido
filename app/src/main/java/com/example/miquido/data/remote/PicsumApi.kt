package com.example.miquido.data.remote

import com.example.miquido.domain.model.PhotoList
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }

    @GET("v2/list?page=1&limit=20")
    suspend fun getPhotos(
        @Query("page") query: Int
    ):PhotoList

}