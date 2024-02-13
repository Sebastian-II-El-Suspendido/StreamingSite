package com.example.streamingsite

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/popular")
     fun getListMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<MovieResponseList>

    @GET("discover/movie")
    suspend fun getOriginals(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_companies") companyId: Int = 178464,
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseList


    @GET("discover/movie")
    suspend fun getMoviesForKids(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("certification_country") country: String = "US",
        @Query("certification.lte") certification: String = "G",
        @Query("page") page: Int = 1
    ): MovieResponseList

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponseList

    // Agrega más endpoints si es necesario
}


data class MovieResponseList(
    @SerializedName("results") val results: List<MovieDataClass>
)