package com.example.movieslist.model.repository

import com.example.movieslist.model.ApiConstants
import com.example.movieslist.model.api.RetrofitService
import com.example.movieslist.model.data.PopularMovies
import com.example.movieslist.model.data.RetrofitResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RetrofitRepositoryTest {

    lateinit var mainRepository: RetrofitRepository

    @Mock
    lateinit var apiService: RetrofitService

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainRepository = RetrofitRepository(apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getPopularMovies() {
        runBlocking {
            Mockito.`when`(apiService.getPopularMovies(ApiConstants.API_KEY)).thenReturn(
                Response.success(
                    PopularMovies(1, listOf<RetrofitResponse>(), 1, 1)
                )
            )
            val response = mainRepository.getPopularMovies()
            assertEquals(200, response.code())
        }
    }
}