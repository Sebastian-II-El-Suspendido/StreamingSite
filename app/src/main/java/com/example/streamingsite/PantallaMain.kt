package com.example.streamingsite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PantallaMain : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerAdapter
    // val title = findViewById<TextView>(R.id.titleView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recyclerView2)


        initRecyclerView()
        Log.v("T","1")

        llamarDatos()
        Log.v("T","2")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    private fun llamarDatos() {
        lifecycleScope.launch {
            Log.e("aviso","Launching Corrutine")
            try {
                // Retrofit manejará la llamada asíncrona en el fondo por ti.
                val response = getRetrofit().create(TMDbApiService::class.java).getListMoviesPopular("1ea435c6f57a9f3833b110a6061d8f93")
                // response es ahora del tipo Response<MovieResponseList>
                if (response.isSuccessful) {
                    // Aquí se accede al cuerpo de la respuesta si la llamada fue exitosa.
                    val movies = response.body()?.results // Esta línea debería funcionar correctamente.
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        // Actualizar el dataset del adapter y refrescar el RecyclerView
                        viewAdapter.MovieList = it
                        viewAdapter.notifyDataSetChanged()
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
    }



/*


    private fun loadMovies() {
        RetrofitClient.tmDbApi.getListMoviesPopular("1ea435c6f57a9f3833b110a6061d8f93").enqueue(object : Callback<MovieResponseList> {
            override fun onResponse(call: Call<MovieResponseList>, response: Response<MovieResponseList>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    Log.d("API Response", "Movies: $movies")
                    movies?.let {
                        // Actualizar el dataset del adapter y refrescar el RecyclerView
                        viewAdapter.MovieList = it
                        viewAdapter.notifyDataSetChanged()
                        it.forEach { movie ->
                            Log.d("Movie Title", movie.title)
                        }

                    }
                } else {
                    // Manejar error
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    Log.e("API Error", "Código de error: $statusCode, Cuerpo del error: $errorBody")
                }
            }

            override fun onFailure(call: Call<MovieResponseList>, t: Throwable) {

                // Manejar fallo en la llamada a la API
                Log.e("API Error", "Call failed with error", t)
            }
        })
    }

*/

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val emptyList = listOf<MovieDataClass>()
        viewAdapter = RecyclerAdapter(emptyList) // Inicializa viewAdapter aquí
        recyclerView.adapter = viewAdapter // Asigna viewAdapter al RecyclerView
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as? SearchView
        setupSpinner()

        binding.videoView.visibility= View.GONE


        binding.imageButton.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

        binding.play.setOnClickListener {
            val intent = Intent(this, InfoSerie::class.java)
            startActivity(intent)
        }


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Implementar lógica de búsqueda aquí
                // Retorna true si el evento fue manejado
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Implementar lógica para cuando el texto cambie
                // Retorna true si el evento fue manejado
                return true
            }
        })

        return true
    }


    private fun setupSpinner() {
        // Crear un ArrayAdapter usando el array de strings y un spinner layout predeterminado
        ArrayAdapter.createFromResource(
            this,
            R.array.Cat,
            R.layout.custom_spinner
        ).also { adapter ->
            // Especificar el layout a usar cuando la lista de opciones aparece
            adapter.setDropDownViewResource(R.layout.custom_spinner)
            // Aplicar el adapter al spinner
            binding.spinner.adapter = adapter
        }


        // Establecer un listener para el spinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Obtener el ítem seleccionado
                val selectedItem = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción a realizar cuando no se selecciona nada
            }
        }
    }

}