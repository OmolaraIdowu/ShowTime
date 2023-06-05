package com.swancodes.showtime.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.swancodes.showtime.adapters.ItemClickListener
import com.swancodes.showtime.adapters.HomeAdapter
import com.swancodes.showtime.data.api.MovieApiClient.retrofitService
import com.swancodes.showtime.data.model.Movie
import com.swancodes.showtime.databinding.FragmentHomeBinding
import com.swancodes.showtime.util.hide
import com.swancodes.showtime.util.show
import com.swancodes.showtime.viewmodel.MovieApiStatus
import com.swancodes.showtime.viewmodel.MovieViewModel
import com.swancodes.showtime.viewmodel.MovieViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(retrofitService)
    }
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(object : ItemClickListener {
            override fun onItemClick(movie: Movie) {
                findNavController().navigate(HomeFragmentDirections.toMovieDetailFragment(movie))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        viewModel.movies.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                MovieApiStatus.LOADING -> {
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.progressBar.hide()
                    } else binding.progressBar.show()
                }
                MovieApiStatus.ERROR -> {
                    binding.progressBar.hide()
                    binding.swipeRefreshLayout.isRefreshing = false
                    showErrorMessage()
                }
                MovieApiStatus.DONE -> {
                    binding.progressBar.hide()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.hide()
            viewModel.getPopularMovies()
        }
        binding.searchButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.toSearchFragment())
        }
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, "A problem occurred. Please try again.", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getPopularMovies()
            }
            .show()
    }

    private fun setUpRecyclerView() {
        binding.movieListRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = homeAdapter
        }
    }
}