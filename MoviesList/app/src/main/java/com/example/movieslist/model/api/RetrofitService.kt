package com.example.movieslist.model.api

import com.example.movieslist.model.data.PopularMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("popular?")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): Response<PopularMovies>
}