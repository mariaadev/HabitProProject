package com.example.habitproproject.Activity

import TusHabitosAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TusHabitosActivity : AppCompatActivity() {

    private lateinit var habitosAdapterMañana: TusHabitosAdapter
    private lateinit var habitosAdapterTarde: TusHabitosAdapter
    private lateinit var habitosAdapterNoche: TusHabitosAdapter
    private lateinit var listaHabitosMañana: MutableList<Habitos>
    private lateinit var listaHabitosTarde: MutableList<Habitos>
    private lateinit var listaHabitosNoche: MutableList<Habitos>

    private lateinit var bottomNavigationView: BottomNavigationView
    private val sharedPreferences by lazy {
        getSharedPreferences("HABITOS_PREF", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tushabitos)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        bottomNavigationView.selectedItemId = R.id.habits


        listaHabitosMañana = mutableListOf()
        listaHabitosTarde = mutableListOf()
        listaHabitosNoche = mutableListOf()

        habitosAdapterMañana = TusHabitosAdapter(listaHabitosMañana)
        habitosAdapterTarde = TusHabitosAdapter(listaHabitosTarde)
        habitosAdapterNoche = TusHabitosAdapter(listaHabitosNoche)



        val recyclerHabitosMañana = findViewById<RecyclerView>(R.id.recyclerHabitosMañana)
        recyclerHabitosMañana.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosMañana.adapter = habitosAdapterMañana

        val recyclerHabitosTarde = findViewById<RecyclerView>(R.id.recyclerHabitosTarde)
        recyclerHabitosTarde.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosTarde.adapter = habitosAdapterTarde

        val recyclerHabitosNoche = findViewById<RecyclerView>(R.id.recyclerHabitosNoche)
        recyclerHabitosNoche.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosNoche.adapter = habitosAdapterNoche

        val buttonAdd = findViewById<MaterialButton>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CrearHabito::class.java)
            startActivity(intent)
        }

        carregarHabitos();

    }

    private fun carregarHabitos(){
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
        val call = apiService.getHabitos()

        call.enqueue(object : retrofit2.Callback<List<Habitos>> {
            override fun onResponse(call: Call<List<Habitos>>, response: Response<List<Habitos>>) {
                if (response.isSuccessful) {
                    response.body()?.let { listaHabitos ->
                        listaHabitosMañana.clear()
                        listaHabitosTarde.clear()
                        listaHabitosNoche.clear()

                        listaHabitos.forEach { habito ->
                            when (habito.id?.let { obtenerMomentoDia(it) }) {
                                "Mañana" -> listaHabitosMañana.add(habito)
                                "Tarde" -> listaHabitosTarde.add(habito)
                                "Noche" -> listaHabitosNoche.add(habito)
                            }
                        }

                        actualizarRecyclerViews()
                    }
                } else {
                    Toast.makeText(this@TusHabitosActivity, "Error al cargar los hábitos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Habitos>>, t: Throwable) {
                Toast.makeText(this@TusHabitosActivity, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerMomentoDia(idHabito: Int): String {
        return sharedPreferences.getString("$idHabito.MOMENTO_DIA", "Cualquiera") ?: "Cualquiera"
    }

    private fun actualizarRecyclerViews() {
        habitosAdapterMañana.notifyDataSetChanged()
        habitosAdapterTarde.notifyDataSetChanged()
        habitosAdapterNoche.notifyDataSetChanged()
    }

    private fun establecerBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val currentActivity = this::class.java
            when (menuItem.itemId) {
                R.id.home -> {
                    if (HabitosActivity::class.java.isAssignableFrom(currentActivity).not()) {
                        startActivity(Intent(this, HabitosActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.habits -> {
                    true
                }
                R.id.stats -> {
                    /*De momento, placeholder hasta crear las demás actividades*/
                    if (EstadisticasActivity::class.java.isAssignableFrom(currentActivity).not()) {
                        startActivity(Intent(this, EstadisticasActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.profile -> {
                    if (ProfileActivity::class.java.isAssignableFrom(currentActivity).not()) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}