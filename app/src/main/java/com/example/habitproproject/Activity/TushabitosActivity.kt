package com.example.habitproproject.Activity

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

class TushabitosActivity : AppCompatActivity() {

    private lateinit var habitosAdapterMañana: TusHabitosAdapter
    private lateinit var habitosAdapterTarde: TusHabitosAdapter
    private lateinit var habitosAdapterNoche: TusHabitosAdapter
    private lateinit var listaHabitosMañana: List<Habitos>
    private lateinit var listaHabitosTarde: List<Habitos>
    private lateinit var listaHabitosNoche: List<Habitos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tushabitos)


        listaHabitosMañana = listOf(
            Habitos("Salir a correr", "30 min", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_running),
            Habitos("Beber agua", "3/8 vasos", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_water)

            )
        listaHabitosTarde = listOf(
            Habitos("Estudiar", "2 horas", 50, 120, "2023-12-01", "2024-01-01", false, R.drawable.ic_study),
            Habitos("Programar", "3 horas", 20, 180, "2023-12-05", "2024-01-15", false, R.drawable.ic_coding)
        )

        listaHabitosNoche = listOf(
            Habitos("Leer", "10 páginas", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_read)
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
}