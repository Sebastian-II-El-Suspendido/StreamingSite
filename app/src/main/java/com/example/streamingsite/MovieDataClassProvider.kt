package com.example.streamingsite

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query


class MovieDataClassProvider {
}

interface TMDbApiService {
    @GET("movie/popular")

    fun getListOfMovies(
        @Query("api_key") apiKey: String = "",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<MovieApiResponse>

    // Agrega más endpoints si es necesario
}
object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val tmDbApi: TMDbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDbApiService::class.java)
    }
}