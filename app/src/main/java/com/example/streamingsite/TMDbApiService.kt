package com.example.streamingsite

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {

    @GET("movie/popular")
     suspend fun getListMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

    @GET("discover/movie")
    suspend fun getOriginals(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_companies") companyId: Int = 178464,
        @Query("page") page: Int = 1
    ) : Response<MovieResponseList>

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : Response<MovieResponseList>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<Moviedetails>

    @GET("discover/movie")
    suspend fun getHorrorMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "27"
    ): Response<MovieResponseList>


    @GET("discover/movie")
    suspend fun getMoviesForKids(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("certification_country") country: String = "US",
        @Query("certification.lte") certification: String = "G",
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

    // Agrega más endpoints si es necesario
}


data class MovieResponseList(
    @SerializedName("results") val results: List<MovieDataClass>
)

data class Moviedetails(
    @SerializedName("result") val result:MovieDataClass
)