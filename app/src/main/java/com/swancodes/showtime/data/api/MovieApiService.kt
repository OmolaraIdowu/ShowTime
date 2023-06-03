package com.swancodes.showtime.data.api

import com.swancodes.showtime.BuildConfig
import com.swancodes.showtime.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") searchQuery: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse
}