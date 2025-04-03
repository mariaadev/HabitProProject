package com.example.habitproproject.Activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitproproject.R
import com.github.mikephil.charting.charts.BarChart
import com.google.android.material.navigation.NavigationView

class EstadisticasHabitoActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estadisticas_habito)

        barChart = findViewById(R.id.barchart)
        btnSave = findViewById(R.id.btnSave)
        btnReset = findViewById(R.id.btnReset)

        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_menu_habits);
        toolbar.setTitle("Estadísticas Habito");

        toolbar.setNavigationIcon(R.drawable.ic_menu_habits)



    }
}