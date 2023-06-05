package com.swancodes.showtime.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.swancodes.showtime.adapters.ItemClickListener
import com.swancodes.showtime.adapters.SearchAdapter
import com.swancodes.showtime.data.api.MovieApiClient
import com.swancodes.showtime.data.model.Movie
import com.swancodes.showtime.databinding.FragmentSearchBinding
import com.swancodes.showtime.util.hide
import com.swancodes.showtime.util.show
import com.swancodes.showtime.viewmodel.MovieApiStatus
import com.swancodes.showtime.viewmodel.MovieViewModel
import com.swancodes.showtime.viewmodel.MovieViewModelFactory

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieApiClient.retrofitService)
    }
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter(object : ItemClickListener {
            override fun onItemClick(movie: Movie) {
                findNavController().navigate(SearchFragmentDirections.toMovieDetailFragment(movie))
            }
        })
    }

    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

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
                    viewModel.searchResult.observe(viewLifecycleOwner) {
                        searchAdapter.submitList(it)
                    }
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        binding.movieSearchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.isNotEmpty()) {
                        viewModel.searchMovies(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.hide()
            viewModel.searchMovies(binding.movieSearchView.toString())
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpRecyclerView() {
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun showErrorMessage() {
        snackBar = Snackbar.make(binding.root, "A problem occurred. Please try again.", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                if (binding.movieSearchView.isNotEmpty()) {
                    viewModel.searchMovies(binding.movieSearchView.toString())
                }
                else {
                    hideErrorMessage()
                }
            }
            snackBar?.show()
    }

    private fun hideErrorMessage() {
        snackBar?.dismiss()
    }
}