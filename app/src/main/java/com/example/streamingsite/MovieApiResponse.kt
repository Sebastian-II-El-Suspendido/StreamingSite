package com.example.streamingsite

data class MovieApiResponse(
    val page: Int,
    val results: List<MovieDataClass>,
    val total_results: Int,
    val total_pages: Int
)