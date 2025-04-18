package com.example.habitproproject.Activity

import TusHabitosAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Activity.EstadisticasActivity.Companion.incrementActivityAccess
import com.example.habitproproject.Adapter.HabitosAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class TusHabitosActivity : AppCompatActivity() {

    private lateinit var habitosAdapterMañana: TusHabitosAdapter
    private lateinit var habitosAdapterTarde: TusHabitosAdapter
    private lateinit var habitosAdapterNoche: TusHabitosAdapter
    private lateinit var listaHabitosMañana: MutableList<Habitos>
    private lateinit var listaHabitosTarde: MutableList<Habitos>
    private lateinit var listaHabitosNoche: MutableList<Habitos>

    private lateinit var originalHabitosMañana: List<Habitos>
    private lateinit var originalHabitosTarde: List<Habitos>
    private lateinit var originalHabitosNoche: List<Habitos>
    private lateinit var searchView: SearchView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private val sharedPreferences by lazy {
        getSharedPreferences("HABITOS_PREF", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tushabitos)
        incrementActivityAccess(this, "habits")
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        establecerBottomNavigationView()

        bottomNavigationView.selectedItemId = R.id.habits

        listaHabitosMañana = mutableListOf()
        listaHabitosTarde = mutableListOf()
        listaHabitosNoche = mutableListOf()

        carregarHabitos()

        originalHabitosMañana = listaHabitosMañana.toList()
        originalHabitosTarde = listaHabitosTarde.toList()
        originalHabitosNoche = listaHabitosNoche.toList()

        habitosAdapterMañana = TusHabitosAdapter(listaHabitosMañana) { habitoSeleccionado ->
            abrirDetallesHabito(habitoSeleccionado)
        }

        habitosAdapterTarde = TusHabitosAdapter(listaHabitosTarde) { habitoSeleccionado ->
            abrirDetallesHabito(habitoSeleccionado)
        }

        habitosAdapterNoche = TusHabitosAdapter(listaHabitosNoche) { habitoSeleccionado ->
            abrirDetallesHabito(habitoSeleccionado)
        }




        val recyclerHabitosMañana = findViewById<RecyclerView>(R.id.recyclerHabitosMañana)
        recyclerHabitosMañana.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosMañana.adapter = habitosAdapterMañana

        val recyclerHabitosTarde = findViewById<RecyclerView>(R.id.recyclerHabitosTarde)
        recyclerHabitosTarde.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosTarde.adapter = habitosAdapterTarde

        val recyclerHabitosNoche = findViewById<RecyclerView>(R.id.recyclerHabitosNoche)
        recyclerHabitosNoche.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerHabitosNoche.adapter = habitosAdapterNoche


        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterHabitos(newText)
                return true
            }
        })

        val buttonAdd = findViewById<MaterialButton>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CrearHabito::class.java)
            startActivity(intent)
        }

        carregarHabitos();

        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_menu_habits);
        toolbar.setTitle("");

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout2)
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
    }

    private fun filterHabitos(query: String?) {
        val queryLower = query?.lowercase(Locale.getDefault()) ?: ""

        if (queryLower.isEmpty()) {
            habitosAdapterMañana.actualizarLista(originalHabitosMañana)
            habitosAdapterTarde.actualizarLista(originalHabitosTarde)
            habitosAdapterNoche.actualizarLista(originalHabitosNoche)
        } else {
            // Filtrar los hábitos para cada lista (Mañana, Tarde, Noche)
            val filteredMañana = originalHabitosMañana.filter {
                it.nombre.lowercase(Locale.getDefault()).contains(queryLower)
            }
            val filteredTarde = originalHabitosTarde.filter {
                it.nombre.lowercase(Locale.getDefault()).contains(queryLower)
            }
            val filteredNoche = originalHabitosNoche.filter {
                it.nombre.lowercase(Locale.getDefault()).contains(queryLower)
            }

            // Actualizar los adaptadores con los datos filtrados
            habitosAdapterMañana.actualizarLista(filteredMañana)
            habitosAdapterTarde.actualizarLista(filteredTarde)
            habitosAdapterNoche.actualizarLista(filteredNoche)
        }
    }





    private fun abrirDetallesHabito(habito: Habitos) {
        val intent = Intent(this, DetalleHabitoActivity::class.java).apply {
            putExtra("HABITO", habito)
        }
        startActivity(intent)
    }

    private fun carregarHabitos(){
        CoroutineScope(Dispatchers.Main).launch{
            try{
                val habitosObtenidos = withContext(Dispatchers.IO){
                    val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
                    apiService.getHabitos()
                }

                if(habitosObtenidos != null){
                    listaHabitosMañana.clear()
                    listaHabitosTarde.clear()
                    listaHabitosNoche.clear()
                    habitosObtenidos.forEach { habito ->
                        when (habito.id?.let { obtenerMomentoDia(it) }) {
                            "Mañana" -> listaHabitosMañana.add(habito)
                            "Tarde" -> listaHabitosTarde.add(habito)
                            "Noche" -> listaHabitosNoche.add(habito)
                        }
                    }
                    originalHabitosMañana = listaHabitosMañana.toList()
                    originalHabitosTarde = listaHabitosTarde.toList()
                    originalHabitosNoche = listaHabitosNoche.toList()

                    actualizarRecyclerViews()

                } else {
                    Toast.makeText(this@TusHabitosActivity, "Error al obtener los hábitos", Toast.LENGTH_SHORT).show()
                }

            }catch (e: Exception) {
                // Manejo de errores
                Toast.makeText(this@TusHabitosActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val idHabitoEliminado = data?.getIntExtra("ID_HABITO_ELIMINADO", -1)
            if (idHabitoEliminado != null && idHabitoEliminado != -1) {
                eliminarHabitoDeLista(idHabitoEliminado)
            }
        }
    }

    private fun eliminarHabitoDeLista(idHabito: Int) {
        listaHabitosMañana.removeAll { it.id == idHabito }
        listaHabitosTarde.removeAll { it.id == idHabito }
        listaHabitosNoche.removeAll { it.id == idHabito }

        actualizarRecyclerViews()
    }


    private fun obtenerMomentoDia(idHabito: Int): String {
        return sharedPreferences.getString("$idHabito.MOMENTO_DIA", "Cualquiera") ?: "Cualquiera"
    }

    private fun actualizarRecyclerViews() {
        habitosAdapterMañana.notifyDataSetChanged()
        habitosAdapterTarde.notifyDataSetChanged()
        habitosAdapterNoche.notifyDataSetChanged()
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
                    true
                }
                R.id.stats -> {
                    /*De momento, placeholder hasta crear las demás actividades*/
                    if (EstadisticasActivity::class.java.isAssignableFrom(currentActivity).not()) {
                        startActivity(Intent(this, EstadisticasActivity::class.java))
                        finish()
                    }
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
}