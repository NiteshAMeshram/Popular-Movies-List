package com.example.movieslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieslist.model.repository.RetrofitRepository

class PopularMoviesViewModelFactory constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
            PopularMoviesViewModel(this.retrofitRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}