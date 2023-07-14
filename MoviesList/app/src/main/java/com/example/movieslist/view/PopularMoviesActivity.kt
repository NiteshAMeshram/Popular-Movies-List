package com.example.movieslist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movieslist.R
import com.example.movieslist.databinding.ActivityMainBinding
import com.example.movieslist.view.adapter.PopularMoviesAdapter
import com.example.movieslist.model.repository.RetrofitRepository
import com.example.movieslist.viewmodel.PopularMoviesViewModel
import com.example.movieslist.viewmodel.PopularMoviesViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class PopularMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val popularMoviesAdapter = PopularMoviesAdapter()

    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    @Inject
    lateinit var popularMoviesViewModel: PopularMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        popularMoviesViewModel = ViewModelProviders.of(this, PopularMoviesViewModelFactory(retrofitRepository)).get(PopularMoviesViewModel::class.java)
        binding.recyclerview.adapter = popularMoviesAdapter

        popularMoviesViewModel.popularMovieList.observe(this, Observer {
            popularMoviesAdapter.setPopularMovieList(it)
        })

        popularMoviesViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        popularMoviesViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        })

        popularMoviesViewModel.getPopularMovies()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}