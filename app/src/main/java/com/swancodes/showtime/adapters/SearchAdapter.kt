package com.swancodes.showtime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.showtime.R
import com.swancodes.showtime.data.model.Movie
import com.swancodes.showtime.databinding.SearchListItemBinding
import com.swancodes.showtime.util.Constants
import com.swancodes.showtime.util.loadImage

class SearchAdapter(private val listener: ItemClickListener) :
    ListAdapter<Movie, SearchAdapter.SearchViewHolder>(differCallback) {

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

    inner class SearchViewHolder(private val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) = with(binding) {
            searchMovieImage.loadImage(
                Constants.IMAGE_BASE_URL.plus(Constants.POSTER_SIZE).plus(movie?.poster_path)
            )
            searchMovieTitle.text = movie?.title
            searchMovieReleaseDate.text =
                root.context.getString(R.string.release_date, movie?.release_date)
            searchMovieLanguage.text =
                root.context.getString(R.string.language, movie?.original_language)
            root.setOnClickListener {
                listener.onItemClick(movie!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchListItemBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_list_item, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}