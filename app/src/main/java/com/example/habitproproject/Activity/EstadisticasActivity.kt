package com.example.habitproproject.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.habitproproject.R
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class EstadisticasActivity : AppCompatActivity() {
    private lateinit var pieChart: PieChart
    private lateinit var barChart: HorizontalBarChart
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button
    private var prefs: SharedPreferences? = null


   private lateinit var navigationView: NavigationView
   private lateinit var drawerLayout: DrawerLayout
   private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_estadisticas)
        incrementActivityAccess(this, "stats")

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        bottomNavigationView.selectedItemId = R.id.stats

        pieChart = findViewById(R.id.pieChart)
        barChart = findViewById(R.id.barChart)
        btnSave = findViewById(R.id.btnSave)
        btnReset = findViewById(R.id.btnReset)

        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_menu_habits);
        toolbar.setTitle("Estadísticas");

        drawerLayout = findViewById(R.id.drawerlayout)
        navigationView = findViewById(R.id.navigationView)

        toolbar.setNavigationIcon(R.drawable.ic_menu_habits)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        /*Configurar SharedPreferences*/
        prefs = getSharedPreferences("HabitStats", MODE_PRIVATE)

        /*Configurar PieChart*/
        setupPieChart()
        loadPieChartData()

        /*Configurar BarChart horizontal*/
        setupBarChart()
        loadBarChartData()

        btnSave.setOnClickListener(View.OnClickListener { view: View? -> saveData() })


        btnReset.setOnClickListener {
            resetData()
            resetDataActivities()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
           when (menuItem.itemId) {
                R.id.menu_settings -> {
                   val intent = Intent(this, AjustesActivity::class.java)
                   startActivity(intent)
                }
                R.id.menu_theme -> {
                    Toast.makeText(this, "Tema seleccionado", Toast.LENGTH_SHORT).show()
                }
                R.id.menu_log_out -> {
                    val intent = Intent(this, IniciarSesionActivity::class.java)
                startActivity(intent)
                }
                R.id.menu_help -> {
                    Toast.makeText(this, "Help seleccionado", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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
                    if (TusHabitosActivity::class.java.isAssignableFrom(currentActivity).not()) {
                       startActivity(Intent(this, TusHabitosActivity::class.java))
                        finish()
                    }
                    true
               }
                R.id.stats -> {

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


    private fun setupPieChart() {
        pieChart!!.description.isEnabled = false
        pieChart!!.holeRadius = 45f
        pieChart!!.transparentCircleRadius = 50f

        pieChart!!.setCenterTextColor(Color.BLACK)
        pieChart!!.centerText = "Estadísticas del CRUD de Hábitos"
        pieChart!!.setCenterTextSize(14f)

        val l = pieChart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
    }

    private fun loadPieChartData() {
        /*Obtener datos de sharedPrefs*/
        val createCount = prefs!!.getInt("create", 0)
        val updateCount = prefs!!.getInt("update", 0)
        val deleteCount = prefs!!.getInt("delete", 0)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(createCount.toFloat(), "Crear"))
        entries.add(PieEntry(updateCount.toFloat(), "Actualizar"))
        entries.add(PieEntry(deleteCount.toFloat(), "Eliminar"))

        val dataSet = PieDataSet(entries, "Acciones del Usuario")
        val pastelGreen = Color.parseColor("#77DD77")
        val pastelBlue = Color.parseColor("#A2C2E0")
        val pastelRed = Color.parseColor("#F6A6C1")

        dataSet.setColors(pastelGreen, pastelBlue, pastelRed)
        dataSet.valueTextSize = 12f


        val pieData = PieData(dataSet)
        pieChart!!.data = pieData
        pieChart!!.invalidate()
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

        val leftAxis = barChart.axisLeft
        leftAxis.setDrawGridLines(false)

        val rightAxis = barChart.axisRight
        rightAxis.isEnabled = false
    }

    private fun loadBarChartData() {
        val homeCount = prefs!!.getInt("home", 0)
        val habitosCount = prefs!!.getInt("habits", 0)
        val statsCount = prefs!!.getInt("stats", 0)
        val profileCount = prefs!!.getInt("profile", 0)
        val settingsCount = prefs!!.getInt("settings", 0)

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, homeCount.toFloat()))
        entries.add(BarEntry(1f, habitosCount.toFloat()))
        entries.add(BarEntry(2f, statsCount.toFloat()))
        entries.add(BarEntry(3f, profileCount.toFloat()))
        entries.add(BarEntry(4f, settingsCount.toFloat()))

        val dataSet = BarDataSet(entries, "Accesos a Actividades")
        dataSet.setColors(
            Color.parseColor("#A2C2E0"),
            Color.parseColor("#77DD77"),
            Color.parseColor("#F6A6C1"),
            Color.parseColor("#CBAACB"),
            Color.parseColor("#FFFACD")
        )

        dataSet.valueTextSize = 12f

        /*Mostrar números enteros*/
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        val barData = BarData(dataSet)
        barData.barWidth = 0.6f
        barChart.data = barData

        val labels = listOf("Home", "Tus Hábitos", "Estadísticas", "Perfil", "Ajustes")
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.textSize = 12f
        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChart.axisLeft.setDrawGridLines(false)
        /*Evitar valores negativos*/
        barChart.axisLeft.axisMinimum = 0f

        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false

        barChart.invalidate()
    }

    private fun saveData() {
        val editor = prefs!!.edit()
        editor.putInt("create", prefs!!.getInt("create", 0))
        editor.putInt("update", prefs!!.getInt("update", 0))
        editor.putInt("delete", prefs!!.getInt("delete", 0))
        editor.apply()
    }


    override fun onResume() {
        super.onResume()
        /*recargar gráfico para que actualice el recuento cuando se accede a ajustes*/
        loadBarChartData()
    }

    private fun resetData() {

        val editor = prefs!!.edit()
        editor.putInt("create", 0)
        editor.putInt("update", 0)
        editor.putInt("delete", 0)
        editor.apply()

        /*Recargar datos en el gráfico*/
        loadPieChartData()
    }

    private fun resetDataActivities() {
        val editor = prefs!!.edit()
        editor.putInt("home", 0)
        editor.putInt("habits", 0)
        editor.putInt("stats", 0)
        editor.putInt("profile", 0)
        editor.putInt("settings", 0)
        editor.apply()

        loadBarChartData()
    }

    companion object {
        fun incrementAction(context: Context, action: String?) {
            val prefs = context.getSharedPreferences("HabitStats", MODE_PRIVATE)
            val count = prefs.getInt(action, 0)
            prefs.edit().putInt(action, count + 1).apply()
        }

        fun incrementActivityAccess(context: Context, activityName: String) {
            val prefs = context.getSharedPreferences("HabitStats", MODE_PRIVATE)
            val count = prefs.getInt(activityName, 0)
            prefs.edit().putInt(activityName, count + 1).apply()
        }
    }
}

