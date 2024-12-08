package com.example.habitproproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HabitosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitos)

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}