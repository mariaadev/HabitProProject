package com.example.habitproproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HabitosActivity : AppCompatActivity() {
    private lateinit var habitosAdapter: HabitosAdapter
    private lateinit var listaHabitos: List<Habitos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitos)

        /*RecyclerView Dias*/
        val recyclerDias = findViewById<RecyclerView>(R.id.recyclerDias)
        recyclerDias.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listaDias: MutableList<Dia> = ArrayList()
        listaDias.add(Dia("Lu", 14))
        listaDias.add(Dia("Ma", 15))
        listaDias.add(Dia("Mi", 16))
        listaDias.add(Dia("Ju", 17))
        listaDias.add(Dia("Vi", 18))
        listaDias.add(Dia("Sá", 19))
        listaDias.add(Dia("Do", 20))

        val adapter = DiasAdapter(listaDias)
        recyclerDias.adapter = adapter

        /*RecyclerView Hábitos*/
        listaHabitos = listOf(
            Habitos("Salir a correr", "30 min", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_running),
            Habitos("Beber agua", "3/8 vasos", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_water),
            Habitos("Estudiar", "2 horas", 50, 120, "2023-12-01", "2024-01-01", false, R.drawable.ic_study),
            Habitos("Programar", "3 horas", 20, 180, "2023-12-05", "2024-01-15", false, R.drawable.ic_coding),

        )

        habitosAdapter = HabitosAdapter(listaHabitos)

        val recyclerHabitos = findViewById<RecyclerView>(R.id.recyclerHabitos)
        recyclerHabitos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitos.adapter = habitosAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}