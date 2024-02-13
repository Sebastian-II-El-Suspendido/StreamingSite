package com.example.streamingsite

sealed class ListaGeneros(val id: Int, val name: String) {
    data object Action : ListaGeneros(28, "Action")
    data object Adventure : ListaGeneros(12, "Adventure")
    data object Animation : ListaGeneros(16, "Animation")
    data object Comedy : ListaGeneros(35, "Comedy")
    data object Crime : ListaGeneros(80, "Crime")
    data object Documentary : ListaGeneros(99, "Documentary")
    data object Drama : ListaGeneros(18, "Drama")
    data object Family : ListaGeneros(10751, "Family")
    data object Fantasy : ListaGeneros(14, "Fantasy")
    data object History : ListaGeneros(36, "History")
    data object Horror : ListaGeneros(27, "Horror")
    data object Music : ListaGeneros(10402, "Music")
    data object Mystery : ListaGeneros(9648, "Mystery")
    data object Romance : ListaGeneros(10749, "Romance")
    data object ScienceFiction : ListaGeneros(878, "Science Fiction")
    data object TVMovie : ListaGeneros(10770, "TV Movie")
    data object Thriller : ListaGeneros(53, "Thriller")
    data object War : ListaGeneros(10752, "War")
    data object Western : ListaGeneros(37, "Western")
}