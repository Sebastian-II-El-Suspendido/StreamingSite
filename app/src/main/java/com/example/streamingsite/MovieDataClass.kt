package com.example.streamingsite

import com.google.gson.annotations.SerializedName

data class MovieDataClass(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("release_date") val release_date: String    ) {

}

