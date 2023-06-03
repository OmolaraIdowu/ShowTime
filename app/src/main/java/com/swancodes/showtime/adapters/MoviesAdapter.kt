package com.swancodes.showtime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.showtime.R
import com.swancodes.showtime.data.model.Movie
import com.swancodes.showtime.databinding.MovieListItemBinding
import com.swancodes.showtime.util.Constants.Companion.IMAGE_BASE_URL
import com.swancodes.showtime.util.Constants.Companion.POSTER_SIZE
import com.swancodes.showtime.util.loadImage

class MoviesAdapter(private val listener: ItemClickListener) :
    ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(differCallback) {

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) = with(binding) {
            movieImage.loadImage(IMAGE_BASE_URL.plus(POSTER_SIZE).plus(movie?.poster_path))
            movieTitle.text = movie?.title
            root.setOnClickListener {
                listener.onItemClick(movie!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieListItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.movie_list_item, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}