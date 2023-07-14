package com.example.movieslist.model.data

data class PopularMovies(
    val page: Int,
    val results: List<RetrofitResponse>,
    val total_pages: Int,
    val total_results: Int
)
