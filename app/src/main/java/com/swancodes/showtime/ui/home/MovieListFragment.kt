package com.swancodes.showtime.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.swancodes.showtime.adapters.ItemClickListener
import com.swancodes.showtime.adapters.MoviesAdapter
import com.swancodes.showtime.data.api.MovieApiClient.retrofitService
import com.swancodes.showtime.data.model.Movie
import com.swancodes.showtime.databinding.FragmentMovieListBinding
import com.swancodes.showtime.util.hide
import com.swancodes.showtime.util.show
import com.swancodes.showtime.viewmodel.MovieApiStatus
import com.swancodes.showtime.viewmodel.MovieViewModel
import com.swancodes.showtime.viewmodel.MovieViewModelFactory

class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(retrofitService)
    }
    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(object : ItemClickListener {
            override fun onItemClick(movie: Movie) {
                findNavController().navigate(MovieListFragmentDirections.toMovieDetailFragment(movie))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                MovieApiStatus.LOADING -> {
                    binding.progressBar.show()
                }
                MovieApiStatus.ERROR -> {
                    binding.progressBar.hide()
                    showErrorMessage()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                MovieApiStatus.DONE -> {
                    binding.progressBar.hide()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { searchResult ->
            moviesAdapter.submitList(searchResult)
            if (searchResult.isEmpty()) {
                // Show a message when no results are found
                binding.noResultImageView.show()
                binding.noResultTextView.show()
            } else {
                binding.noResultImageView.hide()
                binding.noResultTextView.hide()
            }
        }

        binding.movieSearchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchMovies(newText)
                }
                return true
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.hide()
            viewModel.getPopularMovies()
        }
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, "A problem occurred. Please try again.", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getPopularMovies()
                viewModel.searchMovies(binding.movieSearchView.query.toString())
            }
            .show()
    }

    private fun setUpRecyclerView() {
        binding.movieListRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = moviesAdapter
        }
    }
}