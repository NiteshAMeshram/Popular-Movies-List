package com.example.movieslist.model.repository

import com.example.movieslist.model.ApiConstants
import com.example.movieslist.model.api.RetrofitService
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitService: RetrofitService) {
    suspend fun getPopularMovies() = retrofitService.getPopularMovies(ApiConstants.API_KEY)
}
