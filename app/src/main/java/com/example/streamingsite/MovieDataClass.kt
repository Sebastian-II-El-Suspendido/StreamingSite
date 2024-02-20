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

data class SeriesDataClass(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String, // Título de la serie
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("first_air_date") val firstAirDate: String // Fecha de primera emisión
)

enum class MediaType {
    MOVIE, SERIES
}

data class MediaItemGroup(
    val items: List<MediaItem>
)

data class MediaItem(
    val id: Int,
    val title: String, // Usaremos "title" para ambos, películas y series
    val overview: String,
    val posterPath: String,
    val voteAverage: Float,
    val genreIds: List<Int>,
    val releaseDate: String, // "release_date" para películas, "first_air_date" para series
    val type: MediaType
)
