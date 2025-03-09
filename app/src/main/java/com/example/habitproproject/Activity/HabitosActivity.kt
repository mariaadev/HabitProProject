package com.example.habitproproject.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Model.Dia
import com.example.habitproproject.Adapter.DiasAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.Adapter.HabitosAdapter
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import retrofit2.Response


class HabitosActivity : AppCompatActivity() {
    private lateinit var habitosAdapter: HabitosAdapter
    private  var listaHabitos: List<Habitos> = emptyList()
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

        bottomNavigationView.selectedItemId = R.id.home
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



        habitosAdapter = HabitosAdapter(listaHabitos)

        val recyclerHabitos = findViewById<RecyclerView>(R.id.recyclerHabitos)
        recyclerHabitos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitos.adapter = habitosAdapter

        val buttonAdd = findViewById<ImageView>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CrearHabito::class.java)
            startActivity(intent)
        }

        obtenerHabitos()
    }

    private fun updateUI() {
        val titulo: TextView = findViewById(R.id.textHoy)
        val dia: TextView = findViewById(R.id.textNombreDia)
        titulo.text = getString(R.string.hoy)
        dia.text = getString(R.string.jueves)

    }

    private fun obtenerHabitos() {
        val apiService = RetrofitClient.getInstance().create(ApiService::class.java)

        apiService.getHabitos().enqueue(object : Callback<List<Habitos>> {
            override fun onResponse(call: Call<List<Habitos>>, response: Response<List<Habitos>>) {
                if (response.isSuccessful) {
                    val habitosObtenidos = response.body()
                    Log.d("HabitosActivity", "Respuesta de la API: $habitosObtenidos")
                    listaHabitos = response.body() ?: emptyList()
                    habitosAdapter = HabitosAdapter(listaHabitos)
                    habitosAdapter.actualizarListaHabitos(listaHabitos)
                    val recyclerHabitos = findViewById<RecyclerView>(R.id.recyclerHabitos)
                    recyclerHabitos.adapter = habitosAdapter
                } else {
                    Toast.makeText(this@HabitosActivity, "Error al obtener los hábitos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Habitos>>, t: Throwable) {
                Toast.makeText(this@HabitosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun establecerBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
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

