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

class RecyclerAdapter2(var ItemMediaList:List<MediaItem>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter2.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //    val titleView: TextView
        val posterImageView1: ShapeableImageView
        val posterImageView2: ShapeableImageView
        val posterImageView3: ShapeableImageView



        init {

            posterImageView1 = view.findViewById(R.id.posterView1)
            posterImageView2 = view.findViewById(R.id.posterView2)
            posterImageView3 = view.findViewById(R.id.posterView3)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecyclerViewHolder(layoutInflater.inflate(R.layout.recycleritemvertical, parent, false))

    }

    fun updateData(newItemList: List<MediaItem>) {
        this.ItemMediaList = newItemList
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    override fun getItemCount(): Int = ItemMediaList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = ItemMediaList[position]
        Log.v("Paso por aqui", "Paso2")
        // Asume que tienes una base URL para las imágenes. Ajusta según sea necesario.
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        val startIndex = position * 3
        val imageViews = listOf(holder.posterImageView1, holder.posterImageView2, holder.posterImageView3)

        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener{
                val intent = Intent(context, InfoSerie::class.java).apply {
                    val genreIds = item.genreIds
                    val genreNames = genreIds.map { id -> ListaGeneros.getGenreNameById(id) }
                    // Pasar datos de la película a la Activity de detalles
                    putExtra("MOVIE_ID", item.id) // Ejemplo: ID de la película
                    putExtra("MOVIE_TITLE", item.title) // Ejemplo: Título de la película
                    putExtra("MOVIE_POSTER", imageUrl)
                    putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                    putExtra("MOVIE_RELEASE_DATE", item.releaseDate)
                    putExtra("MOVIE_OVERVIEW", item.overview)
                    putExtra("MOVIE_RATE", item.voteAverage.toString())
                    // Agrega cualquier otro dato que necesites
                }
                context.startActivity(intent)

            }
            val imageUrl = ItemMediaList.getOrNull(startIndex + index)?.posterPath?.let {
            "https://image.tmdb.org/t/p/w500$it"
        }
            imageUrl?.let { imageView.load(it) }
        }

    }
}