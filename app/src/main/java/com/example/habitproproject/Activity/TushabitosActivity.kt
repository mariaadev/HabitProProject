package com.example.habitproproject.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Adapter.HabitosAdapter
import com.example.habitproproject.Adapter.TusHabitosAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Response

class TushabitosActivity : AppCompatActivity() {

    private lateinit var habitosAdapterMañana: TusHabitosAdapter
    private lateinit var habitosAdapterTarde: TusHabitosAdapter
    private lateinit var habitosAdapterNoche: TusHabitosAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tushabitos)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        habitosAdapterMañana = TusHabitosAdapter(emptyList())
        habitosAdapterTarde = TusHabitosAdapter(emptyList())
        habitosAdapterNoche = TusHabitosAdapter(emptyList())


        val recyclerHabitosMañana = findViewById<RecyclerView>(R.id.recyclerHabitosMañana)
        recyclerHabitosMañana.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosMañana.adapter = habitosAdapterMañana

        val recyclerHabitosTarde = findViewById<RecyclerView>(R.id.recyclerHabitosTarde)
        recyclerHabitosTarde.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosTarde.adapter = habitosAdapterTarde

        val recyclerHabitosNoche = findViewById<RecyclerView>(R.id.recyclerHabitosNoche)
        recyclerHabitosNoche.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosNoche.adapter = habitosAdapterNoche

        carregarHabitos();

    }

    private fun carregarHabitos(){
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.getHabitos()

        call.enqueue(object : retrofit2.Callback<List<Habitos>> {
            override fun onResponse(call: Call<List<Habitos>>, response: Response<List<Habitos>>) {
                if (response.isSuccessful) {
                    val habitosList: List<Habitos> = response.body() ?: emptyList()

                    val listaHabitosMañana = mutableListOf<Habitos>()
                    val listaHabitosTarde = mutableListOf<Habitos>()
                    val listaHabitosNoche = mutableListOf<Habitos>()

                    for ((index, habito) in habitosList.withIndex()) {
                        when (index % 3) {
                            0 -> listaHabitosMañana.add(habito)
                            1 -> listaHabitosTarde.add(habito)
                            2 -> listaHabitosNoche.add(habito)
                        }
                    }

                    // Actualitzar els adaptadors amb les llistes filtrades
                    habitosAdapterMañana.actualizarLista(listaHabitosMañana)
                    habitosAdapterTarde.actualizarLista(listaHabitosTarde)
                    habitosAdapterNoche.actualizarLista(listaHabitosNoche)
                } else {
                    Toast.makeText(this@TushabitosActivity, "Error al cargar los hábitos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Habitos>>, t: Throwable) {
                Toast.makeText(this@TushabitosActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun establecerBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.habits -> {
                    val intent = Intent(this, TushabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.stats -> {
                    /*De momento, placeholder hasta crear las demás actividades*/
                    val intent = Intent(this, EstadisticasActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}