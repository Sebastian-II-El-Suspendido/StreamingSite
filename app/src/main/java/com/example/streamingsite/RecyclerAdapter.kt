package com.example.streamingsite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView

private fun ShapeableImageView.setOnClickListener(function: (v: View) -> Unit) {

}

class RecyclerAdapter(var MovieList:List<MovieDataClass>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //    val titleView: TextView
        val posterImageView: ShapeableImageView

        init {
            //  titleView = view.findViewById<TextView>(R.id.titleView)
            posterImageView = view.findViewById<ShapeableImageView>(R.id.posterView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecyclerViewHolder(layoutInflater.inflate(R.layout.recycleritems, parent, false))

    }

    override fun getItemCount(): Int = MovieList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = MovieList[position]
        // holder.titleView.text = item.title
        Log.v("Paso por aqui", "Paso2")
        // Asume que tienes una base URL para las imágenes. Ajusta según sea necesario.
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        holder.posterImageView.load(imageUrl) {
        }
        holder.posterImageView.setOnClickListener {
                val intent = Intent(context, InfoSerie::class.java).apply {
                    val genreIds = item.genreIds
                    val genreNames = genreIds.map { id -> ListaGeneros.getGenreNameById(id) }
                    // Pasar datos de la película a la Activity de detalles
                    putExtra("MOVIE_ID", item.id) // Ejemplo: ID de la película
                    putExtra("MOVIE_TITLE", item.title) // Ejemplo: Título de la película
                    putExtra("MOVIE_POSTER", imageUrl)
                    putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                    putExtra("MOVIE_RELEASE_DATE", item.release_date)
                    putExtra("MOVIE_OVERVIEW", item.overview)
                    putExtra("MOVIE_RATE", item.vote_average.toString())

                    // Agrega cualquier otro dato que necesites
                }
                context.startActivity(intent)
            }
    }
}

