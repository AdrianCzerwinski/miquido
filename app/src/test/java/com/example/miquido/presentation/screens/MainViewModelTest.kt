package com.example.miquido.presentation.screens

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.miquido.data.remote.PicsumApi
import com.example.miquido.data.remote.validResponse
import com.example.miquido.data.repository.RepositoryImpl
import com.example.miquido.domain.model.Photo
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: PicsumApi
    private lateinit var repository: RepositoryImpl
    private lateinit var viewModel: MainViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalSerializationApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
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
        viewModel = MainViewModel(repository = repository)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should give proper GET path`() =
        runBlocking{
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(validResponse)
            )
            val result = mockWebServer.takeRequest(5, TimeUnit.SECONDS)
            viewModel.pageUpAndLoadPhotos()
            val stateResult = viewModel.listScreenState
            val path = "/v2/list?page=1&limit=20"
            assertThat(path == result.path).isTrue()
    }

    @Test
    fun `should give screenState of ListScreen populated with 0 items and isLoading, progressBarState as false`() =
        runBlocking{
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validResponse)
        )
        viewModel.loadPhotos(1)
        val stateResult = viewModel.listScreenState
        delay(500)
        assertThat(stateResult.photos.isEmpty()).isTrue()
    }

    @Test
    fun `should give higher page number = 2`() =
        runBlocking{
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(validResponse)
            )
            viewModel.pageUpAndLoadPhotos()
            val stateResult = viewModel.listScreenState
            assertThat(stateResult.page == 2).isTrue()
        }


}