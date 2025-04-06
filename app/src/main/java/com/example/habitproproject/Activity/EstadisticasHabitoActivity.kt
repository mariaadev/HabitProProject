package com.example.habitproproject.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.habitproproject.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import com.github.mikephil.charting.formatter.ValueFormatter

class EstadisticasHabitoActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estadisticas_habito)

        val habitId = intent.getIntExtra("habitId", -1)
        Log.d("EstadisticasHabito", "habitId recibido: $habitId")

        barChart = findViewById(R.id.barchart)
        btnSave = findViewById(R.id.btnSave)
        btnReset = findViewById(R.id.btnReset)
        prefs = getSharedPreferences("HabitCalendarPrefs", MODE_PRIVATE)

        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setTitle("Estadísticas Habito");
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }



        setupBarChart()
        loadHabitData(habitId)

        btnSave.setOnClickListener {
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
        }

        btnReset.setOnClickListener {
            resetHabitData(habitId)
            loadHabitData(habitId)

            val intent = Intent("com.tuapp.RESET_HABITO")
            intent.putExtra("habitId", habitId)
            sendBroadcast(intent)
        }

    }


    private fun setupBarChart() {
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)

        val legend = barChart.legend
        legend.isEnabled = false

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.textSize = 12f

        val yAxis = barChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 7f
        yAxis.granularity = 1f
        yAxis.setLabelCount(8, true) // Para mostrar del 0 al 7
        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString() // Sin decimales
            }
        }

        barChart.axisRight.isEnabled = false
    }


    private fun loadHabitData(habitId: Int) {
        val entries = ArrayList<BarEntry>()
        val weekLabels = ArrayList<String>()

        for (week in 1..5) {
            val key = "habit_${habitId}_week_$week"
            val daysCompleted = prefs?.getInt(key, 0)

            if (daysCompleted != null) {
                entries.add(BarEntry((week - 1).toFloat(), daysCompleted.toFloat()))
            }
            weekLabels.add("Semana $week")
        }

        val dataSet = BarDataSet(entries, "Días cumplidos")
        dataSet.setColors(Color.parseColor("#8C4A5F"))
        dataSet.valueTextSize = 14f

        val barData = BarData(dataSet)
        barData.barWidth = 0.6f

        barChart.data = barData
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(weekLabels)

        barChart.invalidate()
    }

    private fun resetHabitData(habitId: Int) {
        val editor = prefs?.edit()
        for (week in 1..5) {
            if (editor != null) {
                editor.putInt("habit_${habitId}_week_$week", 0)
            }
        }
        if (editor != null) {
            editor.apply()
        }
    }
}