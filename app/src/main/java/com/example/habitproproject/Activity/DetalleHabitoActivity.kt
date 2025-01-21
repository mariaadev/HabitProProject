package com.example.habitproproject.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitproproject.R

class DetalleHabitoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_habito)

        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setTitle("Hábitos");

        toolbar.setNavigationOnClickListener {
            finish();
        }

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("duracion") ?: "Sin descripción"
        val progreso = intent.getIntExtra("progreso", 0)
        val completado = intent.getBooleanExtra("completado", false)
        val fechaInicio = intent.getStringExtra("fechaInicio") ?: "Fecha no disponible"
        val fechaFin = intent.getStringExtra("fechaFin") ?: "Fecha no disponible"
        val imagenId = intent.getIntExtra("imagenId", 0)
        val tiempoEnMinutos = intent.getIntExtra("tiempoEnMinutos", 0)

        findViewById<TextView>(R.id.textNombre).text = nombre
        findViewById<TextView>(R.id.textDescripcion).text = descripcion
        findViewById<TextView>(R.id.textProgreso).text = "Progreso: $progreso%"
        findViewById<TextView>(R.id.textCompletado).text =
            if (completado) "Estado: Completado" else "Estado: En progreso"
        findViewById<TextView>(R.id.textFechas).text = "Desde: $fechaInicio Hasta: $fechaFin"
        findViewById<TextView>(R.id.textTiempo).text = "Tiempo estimado: $tiempoEnMinutos min"
        findViewById<ImageView>(R.id.imagenHabito).setImageResource(imagenId)
    }
}