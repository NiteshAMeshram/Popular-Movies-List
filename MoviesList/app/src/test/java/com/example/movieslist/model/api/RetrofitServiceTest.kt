package com.example.movieslist.model.api

import com.example.movieslist.model.ApiConstants
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var retrofitService: RetrofitService
    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        retrofitService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(ApiConstants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RetrofitService::class.java)
    }


    @Test
    fun get_popular_movie_api_test() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody(""))
            val response = retrofitService.getPopularMovies(ApiConstants.API_KEY)
            val request = mockWebServer.takeRequest()
            assertEquals(
                "https://api.themoviedb.org/3/movie/popular?&api_key=247df8e593f3a6e63c3708d33e493d94",
                request.path
            )
            assertEquals(true, response.body()?.results?.isEmpty() == true)
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}