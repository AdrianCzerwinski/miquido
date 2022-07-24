package com.example.miquido.data.repository

import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.data.remote.invalidResponse
import com.example.miquido.data.remote.validResponse
import com.example.miquido.domain.model.Response
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class RepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: PicsumApi
    lateinit var repository: RepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.play()
        val contentType = "application/json".toMediaType()
        okHttpClient = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(contentType = contentType))
            .client(okHttpClient)
            .baseUrl(mockWebServer.getUrl("/"))
            .build()
            .create(PicsumApi::class.java)
        repository = RepositoryImpl(remote = api)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `it should give success response from api`(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validResponse)
        )
        val result = repository.getPhotos(1)
        assertThat(result is Response.Success).isTrue()
    }

    @Test
    fun `it should give error response from api`(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validResponse)
        )
        val result = repository.getPhotos(1)
        assertThat(result is Response.Error).isTrue()
    }

    @Test
    fun `it should give error response bc wrong data type`(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(invalidResponse)
        )
        val result = repository.getPhotos(1)
        assertThat(result is Response.Success).isFalse()
    }
}