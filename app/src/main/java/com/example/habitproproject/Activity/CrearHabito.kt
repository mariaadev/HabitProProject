package com.example.habitproproject.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.habitproproject.API.ApiService
import com.example.habitproproject.API.RetrofitClient
import com.example.habitproproject.Adapter.ImageAdapter
import com.example.habitproproject.Model.Habitos
import com.example.habitproproject.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearHabito : AppCompatActivity() {

    private lateinit var etNombreHabito: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etObjetivo: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var rgMomentoDia: RadioGroup
    private lateinit var btnGuardar: Button
    private lateinit var ivSeleccionarImagen: ImageView

    private var imagenSeleccionada: String = ""

    private val imagenesUrl = listOf(
        "http://13.216.192.241/images/imagen1.png",
        "http://13.216.192.241/images/imagen2.png",
        "http://13.216.192.241/images/imagen3.png",
        "http://13.216.192.241/images/imagen4.png",
        "http://13.216.192.241/images/imagen5.png",
        "http://13.216.192.241/images/imagen6.png",
        "http://13.216.192.241/images/imagen8.png"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_habito)

        etNombreHabito = findViewById(R.id.etNombreHabito)
        etDescripcion = findViewById(R.id.etDescripcion)
        etObjetivo = findViewById(R.id.etObjetivo)
        etFechaInicio = findViewById(R.id.etFechaInicio)
        etFechaFin = findViewById(R.id.etFechaFin)
        rgMomentoDia = findViewById(R.id.rgMomentoDia)
        ivSeleccionarImagen = findViewById(R.id.ivSeleccionarImagen)
        btnGuardar = findViewById(R.id.btnGuardar)

        ivSeleccionarImagen.setOnClickListener { mostrarSelectorImagenes() }

        btnGuardar.setOnClickListener { guardarHabito() }
    }

    private fun mostrarSelectorImagenes() {
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

    private fun guardarHabito() {
        val nombre = etNombreHabito.text.toString()
        val descripcion = etDescripcion.text.toString()
        val objetivo = etObjetivo.text.toString()
        val fechaInicio = etFechaInicio.text.toString()
        val fechaFin = etFechaFin.text.toString()

        val momentoDia = when (rgMomentoDia.checkedRadioButtonId) {
            R.id.rbManana -> "Mañana"
            R.id.rbTarde -> "Tarde"
            R.id.rbNoche -> "Noche"
            else -> "Cualquiera"
        }
        val nuevoHabito = Habitos(
            nombre = nombre,
            descripcion = descripcion,
            progreso = 0,
            tiempoEnMinutos = 30,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            completado = false,
            imagenId = 0
        )

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.createHabitos(nuevoHabito).enqueue(object : Callback<Habitos> {
            override fun onResponse(call: Call<Habitos>, response: Response<Habitos>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CrearHabito, "Hábito creado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CrearHabito, "Error al crear hábito", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Habitos>, t: Throwable) {
                Toast.makeText(this@CrearHabito, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

