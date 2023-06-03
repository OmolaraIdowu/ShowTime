package com.swancodes.showtime.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.swancodes.showtime.R
import com.swancodes.showtime.databinding.FragmentMovieDetailBinding
import com.swancodes.showtime.util.Constants
import com.swancodes.showtime.util.loadImage

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        with(binding) {
            movieHeader.text = movie.title
            movieName.text = movie.title
            movieImage.loadImage(
                Constants.IMAGE_BASE_URL.plus(Constants.POSTER_SIZE).plus(movie.poster_path)
            )
            movieOverview.text = movie.overview
            movieReleaseDate.text = getString(R.string.release_date, movie.release_date)
            movieLanguage.text = getString(R.string.language, movie.original_language)
            progressBar.apply {
                progress = movie.vote_average.toFloat()
                labelText = movie.vote_average.toString()
            }
        }
    }
}