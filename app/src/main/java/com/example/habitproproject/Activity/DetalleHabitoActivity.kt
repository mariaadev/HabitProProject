package com.example.habitproproject.Activity

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class DetalleHabitoActivity : AppCompatActivity() {
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationViewHab: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_habito)

        bottomNavigationViewHab = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("duracion") ?: "Sin descripción"
        val progreso = intent.getIntExtra("progreso", 0)
        val completado = intent.getBooleanExtra("completado", false)
        val fechaInicio = intent.getStringExtra("fechaInicio") ?: "Sin fecha de inicio"
        val fechaFin = intent.getStringExtra("fechaFin") ?: "Sin fecha de fin"
        val imagenId = intent.getIntExtra("imagenId", R.drawable.ic_study)
        val tiempoEnMinutos = intent.getIntExtra("tiempoEnMinutos", 0)


        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setTitle(nombre);
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val progressText: TextView = findViewById(R.id.progressText)
        val progressIcon1: ImageView = findViewById(R.id.imagenHabitoProgressBar)
        val progressIcon2: ImageView = findViewById(R.id.imagenMedallaHabito)
        // Configura el progreso
        progressBar.progress = progreso
        progressText.text = "$progreso%"
        progressIcon1.setImageResource(imagenId)
        progressIcon2.setImageResource(R.drawable.medalla)

        findViewById<TextView>(R.id.textNombre).text = nombre
        findViewById<TextView>(R.id.textDescripcion).text = descripcion
        findViewById<TextView>(R.id.textProgreso).text = "Progreso: $progreso%"
        findViewById<TextView>(R.id.textCompletado).text =
            if (completado) "Estado: Completado" else "Estado: En progreso"
        findViewById<TextView>(R.id.textFechas).text = "Desde: $fechaInicio Hasta: $fechaFin"
        findViewById<TextView>(R.id.textTiempo).text = "Tiempo estimado: $tiempoEnMinutos min"
        findViewById<ImageView>(R.id.imagenHabito).setImageResource(imagenId)
    }

    private fun establecerBottomNavigationView() {
        bottomNavigationViewHab.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.habits -> {
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.stats -> {
                    /*De momento, placeholder hasta crear las demás actividades*/
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }



}