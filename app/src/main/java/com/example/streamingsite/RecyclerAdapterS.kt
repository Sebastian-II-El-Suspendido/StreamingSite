package com.example.streamingsite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
class RecyclerAdapterS(val context: Context) :
    ListAdapter<SeriesDataClass, RecyclerAdapterS.RecyclerViewHolderS>(DIFF_CALLBACK) {

    class RecyclerViewHolderS(view: View) : RecyclerView.ViewHolder(view) {
        val posterImageView: ShapeableImageView = view.findViewById(R.id.posterView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolderS {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecyclerViewHolderS(layoutInflater.inflate(R.layout.recycleritems, parent, false))


    }

    override fun onBindViewHolder(holder: RecyclerViewHolderS, position: Int) {
        val item = getItem(position) // Ahora usamos getItem() proporcionado por ListAdapter
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        holder.posterImageView.load(imageUrl) {
            // Configuración adicional para la carga de imágenes si es necesario
        }

        holder.posterImageView.setOnClickListener {
            val intent = Intent(context, InfoSerie::class.java).apply {
                val genreIds = item.genreIds
                val genreNames = genreIds.map { id -> ListaGeneros.getGenreNameById(id) }
                // Pasar datos de la película a la Activity de detalles
                putExtra("MOVIE_ID", item.id)
                putExtra("MOVIE_TITLE", item.name)
                putExtra("MOVIE_POSTER", imageUrl)
                putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                putExtra("MOVIE_RELEASE_DATE", item.firstAirDate)
                putExtra("MOVIE_OVERVIEW", item.overview)
                putExtra("MOVIE_RATE", item.vote_average.toString())
                Log.v("Paso Datos","Correcto")
                // Agrega cualquier otro dato que necesites
            }
            context.startActivity(intent)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SeriesDataClass>() {
            override fun areItemsTheSame(oldItem: SeriesDataClass, newItem: SeriesDataClass): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: SeriesDataClass, newItem: SeriesDataClass): Boolean = oldItem == newItem
        }
    }
}


/*
    val item = SeriesList[position] // getItem() es un método de ListAdapter
        val imageUrl = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        holder.posterImageView.load(imageUrl) {
            // Configuración adicional para la carga de imágenes si es necesario
        }

        holder.posterImageView.setOnClickListener {
            val intent = Intent(context, InfoSerie::class.java).apply {
                val genreIds = item.genreIds
                val genreNames = genreIds.map { id -> ListaGeneros.getGenreNameById(id) }
                // Pasar datos de la película a la Activity de detalles
                putExtra("MOVIE_ID", item.id)
                putExtra("MOVIE_TITLE", item.name)
                putExtra("MOVIE_POSTER", imageUrl)
                putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                putExtra("MOVIE_RELEASE_DATE", item.firstAirDate)
                putExtra("MOVIE_OVERVIEW", item.overview)
                putExtra("MOVIE_RATE", item.vote_average.toString())
                Log.v("Paso Datos","Correcto")
                // Agrega cualquier otro dato que necesites
            }
            context.startActivity(intent)
        }


 */