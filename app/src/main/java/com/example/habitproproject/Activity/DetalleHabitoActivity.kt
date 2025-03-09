package com.example.habitproproject.Activity

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitproproject.Adapter.CalendarAdapter
import com.example.habitproproject.Model.Dia
import com.example.habitproproject.Model.DiaCalendario
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DetalleHabitoActivity : AppCompatActivity() {
    private lateinit var bottomNavigationViewHab: BottomNavigationView
    private val daysInMonth = 31
    private val calendarDays = mutableListOf<DiaCalendario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_habito)

        /*Aplicar un o altre depenent de la versió del sdk*/
        val habito: Habitos? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("habito", Habitos::class.java)
        } else {
            intent.getParcelableExtra("habito")
        }


        if (habito != null) {
        // Inicializar los días del mes
        for (i in 1..daysInMonth) {
            calendarDays.add(DiaCalendario(i, false))  // Todos los días comienzan sin marcar
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 7)  // 7 columnas por semana (Lunes a Domingo)

        val adapter = CalendarAdapter(calendarDays) { day ->
            // Este callback se llama cuando un día es clickeado
            // Aquí puedes guardar el estado del día (marcado/desmarcado)
            Log.d("MainActivity", "Día $day clickeado")
        }

        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        bottomNavigationViewHab = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        val nombre = habito.nombre
        val descripcion = habito.descripcion
        val progreso = habito.progreso
        val completado = habito.completado
        val fechaInicio = habito.fechaInicio
        val fechaFin = habito.fechaFin
        val imagenId = habito.imagenId
        val tiempoEnMinutos = habito.tiempoEnMinutos

        //Parsear las fechas de inicio y fin usando SimpleDateFormat
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDate: Date = format.parse(fechaInicio) ?: Date()
        val endDate: Date = format.parse(fechaFin) ?: Date()

        val currentDate = Date()

        /*Calcular la duración total del hábito en días*/
        val totalTime = (endDate.time - startDate.time) / (1000 * 60 * 60 * 24)

        /*Calcular los días cumplidos según el progreso*/
        val diasCompletados = (totalTime * (progreso / 100.0)).toInt()
        val daysText = "$diasCompletados días"

        findViewById<TextView>(R.id.textViewDays).text = "($daysText)"

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
            Glide.with(this)
                .load(imagenId)
                .into(progressIcon1)
        progressIcon2.setImageResource(R.drawable.medalla)

            Glide.with(this)
                .load(imagenId)
                .into(findViewById<ImageView>(R.id.imageViewDetalleHabito))
        findViewById<TextView>(R.id.textDescripcion).text = "Tiempo total: $descripcion"
        findViewById<TextView>(R.id.textProgreso).text = "Progreso: $progreso%"
        findViewById<TextView>(R.id.textCompletado).text =
            if (completado) "Estado: Completado" else "Estado: En progreso"
        findViewById<TextView>(R.id.textFechas).text = "Desde: $fechaInicio\nHasta: $fechaFin"
        findViewById<TextView>(R.id.textTiempo).text = "Tiempo estimado: $tiempoEnMinutos min"

        } else {
            Toast.makeText(this, "Error: No se pudo obtener el hábito", Toast.LENGTH_SHORT).show()
        }
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