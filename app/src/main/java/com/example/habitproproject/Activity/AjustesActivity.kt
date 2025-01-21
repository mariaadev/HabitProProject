package com.example.habitproproject.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.R

class AjustesActivity : AppCompatActivity() {
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

        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerIdiomas)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdiomas.adapter = adapter

        val posicionEspañol = adapter.getPosition("Español")
        spinnerIdiomas.setSelection(posicionEspañol)

        spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val idiomaSeleccionado = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Idioma: $idiomaSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }
}