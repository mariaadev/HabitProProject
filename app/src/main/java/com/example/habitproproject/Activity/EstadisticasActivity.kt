package com.example.habitproproject.Activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.CoroutineScope


class EstadisticasActivity : AppCompatActivity() {
    private lateinit var pieChart: PieChart
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private var prefs: SharedPreferences? = null


//    private lateinit var navigationView: NavigationView
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_estadisticas)

        pieChart = findViewById(R.id.pieChart)
        btnSave = findViewById(R.id.btnSave)
        btnReset = findViewById(R.id.btnReset)

        // Configurar SharedPreferences
        prefs = getSharedPreferences("HabitStats", MODE_PRIVATE)

        // Configurar PieChart
        setupPieChart()
        loadPieChartData()

        // Botón Guardar
        btnSave.setOnClickListener(View.OnClickListener { view: View? -> saveData() })

        // Botón Resetear
        btnReset.setOnClickListener(View.OnClickListener { view: View? -> resetData() })
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menu_settings -> {
//                    val intent = Intent(this, AjustesActivity::class.java)
//                    startActivity(intent)
//                }
//                R.id.menu_theme -> {
//                    Toast.makeText(this, "Tema seleccionado", Toast.LENGTH_SHORT).show()
//                }
//                R.id.menu_log_out -> {
//                    val intent = Intent(this, IniciarSesionActivity::class.java)
//                    startActivity(intent)
//                }
//                R.id.menu_help -> {
//                    Toast.makeText(this, "Help seleccionado", Toast.LENGTH_SHORT).show()
//                }
//            }
//            drawerLayout.closeDrawer(GravityCompat.START)
//            true
//        }
    }

//    private fun establecerBottomNavigationView() {
//        bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            val currentActivity = this::class.java
//            when (menuItem.itemId) {
//                R.id.home -> {
//                    if (HabitosActivity::class.java.isAssignableFrom(currentActivity).not()) {
//                        startActivity(Intent(this, HabitosActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                R.id.habits -> {
//                    if (TusHabitosActivity::class.java.isAssignableFrom(currentActivity).not()) {
//                        startActivity(Intent(this, TusHabitosActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                R.id.stats -> {
//
//                    true
//                }
//                R.id.profile -> {
//                    if (ProfileActivity::class.java.isAssignableFrom(currentActivity).not()) {
//                        startActivity(Intent(this, ProfileActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
//    }


    private fun setupPieChart() {
        pieChart!!.description.isEnabled = false
        pieChart!!.holeRadius = 45f
        pieChart!!.transparentCircleRadius = 50f

        // Fuente personalizada
        pieChart!!.setCenterTextColor(Color.BLACK)
        pieChart!!.centerText = "Estadísticas del CRUD de Hábitos"
        pieChart!!.setCenterTextSize(14f)

        // Leyenda
        val l = pieChart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
    }

    private fun loadPieChartData() {
        // Obtener datos guardados en SharedPreferences
        val createCount = prefs!!.getInt("create", 0)
        val updateCount = prefs!!.getInt("update", 0)
        val deleteCount = prefs!!.getInt("delete", 0)

        // Lista de entradas para el gráfico
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(createCount.toFloat(), "Crear"))
        entries.add(PieEntry(updateCount.toFloat(), "Actualizar"))
        entries.add(PieEntry(deleteCount.toFloat(), "Eliminar"))

        // Configurar dataset
        val dataSet = PieDataSet(entries, "Acciones del Usuario")
        val pastelGreen = Color.parseColor("#77DD77")
        val pastelBlue = Color.parseColor("#A2C2E0")
        val pastelRed = Color.parseColor("#F6A6C1")

        dataSet.setColors(pastelGreen, pastelBlue, pastelRed)
        dataSet.valueTextSize = 12f

        // Configurar datos del gráfico
        val pieData = PieData(dataSet)
        pieChart!!.data = pieData
        pieChart!!.invalidate() // Refrescar gráfico
    }

    private fun saveData() {
        val editor = prefs!!.edit()
        editor.putInt("create", prefs!!.getInt("create", 0))
        editor.putInt("update", prefs!!.getInt("update", 0))
        editor.putInt("delete", prefs!!.getInt("delete", 0))
        editor.apply()
    }


    private fun resetData() {
        // Resetear valores a 0
        val editor = prefs!!.edit()
        editor.putInt("create", 0)
        editor.putInt("update", 0)
        editor.putInt("delete", 0)
        editor.apply()

        // Recargar datos en el gráfico
        loadPieChartData()
    }

    companion object {
        // Método estático para incrementar eventos desde otras partes de la app
        fun incrementAction(context: Context, action: String?) {
            val prefs = context.getSharedPreferences("HabitStats", MODE_PRIVATE)
            val count = prefs.getInt(action, 0)
            prefs.edit().putInt(action, count + 1).apply()
        }
    }
}

