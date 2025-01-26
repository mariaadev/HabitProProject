package com.example.habitproproject.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.R
import java.util.Locale

class AjustesActivity : AppCompatActivity() {

    private var currentLanguage = "es"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajustes)

        /*Configuración toolbar*/
        val toolbar: androidx.appcompat.widget.Toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setTitle("Ajustes");

        toolbar.setNavigationOnClickListener {
            finish();
        }

        val preferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val savedLanguage = preferences.getString("AppLanguage", "ca") // Català per defecte
        setAppLocale(savedLanguage)

        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerIdiomas)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdiomas.adapter = adapter

        if (savedLanguage == "es") {
            spinnerIdiomas.setSelection(0) // Catalán seleccionado
        } else if (savedLanguage == "ca") {
            spinnerIdiomas.setSelection(1) // Inglés seleccionado
        }else if (savedLanguage == "en") {
            spinnerIdiomas.setSelection(2) // Inglés seleccionado
        } else {
            spinnerIdiomas.setSelection(3)
        }

        spinnerIdiomas.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val languageCode = when (position) {
                    0 -> "es" // Español
                    1 -> "ca" // Catalán
                    2 -> "en" // Inglés
                    3 -> "fr" // Francés
                    else -> "es" // Idioma por defecto
                }
                setAppLocale(languageCode)
                currentLanguage = languageCode

                val editor = getSharedPreferences("AppSettings", MODE_PRIVATE).edit()
                editor.putString("AppLanguage", languageCode)
                editor.apply()

                //updateUI()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })

    }
    private fun updateUI() {

    }

    private fun setAppLocale(savedLanguage: String?) {
        val locale = Locale(savedLanguage)
        Locale.setDefault(locale)
        val resources = resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}