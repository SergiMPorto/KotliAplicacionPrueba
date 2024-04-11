package com.sergi.RickAndMortyActivity.ui.theme

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sergi.ricandmorty.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperHeroListBinding
    private lateinit var retrofit: Retrofit
    // private val adapter = SuperHeroAdapter() // Asumiendo que tienes un adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return true // Indicar que el evento fue manejado correctamente
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Configurar RecyclerView
        //binding.recyclerView.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.create(ApiService::class.java).searchCharacterByName(query)
            if (response.isSuccessful) {
                val characters = response.body()?.superheroes
                characters?.let {
                    runOnUiThread {
                        // Actualiza el RecyclerView con los personajes obtenidos
                        // adapter.updateList(characters)
                    }
                }
            } else {
                runOnUiThread {
                    // Muestra un mensaje de error al usuario
                    // Por ejemplo: Snackbar.make(binding.root, "Error en la búsqueda", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


//("Retrofit es una biblioteca de cliente HTTP para Android y Java, desarrollada por Square," +
//  " que simplifica el proceso de realizar solicitudes HTTP en tu aplicación. " +
// "Retrofit convierte una API REST en una interfaz Java/Kotlin.")
