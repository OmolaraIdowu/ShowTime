package com.swancodes.showtime.viewmodel

import androidx.lifecycle.*
import com.swancodes.showtime.data.api.MovieApiService
import com.swancodes.showtime.data.model.Movie
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class MovieViewModel(private val api: MovieApiService) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus> = _status

    private val _searchResult = MutableLiveData<List<Movie>>()
    val searchResult: LiveData<List<Movie>> = _searchResult

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                _movies.value = api.getPopularMovies().results
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _movies.value = listOf()
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            try {
                _searchResult.value = api.searchMovies(query).results
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _searchResult.value = listOf()
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val api: MovieApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(api) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}