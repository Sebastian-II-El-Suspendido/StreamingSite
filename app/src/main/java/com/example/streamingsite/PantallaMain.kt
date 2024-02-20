package com.example.streamingsite

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PantallaMain : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaMainBinding
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView
    private lateinit var viewAdapter1: RecyclerAdapter
    private lateinit var viewAdapter2: RecyclerAdapter
    private lateinit var viewAdapter3: RecyclerAdapter
    private lateinit var viewAdapter4: RecyclerAdapter
    private lateinit var viewAdapter1S: RecyclerAdapterS
    private lateinit var viewAdapter2S: RecyclerAdapterS
    private lateinit var viewAdapter3S: RecyclerAdapterS
    private lateinit var viewAdapter4S: RecyclerAdapterS
    private lateinit var recyclerGenero: RecyclerView
    private lateinit var viewAdapterG: RecyclerAdapter2
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var scrollP: ScrollView
    private lateinit var scrollG: LinearLayout
    private lateinit var listita: List<MediaItem>
    var jobMovies: Job? = null
    var jobSeries: Job? = null
    private var Language = "en-US"
    // val title = findViewById<TextView>(R.id.titleView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scrollP= binding.scrollView2
        scrollG= binding.recyclercat
        recyclerView1 = findViewById(R.id.recyclerView1)
        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView3 = findViewById(R.id.recyclerView3)
        recyclerView4 = findViewById(R.id.recyclerView4)
        recyclerGenero= findViewById(R.id.recyclerViewCat)


        llamadaDatosPeliculas()
        initRecyclerViewPeliculas()

        val storageReference = Firebase.storage.reference
        val imageReference = storageReference.child("Iconos/volume_0.png")


        imageReference.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            // Ahora puedes usar Glide para cargar la imagen
            cargarImagenConGlide(imageUrl)
        }.addOnFailureListener {
            // Manejar el caso de que no se pueda obtener la URL
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


    }


    private fun cargarImagenConGlide(imageUrl: String) {
        val imageView = findViewById<ImageView>(R.id.imagePerfil)
        Glide.with(this /* context */)
            .load(imageUrl) // Carga la imagen desde la URL
            .circleCrop()
            .into(imageView) // Carga la imagen en el ImageView
    }


    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val currentLanguage = sharedPreferences.getString("selectedLanguage", "en-US")

        if (currentLanguage != Language) {
            if (currentLanguage != null) {
                Language = currentLanguage
            }
            // Aquí es donde realizarías la llamada a la API con el nuevo idioma.
            // Esto podría ser simplemente llamar a una función que hace la solicitud de la red y actualiza la UI.
           // fetchMovies(currentLanguage)
        }
    }




    private fun llamadaDatosPeliculas(): Job{
        jobSeries?.cancel()
        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                // Retrofit manejará la llamada asíncrona en el fondo por ti.
                val response = getRetrofit().create(TMDbApiService::class.java).getListMoviesPopular("1ea435c6f57a9f3833b110a6061d8f93", Language)
                // response es ahora del tipo Response<MovieResponseList>
                if (response.isSuccessful) {
                    // Aquí se accede al cuerpo de la respuesta si la llamada fue exitosa.
                    val movies = response.body()?.results // Esta línea debería funcionar correctamente.
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        // Actualizar el dataset del adapter y refrescar el RecyclerView
                        viewAdapter1.MovieList=it
                        viewAdapter1.submitList(it)
                        it.forEach { movie ->
                            Log.d("Movie Title", movie.title)
                        }
                    }
                } else {
                    // Manejar la respuesta no exitosa
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción que pueda ocurrir durante la llamada a la API.
                Log.e("API Error", "Call failed with error", e)
            }
        }


    lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getMoviesTopRated("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        viewAdapter2.MovieList=it
                        viewAdapter2.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }


        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getOriginals("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        viewAdapter3.MovieList=it
                        viewAdapter3.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }


        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getNowPlayingMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    movies?.let {
                        viewAdapter4.MovieList=it
                        viewAdapter4.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }

        jobMovies = lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getHorrorMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")

                    var index = 0
                    movies?.let { moviesList ->
                        val numberOfMovies = moviesList.size
                        while (isActive) { // Utiliza isActive para respetar la cancelación
                            if (numberOfMovies > 0) {
                                val movie = moviesList[index % numberOfMovies]

                                withContext(Dispatchers.Main) {
                                    // Asegúrate de actualizar la UI en el hilo principal
                                    val posterImageView: ImageView = findViewById(R.id.imageView2)
                                    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                                    Log.v("Serie",movie.title)
                                    posterImageView.load(imageUrl)
                                    binding.play.setOnClickListener {
                                        val intent =
                                            Intent(this@PantallaMain, InfoSerie::class.java).apply {
                                                val genreIds = movie.genreIds
                                                val genreNames = genreIds.map { id ->
                                                    ListaGeneros.getGenreNameById(id)
                                                }
                                                // Pasar datos de la película a la Activity de detalles
                                                putExtra("MOVIE_ID", movie.id)
                                                putExtra("MOVIE_TITLE", movie.title)
                                                putExtra("MOVIE_POSTER", imageUrl)
                                                putExtra(
                                                    "MOVIE_GENRES",
                                                    genreNames.joinToString(", ")
                                                )
                                                putExtra("MOVIE_RELEASE_DATE", movie.release_date)
                                                putExtra("MOVIE_OVERVIEW", movie.overview)
                                                putExtra(
                                                    "MOVIE_RATE",
                                                    movie.vote_average.toString()
                                                )
                                                Log.v("Paso Datos", "Correcto")
                                                // Agrega cualquier otro dato que necesites
                                            }
                                        this@PantallaMain.startActivity(intent)
                                    }
                                }

                                index++
                                delay(5000) // Espera 5 segundos antes de continuar con el próximo elemento
                            } else {
                                delay(5000) // Espera si la lista está vacía
                            }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }

        return jobMovies!!


    }



    private fun llamarDatosSeries(): Job{
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                // Retrofit manejará la llamada asíncrona en el fondo por ti.
                val response = getRetrofit().create(TMDbApiService::class.java).getListSeriesPopular("1ea435c6f57a9f3833b110a6061d8f93", Language)
                // response es ahora del tipo Response<MovieResponseList>
                if (response.isSuccessful) {
                    // Aquí se accede al cuerpo de la respuesta si la llamada fue exitosa.
                    val movies = response.body()?.results // Esta línea debería funcionar correctamente.
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        // Actualizar el dataset del adapter y refrescar el RecyclerView
                        viewAdapter1S.submitList(it)
                        it.forEach { movie ->
                            Log.d("Movie Title", movie.name)
                        }
                    }
                } else {
                    // Manejar la respuesta no exitosa
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción que pueda ocurrir durante la llamada a la API.
                Log.e("API Error", "Call failed with error", e)
            }
        }

        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getSeriesTopRated("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        viewAdapter2S.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }


        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getOriginalSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        viewAdapter3S.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }


        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getSeriesAiringToday("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    movies?.let {
                        viewAdapter4S.submitList(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }



        jobSeries=lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            try {
                val response = getRetrofit().create(TMDbApiService::class.java).getHorrorSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    var index = 0
                    movies?.let { moviesList ->
                        val numberOfMovies = moviesList.size
                        while (isActive) { // Utiliza isActive para respetar la cancelación
                            if (numberOfMovies > 0) {
                                val movie = moviesList[index % numberOfMovies]
                                withContext(Dispatchers.Main) {
                                    Log.v("Serie",movie.name)
                                    // Asegúrate de actualizar la UI en el hilo principal
                                    val posterImageView: ImageView = findViewById(R.id.imageView2)
                                    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                                    posterImageView.load(imageUrl)
                                    binding.play.setOnClickListener {
                                        val intent =
                                            Intent(this@PantallaMain, InfoSerie::class.java).apply {
                                                val genreIds = movie.genreIds
                                                val genreNames = genreIds.map { id ->
                                                    ListaGeneros.getGenreNameById(id)
                                                }
                                                // Pasar datos de la película a la Activity de detalles
                                                putExtra("MOVIE_ID", movie.id)
                                                putExtra("MOVIE_TITLE", movie.name)
                                                putExtra("MOVIE_POSTER", imageUrl)
                                                putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                                                putExtra("MOVIE_RELEASE_DATE", movie.firstAirDate)
                                                putExtra("MOVIE_OVERVIEW", movie.overview)
                                                putExtra("MOVIE_RATE", movie.vote_average.toString())
                                                Log.v("Paso Datos", "Correcto")
                                                // Agrega cualquier otro dato que necesites
                                            }
                                        this@PantallaMain.startActivity(intent)
                                    }

                                }

                                index++
                                delay(5000) // Espera 5 segundos antes de continuar con el próximo elemento
                            } else {
                                delay(5000) // Espera si la lista está vacía
                            }
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Código de error: ${response.code()}, Cuerpo del error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Call failed with error", e)
            }
        }

        return jobSeries!!

    }



/*
val intent = Intent(this@PantallaMain, InfoSerie::class.java).apply {
                                    val genreIds = movie.genreIds
                                    val genreNames = genreIds.map { id -> ListaGeneros.getGenreNameById(id) }
                                    // Pasar datos de la película a la Activity de detalles
                                    putExtra("MOVIE_ID", movie.id)
                                    putExtra("MOVIE_TITLE", movie.title)
                                    putExtra("MOVIE_POSTER", imageUrl)
                                    putExtra("MOVIE_GENRES", genreNames.joinToString(", "))
                                    putExtra("MOVIE_RELEASE_DATE", movie.release_date)
                                    putExtra("MOVIE_OVERVIEW", movie.overview)
                                    putExtra("MOVIE_RATE", movie.vote_average.toString())
                                    Log.v("Paso Datos","Correcto")
                                    // Agrega cualquier otro dato que necesites
                                }
 */



    private fun llamarDatosAccion(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getActionMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getActionSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

            listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun llamarDatosAventuras(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getAventureMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getAventureSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

            listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                    Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun llamarDatosSciFi(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getSciFiMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getSciFiSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

            listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                    Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun llamarDatosComedia(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getComedyMovie("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getComedySeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

             listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                    Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun llamarDatosFantasia(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getFantasyMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getFantasySeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

            listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                    Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun llamarDatosRomance(){
        var movies = listOf<MovieDataClass>()
        var series = listOf<SeriesDataClass>()
        jobSeries?.cancel()
        jobMovies?.cancel()
        lifecycleScope.launch {
            Log.e("aviso", "Launching Coroutine")
            val moviesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getRomanceMovies("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val seriesDeferred = async(Dispatchers.IO) {
                getRetrofit().create(TMDbApiService::class.java).getRomanceSeries("1ea435c6f57a9f3833b110a6061d8f93", Language)
            }
            val moviesResponse = moviesDeferred.await()
            val seriesResponse = seriesDeferred.await()
            if (moviesResponse.isSuccessful) {
                movies = moviesResponse.body()!!.results
            } else {
                Log.e("API Error", "Movies error: ${moviesResponse.errorBody()?.string()}")
            }

            if (seriesResponse.isSuccessful) {
                series = seriesResponse.body()!!.results
            } else {
                Log.e("API Error", "Series error: ${seriesResponse.errorBody()?.string()}")
            }

            listita = intercalarListas(movies,series)

            for (i in listita){
                if (i.type == MediaType.MOVIE)
                    Log.v("Pelicula",i.title)
                else
                    Log.v("Serie",i.title)
            }

            withContext(Dispatchers.Main) {
                viewAdapterG.updateData(listita)
            }

        }

    }

    private fun initRecyclerGenero(){
        val emptyMedia = listOf<MediaItem>()
        recyclerGenero.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        viewAdapterG= RecyclerAdapter2(emptyMedia, this)
        recyclerGenero.adapter = viewAdapterG

    }

    private fun initRecyclerViewPeliculas() {
        recyclerView1.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val emptyList = listOf<MovieDataClass>()
        viewAdapter1 = RecyclerAdapter(this,emptyList) // Inicializa viewAdapter aquí
        recyclerView1.adapter = viewAdapter1 // Asigna viewAdapter al RecyclerView

        recyclerView2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter2 = RecyclerAdapter(this,emptyList) // Inicializa viewAdapter aquí
        recyclerView2.adapter = viewAdapter2 // Asigna viewAdapter al RecyclerView

        recyclerView3.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter3 = RecyclerAdapter(this,emptyList) // Inicializa viewAdapter aquí
        recyclerView3.adapter = viewAdapter3 // Asigna viewAdapter al RecyclerView

        recyclerView4.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter4 = RecyclerAdapter(this,emptyList) // Inicializa viewAdapter aquí
        recyclerView4.adapter = viewAdapter4 // Asigna viewAdapter al RecyclerView
    }

    private fun initRecyclerViewSeries() {
        recyclerView1.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter1S = RecyclerAdapterS(this) // Inicializa viewAdapter aquí
        recyclerView1.adapter = viewAdapter1S // Asigna viewAdapter al RecyclerView

        recyclerView2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter2S = RecyclerAdapterS(this) // Inicializa viewAdapter aquí
        recyclerView2.adapter = viewAdapter2S // Asigna viewAdapter al RecyclerView

        recyclerView3.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter3S = RecyclerAdapterS(this) // Inicializa viewAdapter aquí
        recyclerView3.adapter = viewAdapter3S // Asigna viewAdapter al RecyclerView

        recyclerView4.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        viewAdapter4S = RecyclerAdapterS(this) // Inicializa viewAdapter aquí
        recyclerView4.adapter = viewAdapter4S // Asigna viewAdapter al RecyclerView
    }

    fun intercalarListas(listaPeliculas: List<MovieDataClass>, listaSeries: List<SeriesDataClass>): List<MediaItem> {
        val listaIntermedia = mutableListOf<MediaItem>()
        val iteradorPeliculas = listaPeliculas.iterator()
        val iteradorSeries = listaSeries.iterator()

        while (iteradorPeliculas.hasNext() || iteradorSeries.hasNext()) {
            if (iteradorPeliculas.hasNext()) listaIntermedia.add(convertirAPuntoComun(iteradorPeliculas.next(), MediaType.MOVIE))
            if (iteradorSeries.hasNext()) listaIntermedia.add(convertirAPuntoComun(iteradorSeries.next(), MediaType.SERIES))
        }
        return listaIntermedia
    }

    /*

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
     */

    fun convertirAPuntoComun(media: Any, tipo: MediaType): MediaItem {
        return if (tipo == MediaType.MOVIE) {
            val movie = media as MovieDataClass
            MediaItem(movie.id, movie.title, movie.overview, movie.posterPath,movie.vote_average, movie.genreIds,movie.release_date,MediaType.MOVIE)
        } else {
            val series = media as SeriesDataClass
            MediaItem(series.id, series.name, series.overview,series.posterPath, series.vote_average, series.genreIds,series.firstAirDate, MediaType.SERIES)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
         button1= binding.buttonpelis
         button2=binding.buttonseries

        setupSpinner()
        binding.imagePerfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }
        binding.play.setOnClickListener {
            val intent = Intent(this, InfoSerie::class.java)
            startActivity(intent)
        }
        val home = findViewById<ImageView>(R.id.iconologo)
        home.setOnClickListener{
            val intent = Intent(this, PantallaMain::class.java)
            startActivity(intent)
        }

        button1.isSelected = false
        button2.isSelected = false


        button1.setOnClickListener {
            scrollG.visibility= View.GONE
            scrollP.visibility = View.VISIBLE
            it.isSelected = !it.isSelected
            button2.isSelected = false
            updateButtonAppearance(button1, it.isSelected)
            updateButtonAppearance(button2, button2.isSelected)
            binding.spinner.setSelection(0)
            initRecyclerViewPeliculas()
            llamadaDatosPeliculas()
        }

        button2.setOnClickListener {
            scrollG.visibility= View.GONE
            scrollP.visibility = View.VISIBLE
            it.isSelected = !it.isSelected
            button1.isSelected = false
            updateButtonAppearance(button1, button1.isSelected)
            updateButtonAppearance(button2, it.isSelected)
            binding.spinner.setSelection(0)
            initRecyclerViewSeries()
            llamarDatosSeries()
        }



       return true
    }



    private fun setupSpinner() {
        val categories = resources.getStringArray(R.array.Cat)
        //val scrollS = binding.ScollView3
        val adapter = object : CustomSpinnerAdapter(this, R.layout.custom_spinner, categories) {
            override fun isEnabled(position: Int): Boolean {

                return position != 0
            }
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return super.getDropDownView(position, convertView, parent) as TextView
            }
        }
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        binding.spinner.adapter = adapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    button1.isSelected = false
                    button2.isSelected = false
                    updateButtonAppearance(button1, false)
                    updateButtonAppearance(button2, false)
                    view.setBackgroundResource(R.drawable.botonseleccionado)
                    (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#1E1F22"))
                }
                /*
                    <item>Acción</item>
        <item>Aventuras</item>
        <item>Ciencia Ficción</item>
        <item>Comedia</item>
        <item>Fantasía</item>
        <item>Romance</item>
                 */

                when(position){
                    1->{
                        scrollP.visibility= View.GONE
                        initRecyclerGenero()
                        llamarDatosAccion()
                        scrollG.visibility= View.VISIBLE
                    }
                    2->{
                        scrollP.visibility= View.GONE
                        initRecyclerGenero()
                        llamarDatosAventuras()
                        scrollG.visibility= View.VISIBLE

                    }
                   3->{
                        scrollP.visibility= View.GONE
                       initRecyclerGenero()
                       llamarDatosSciFi()
                       scrollG.visibility= View.VISIBLE

                    }
                    4->{
                        scrollP.visibility= View.GONE
                        initRecyclerGenero()
                        llamarDatosComedia()
                        scrollG.visibility= View.VISIBLE

                    }
                    5->{
                        scrollP.visibility= View.GONE
                        initRecyclerGenero()
                        llamarDatosFantasia()
                        scrollG.visibility= View.VISIBLE

                    }
                   6->{
                        scrollP.visibility= View.GONE
                       initRecyclerGenero()
                       llamarDatosRomance()
                       scrollG.visibility= View.VISIBLE

                    }

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    open class CustomSpinnerAdapter(context: Context, resource: Int, objects: Array<String>) :
        ArrayAdapter<String>(context, resource, objects) {
        }
    }

    fun updateButtonAppearance(button: Button, isSelected: Boolean) {
        if (isSelected) {
            button.setBackgroundColor(Color.WHITE)
            button.setTextColor(Color.parseColor("#1E1F22"))
        } else {
            button.setBackgroundColor(Color.parseColor("#1E1F22"))
            button.setTextColor(Color.WHITE)
        }
    }