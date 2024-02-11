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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PantallaMain : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaMainBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recyclerView2)
        Log.v("T","1")
        initRecyclerView()

        loadMovies()
        Log.v("T","2")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    private fun loadMovies() {
        RetrofitClient.tmDbApi.getListOfMovies().enqueue(object : Callback<MovieApiResponse> {
            override fun onResponse(call: Call<MovieApiResponse>, response: Response<MovieApiResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    movies?.let {
                        // Actualizar el dataset del adapter y refrescar el RecyclerView
                        viewAdapter.MovieList = it
                        viewAdapter.notifyDataSetChanged()
                    }
                } else {
                    // Manejar error
                    val errorBody = response.errorBody()?.string()
                    val statusCode = response.code()
                    Log.e("API Error", "Código de error: $statusCode, Cuerpo del error: $errorBody")
                }
            }

            override fun onFailure(call: Call<MovieApiResponse>, t: Throwable) {
                // Manejar fallo en la llamada a la API

            }
        })
    }



    private fun initRecyclerView(){
        recyclerView.layoutManager= LinearLayoutManager(this)
        val emptyList = listOf<MovieDataClass>()
        recyclerView.adapter = RecyclerAdapter(emptyList)

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