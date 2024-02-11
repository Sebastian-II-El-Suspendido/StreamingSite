package com.example.streamingsite

data class MovieDataClass(val id: Int,
                          val title: String,
                          val overview: String,
                          val posterPath: String,
                          val vote_average: Int,
                          val genreIds: List<Int>,
                          val release_date: String    ) {

}