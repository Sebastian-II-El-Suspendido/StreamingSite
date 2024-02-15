package com.example.streamingsite

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import coil.load
import com.example.streamingsite.databinding.ActivityInfoSerieBinding
import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import java.net.URLEncoder

class InfoSerie : AppCompatActivity() {
    private lateinit var binding: ActivityInfoSerieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoSerieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Extraer datos del Intent
        val movieId = intent.getIntExtra("MOVIE_ID", 0) // Asegúrate de usar el valor predeterminado adecuado
        val movieTitle = intent.getStringExtra("MOVIE_TITLE")
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")
        val movieGenres = intent.getStringExtra("MOVIE_GENRES")
        val movieReleaseDate = intent.getStringExtra("MOVIE_RELEASE_DATE")
        val movieOverview = intent.getStringExtra("MOVIE_OVERVIEW")
        val movieRate = intent.getStringExtra("MOVIE_RATE")
        Log.d("Movie Title : INFO SERIE", movieTitle.toString())

        /*
        Log.v("2",imageUrl)
        Log.v("3",movie.genreIds.joinToString(", "))
        Log.v("4",movie.release_date)
        Log.v("5",movie.overview)
        Log.v("6",movie.vote_average.toString())


        val movieId2 = intent.getIntExtra("MOVIE_ID2", 0) // Asegúrate de usar el valor predeterminado adecuado
        val movieTitle2 = intent.getStringExtra("MOVIE_TITLE2")
        val moviePoster2 = intent.getStringExtra("MOVIE_POSTER2")
        val movieGenres2 = intent.getStringExtra("MOVIE_GENRES2")
        val movieReleaseDate2 = intent.getStringExtra("MOVIE_RELEASE_DATE2")
        val movieOverview2 = intent.getStringExtra("MOVIE_OVERVIEW2")
        val movieRate2 = intent.getStringExtra("MOVIE_RATE2")
*/
        // Usar los datos extraídos para cargar los detalles de la película
        // Por ejemplo, configurar el título en un TextView

        binding.textMedia.text = movieRate
        binding.textTitulo.text = movieTitle
        binding.textDescripcion.text = movieOverview
        binding.textGeneros.text = movieGenres
        binding.textRelease.text= movieReleaseDate
        binding.imageView2.load(moviePoster)


        /*
        binding.textMedia.text = movieRate2
        binding.textTitulo.text = movieTitle2
        binding.textDescripcion.text = movieOverview2
        binding.textGeneros.text = movieGenres2
        binding.textRelease.text= movieReleaseDate2
        binding.imageView2.load(moviePoster2)
*/
        binding.play.setOnClickListener{
            if (movieTitle != null) {
                openYouTubeSearch(this, "$movieTitle trailer")
            }
        }

    }


    fun openYouTubeSearch(context: Context, movieTitle: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=${URLEncoder.encode(movieTitle, "UTF-8")}"))
        context.startActivity(intent)
    }
}

