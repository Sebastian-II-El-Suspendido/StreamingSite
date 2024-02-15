package com.example.streamingsite

sealed class ListaGeneros(val id: Int, val name: String) {
    object Action : ListaGeneros(28, "Action")
    object Adventure : ListaGeneros(12, "Adventure")
    object Animation : ListaGeneros(16, "Animation")
    object Comedy : ListaGeneros(35, "Comedy")
    object Crime : ListaGeneros(80, "Crime")
    object Documentary : ListaGeneros(99, "Documentary")
    object Drama : ListaGeneros(18, "Drama")
    object Family : ListaGeneros(10751, "Family")
    object Fantasy : ListaGeneros(14, "Fantasy")
    object History : ListaGeneros(36, "History")
    object Horror : ListaGeneros(27, "Horror")
    object Music : ListaGeneros(10402, "Music")
    object Mystery : ListaGeneros(9648, "Mystery")
    object Romance : ListaGeneros(10749, "Romance")
    object ScienceFiction : ListaGeneros(878, "Science Fiction")
    object TVMovie : ListaGeneros(10770, "TV Movie")
    object Thriller : ListaGeneros(53, "Thriller")
    object War : ListaGeneros(10752, "War")
    object Western : ListaGeneros(37, "Western")


    companion object {
        fun getGenreNameById(id: Int): String {
            return when (id) {
                Action.id -> Action.name
                Adventure.id -> Adventure.name
                Animation.id -> Animation.name
                Comedy.id -> Animation.name
                Crime.id -> Animation.name
                Documentary.id -> Animation.name
                Drama.id -> Drama.name
                Family.id -> Family.name
                Fantasy.id -> Fantasy.name
                History.id -> History.name
                Horror.id -> Horror.name
                Music.id -> Music.name
                Mystery.id -> Mystery.name
                Romance.id -> Romance.name
                ScienceFiction.id -> ScienceFiction.name
                TVMovie.id -> TVMovie.name
                Thriller.id -> Thriller.name
                War.id -> War.name
                Western.id -> Western.name
                else -> "Unknown"
            }
        }
    }
}