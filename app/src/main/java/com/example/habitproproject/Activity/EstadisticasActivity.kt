package com.example.habitproproject.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.habitproproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class EstadisticasActivity : AppCompatActivity() {


//    private lateinit var navigationView: NavigationView
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estadisticas)

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
}