package com.example.streamingsite

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieDataClassProvider {
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