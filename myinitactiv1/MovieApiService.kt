package com.example.myinitactiv1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<MovieResponse>
    // Método para obter detalhes de um filme
    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetailsResponse>
}
