package com.swancodes.showtime.adapters

import com.swancodes.showtime.data.model.Movie

interface ItemClickListener {
    fun onItemClick(movie: Movie)
}