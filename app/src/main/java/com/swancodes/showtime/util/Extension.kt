package com.swancodes.showtime.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.swancodes.showtime.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

fun View.hide() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

fun View.show() {
    visibility = View.VISIBLE
}
