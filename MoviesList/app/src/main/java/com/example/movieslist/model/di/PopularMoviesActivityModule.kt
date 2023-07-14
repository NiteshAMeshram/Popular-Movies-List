package com.example.movieslist.model.di

import com.example.movieslist.view.PopularMoviesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PopularMoviesActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): PopularMoviesActivity

}