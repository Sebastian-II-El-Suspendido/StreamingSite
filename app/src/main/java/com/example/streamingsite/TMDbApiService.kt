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
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

     //Netflix
    @GET("discover/movie")
    suspend fun getOriginals(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_companies") companyId: Int = 178464,
        @Query("page") page: Int = 1
    ) : Response<MovieResponseList>

    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ) : Response<MovieResponseList>
    @GET("movie/upcoming")

    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>


    @GET("discover/movie")
    suspend fun getHorrorMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "27"
    ): Response<MovieResponseList>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponseList>

    //Series

    @GET("tv/popular")
    suspend fun getListSeriesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<SeriesResponseList>

    //Netflix Originals (Se asume que Netflix también produce series originales)
    @GET("discover/tv")
    suspend fun getOriginalSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_networks") networkId: Int = 213, // ID de Netflix como productora de series
        @Query("page") page: Int = 1
    ): Response<SeriesResponseList>

    @GET("tv/top_rated")
    suspend fun getSeriesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<SeriesResponseList>

    @GET("tv/on_the_air")
    suspend fun getSeriesOnTheAir(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<SeriesResponseList>

    @GET("discover/tv")
    suspend fun getHorrorSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "9648" // Se asume suspense/thriller para series de horror
    ): Response<SeriesResponseList>

    @GET("tv/airing_today")
    suspend fun getSeriesAiringToday(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<SeriesResponseList>


    //Géneros

    //Accion
    @GET("discover/tv")
    suspend fun getActionSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10759"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getActionMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "28"
    ): Response<MovieResponseList>

    //Aventuras

    @GET("discover/tv")
    suspend fun getAventureSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10759"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getAventureMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "12"
    ): Response<MovieResponseList>

    //Ciencia ficción

    @GET("discover/tv")
    suspend fun getSciFiSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10765"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getSciFiMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "878"
    ): Response<MovieResponseList>

    //Comedia
    @GET("discover/tv")
    suspend fun getComedySeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "35"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getComedyMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "35"
    ): Response<MovieResponseList>

    //Fantasía

    @GET("discover/tv")
    suspend fun getFantasySeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10765"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getFantasyMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "14"
    ): Response<MovieResponseList>


    //Romance

    @GET("discover/tv")
    suspend fun getRomanceSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10749"
    ): Response<SeriesResponseList>

    @GET("discover/movie")
    suspend fun getRomanceMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String = "10749"
    ): Response<MovieResponseList>

}






data class MovieResponseList(
    @SerializedName("results") val results: List<MovieDataClass>
)

data class SeriesResponseList(
        @SerializedName("results") val results: List<SeriesDataClass>
        )

