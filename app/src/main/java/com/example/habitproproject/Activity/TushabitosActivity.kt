package com.example.habitproproject.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Adapter.HabitosAdapter
import com.example.habitproproject.Adapter.TusHabitosAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TushabitosActivity : AppCompatActivity() {

    private lateinit var habitosAdapterMañana: TusHabitosAdapter
    private lateinit var habitosAdapterTarde: TusHabitosAdapter
    private lateinit var habitosAdapterNoche: TusHabitosAdapter
    private lateinit var listaHabitosMañana: List<Habitos>
    private lateinit var listaHabitosTarde: List<Habitos>
    private lateinit var listaHabitosNoche: List<Habitos>

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tushabitos)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        bottomNavigationView.selectedItemId = R.id.habits

        listaHabitosMañana = listOf(
            Habitos(null,"Salir a correr", "30 min", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_running),
            Habitos(null,"Beber agua", "3/8 vasos", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_water)

            )
        listaHabitosTarde = listOf(
            Habitos(null,"Estudiar", "2 horas", 50, 120, "2023-12-01", "2024-01-01", false, R.drawable.ic_study),
            Habitos(null,"Programar", "3 horas", 20, 180, "2023-12-05", "2024-01-15", false, R.drawable.ic_coding)
        )

        listaHabitosNoche = listOf(
            Habitos(null,"Leer", "10 páginas", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_read)
        )

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