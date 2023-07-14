package com.example.movieslist.model.di


import com.example.movieslist.PopularMoviesApplication
import com.example.movieslist.model.repository.RetrofitRepository
import dagger.Component

@Component(
    modules = [
        PopularMoviesActivityModule::class,
        RetrofitApiModule::class
    ]
)
interface AppComponent {
    fun inject(mPopularMoviesApplication: PopularMoviesApplication)
    fun injectRetrofit(retrofitRepository: RetrofitRepository)
}