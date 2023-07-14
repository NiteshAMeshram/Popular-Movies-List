package com.example.movieslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movieslist.model.api.RetrofitService
import com.example.movieslist.model.data.PopularMovies
import com.example.movieslist.model.data.RetrofitResponse
import com.example.movieslist.model.repository.RetrofitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class PopularMoviesViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mPopularMoviesViewModel: PopularMoviesViewModel
    lateinit var mRetrofitRepository: RetrofitRepository

    @Mock
    lateinit var mRetrofitService: RetrofitService

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mRetrofitRepository = RetrofitRepository(mRetrofitService)
        mPopularMoviesViewModel = PopularMoviesViewModel(mRetrofitRepository)

    }

    @Test
    fun getPopularMovies() {
        runBlocking {
            Mockito.`when`(mRetrofitRepository.getPopularMovies())
                .thenReturn(
                    Response.success(
                        PopularMovies(1, listOf<RetrofitResponse>(), 1, 1)
                    )
                )
            mPopularMoviesViewModel.getPopularMovies()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = mPopularMoviesViewModel.popularMovieList.getOrAwaitValue()
            assertEquals(PopularMovies(1, listOf<RetrofitResponse>(), 1, 1), result)
        }
    }
}

private fun <T : Any> MutableLiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): Any {

    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    try {
        afterObserve.invoke()
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}