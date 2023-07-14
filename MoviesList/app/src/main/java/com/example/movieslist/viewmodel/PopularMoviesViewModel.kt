package com.example.movieslist.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieslist.model.data.PopularMovies
import com.example.movieslist.model.repository.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModel() {

    val popularMovieList = MutableLiveData<PopularMovies>()
    val errorMessage = MutableLiveData<String>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun getPopularMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitRepository.getPopularMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    popularMovieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}