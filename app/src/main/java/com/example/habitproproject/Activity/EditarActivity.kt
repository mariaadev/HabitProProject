package com.example.habitproproject.Activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Adapter.ImageAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditarActivity : AppCompatActivity() {

    private var habito: Habitos? = null

    private lateinit var etNombreHabito: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etObjetivo: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var ivSeleccionarImagen: ImageView
    private lateinit var rgMomentoDia: RadioGroup
    private lateinit var btnEditar: Button

    private var imagenSeleccionada: String = ""

    private val imagenesUrl = listOf(
        "http://13.216.192.241/images/imagen1.png",
        "http://13.216.192.241/images/imagen2.png",
        "http://13.216.192.241/images/imagen3.png",
        "http://13.216.192.241/images/imagen4.png",
        "http://13.216.192.241/images/imagen5.png",
        "http://13.216.192.241/images/imagen6.png",
        "http://13.216.192.241/images/imagen7.png",
        "http://13.216.192.241/images/imagen8.png"
    )
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val sharedPreferences by lazy {
        getSharedPreferences("HABITOS_PREF", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar)

        this.habito = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("habito", Habitos::class.java)
        } else {
            intent.getParcelableExtra("habito")
        }

        if(habito != null){

            val id = habito!!.id
            val nombre = habito!!.nombre
            val descripcion = habito!!.descripcion
            val fechaInicio = habito!!.fechaInicio
            val fechaFin = habito!!.fechaFin
            val imagen = habito!!.imagenId

            etNombreHabito = findViewById(R.id.etNombreHabito)
            etDescripcion = findViewById(R.id.etDescripcion)
            etObjetivo = findViewById(R.id.etObjetivo)
            etFechaInicio = findViewById(R.id.etFechaInicio)
            etFechaFin = findViewById(R.id.etFechaFin)
            ivSeleccionarImagen = findViewById(R.id.ivSeleccionarImagen)
            rgMomentoDia = findViewById(R.id.rgMomentoDia)
            btnEditar = findViewById(R.id.btnEditar)


            val datosHabitos = id?.let { recuperarDeSharedPreferences(id) }

            etNombreHabito.setText(nombre)
            etDescripcion.setText(descripcion)
            if (datosHabitos != null) {
                etObjetivo.setText(datosHabitos.first)

                when(datosHabitos.second){
                    "Mañana" -> rgMomentoDia.check(R.id.rbManana)
                    "Tarde" -> rgMomentoDia.check(R.id.rbTarde)
                    "Noche" -> rgMomentoDia.check(R.id.rbNoche)
                }

            }

            etFechaInicio.setText(fechaInicio)
            etFechaFin.setText(fechaFin)

            imagenSeleccionada = imagen
            Glide.with(this).load(imagen).into(ivSeleccionarImagen)

            ivSeleccionarImagen.setOnClickListener { mostrarSelectorImagenes() }

            etFechaInicio.setOnClickListener { mostrarDatePickerDialog(etFechaInicio) }
            etFechaFin.setOnClickListener { mostrarDatePickerDialog(etFechaFin) }

            btnEditar.setOnClickListener {
                if (id != null) {
                    EditarHabito(id)
                }
            }

        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun mostrarSelectorImagenes(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_seleccionar_imagen, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewImagenes)


        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = ImageAdapter(imagenesUrl) { url ->
            imagenSeleccionada = url
            Glide.with(this).load(url).into(ivSeleccionarImagen)

        }

        AlertDialog.Builder(this)
            .setTitle("Selecciona una imagen")
            .setView(dialogView)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val date = Calendar.getInstance()
                date.set(year, month, dayOfMonth)
                editText.setText(dateFormat.format(date.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


    private fun recuperarDeSharedPreferences(idHabito: Int): Pair<String,String>? {
        Log.e("MiApp", "Entra" );
        val momentoDia = sharedPreferences.getString("$idHabito.MOMENTO_DIA", null)
        val objetivo = sharedPreferences.getString("$idHabito.OBJETIVO", null)

        Log.e("MiApp", "Objetivo: " + objetivo );

        return if(momentoDia != null && objetivo != null){
            Pair(objetivo,momentoDia)
        } else {
            null
        }
    }

    private fun EditarHabito(id:Int){
        val nombre = etNombreHabito.text.toString()
        val descripcion = etDescripcion.text.toString()
        val objetivo = etObjetivo.text.toString()
        val fechaInicio = etFechaInicio.text.toString()
        val fechaFin = etFechaFin.text.toString()

        val momentoDia = when (rgMomentoDia.checkedRadioButtonId){
            R.id.rbManana -> "Mañana"
            R.id.rbTarde -> "Tarde"
            R.id.rbNoche -> "Noche"
            else -> "Mañana"
        }

        val nuevoHabito = Habitos(
            id = 0,
            nombre = nombre,
            descripcion = descripcion,
            progreso = 0,
            tiempoEnMinutos = 0,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            completado = false,
            imagenId = imagenSeleccionada
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetrofitClient.getInstance().create(ApiService::class.java)
                val habit = apiService.updateHabito(id, nuevoHabito)

                withContext(Dispatchers.Main) {
                    habit.id?.let { habitId ->
                        guardarEnSharedPreferences(habitId, momentoDia, objetivo)
                    }

                    Toast.makeText(this@EditarActivity, "Hábito actualizado exitosamente", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@EditarActivity, HabitosActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API_ERROR", "Error al actualizar hábito: ${e.message}")
                    Toast.makeText(this@EditarActivity, "Error al actualizar hábito", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun guardarEnSharedPreferences(idHabito: Int, momentoDia: String, objetivo: String) {
        val editor = sharedPreferences.edit()
        editor.putString("$idHabito.MOMENTO_DIA", momentoDia)
        editor.putString("$idHabito.OBJETIVO", objetivo)
        editor.apply()
    }


}