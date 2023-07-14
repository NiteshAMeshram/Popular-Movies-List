package com.example.movieslist.model.di

import com.example.movieslist.model.ApiConstants
import com.example.movieslist.model.api.RetrofitService
import com.example.movieslist.model.repository.RetrofitRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitApiModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRetroRepository(): RetrofitRepository {
        return RetrofitRepository(provideRetrofitInstance().create(RetrofitService::class.java))
    }
}