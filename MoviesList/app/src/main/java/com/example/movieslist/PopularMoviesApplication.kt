package com.example.movieslist

import android.app.Activity
import android.app.Application
import com.example.movieslist.model.di.RetrofitApiModule
import com.example.movieslist.model.di.AppComponent
import com.example.movieslist.model.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class PopularMoviesApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .retrofitApiModule(RetrofitApiModule())
            .build()
        appComponent.inject(this)
    }
}