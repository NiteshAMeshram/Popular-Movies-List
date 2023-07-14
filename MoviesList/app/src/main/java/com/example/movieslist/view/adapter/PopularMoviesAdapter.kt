package com.example.movieslist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieslist.databinding.AdapterMovieBinding
import com.example.movieslist.model.ApiConstants
import com.example.movieslist.model.data.PopularMovies
import com.example.movieslist.model.data.RetrofitResponse

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMovieViewHolder>() {

    var mPopularMoviesList = mutableListOf<RetrofitResponse>()
    fun setPopularMovieList(mPopularMoviesList: PopularMovies) {
        this.mPopularMoviesList = mPopularMoviesList.results.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return PopularMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val movie = mPopularMoviesList
        holder.binding.name.text = movie.get(position).title
        Glide.with(holder.itemView.context)
            .load(ApiConstants.IMAGE_URL_PREFIX + movie.get(position).poster_path)
            .into(holder.binding.imageview)
    }

    override fun getItemCount(): Int {
        return mPopularMoviesList.size
    }
}

class PopularMovieViewHolder(val binding: AdapterMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
}