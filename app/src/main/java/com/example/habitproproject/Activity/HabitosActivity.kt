package com.example.habitproproject.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.Model.Dia
import com.example.habitproproject.Adapter.DiasAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.Adapter.HabitosAdapter
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class HabitosActivity : AppCompatActivity() {
    private lateinit var habitosAdapter: HabitosAdapter
    private lateinit var listaHabitos: List<Habitos>
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitos)

        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_menu_habits);
        toolbar.setTitle("");

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        // Configurar el icono de navegación
        toolbar.setNavigationIcon(R.drawable.ic_menu_habits)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        // Manejar clics en los elementos del NavigationView
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

        /*Establecer barra de navegación */
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()


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
            Habitos(null,"Salir a correr", "30 min", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_running),
            Habitos(null,"Beber agua", "3/8 vasos", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_water),
            Habitos(null,"Estudiar", "2 horas", 50, 120, "2023-12-01", "2024-01-01", false, R.drawable.ic_study),
            Habitos(null,"Programar", "3 horas", 20, 180, "2023-12-05", "2024-01-15", false, R.drawable.ic_coding),
            Habitos(null,"Comer sano", "2 veces por semana", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_healthy),
            Habitos(null,"Leer", "10 páginas", 40, 30, "2023-12-02", "2024-02-01", false, R.drawable.ic_read),
            Habitos(null,"Desconectar", "3 horas", 20, 180, "2023-12-05", "2024-01-15", false, R.drawable.ic_mobile),
            Habitos(null,"Ir al gimnasio", "2 dias", 50, 120, "2023-12-01", "2024-01-01", false, R.drawable.ic_gym),

            )

        habitosAdapter = HabitosAdapter(listaHabitos)

        val recyclerHabitos = findViewById<RecyclerView>(R.id.recyclerHabitos)
        recyclerHabitos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitos.adapter = habitosAdapter

    }

    private fun updateUI() {
        val titulo: TextView = findViewById(R.id.textHoy)
        val dia: TextView = findViewById(R.id.textNombreDia)
        titulo.text = getString(R.string.hoy)
        dia.text = getString(R.string.jueves)

    }

    private fun establecerBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.habits -> {
                    val intent = Intent(this, TushabitosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.stats -> {
                    /*De momento, placeholder hasta crear las demás actividades*/
                    val intent = Intent(this, EstadisticasActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun abrirAjustesActivity() {
        val intent = Intent(this, AjustesActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}