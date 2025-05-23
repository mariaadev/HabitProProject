package com.example.habitproproject.Activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.R
import com.example.habitproproject.ViewModel.RegistrarseViewModel
import com.example.habitproproject.databinding.ActivityRegistrarseBinding

class RegistrarseActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrarseBinding
    private val model:RegistrarseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val textView: TextView = findViewById(R.id.toInicio)
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val button: Button = findViewById(R.id.toHabitos)

        button.setOnClickListener {
            if (validarCamps()) {
                val intent = Intent(this, HabitosActivity::class.java)
                startActivity(intent)
            } else {

                Toast.makeText(this, "Por favor, corrige los errores", Toast.LENGTH_SHORT).show()
            }
        }

        textView.setOnClickListener {

            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        binding.nombreUsuario.addTextChangedListener {
            model.actualitzaNomUsuari(it.toString())
        }

        binding.contraseA1.addTextChangedListener {
            model.actualitzaContrasenya(it.toString())
        }

        binding.contraseA2.addTextChangedListener {
            model.actualitzaContrasenyaConfirma(it.toString())
        }

        binding.correo.addTextChangedListener {
            model.actualitzaCorreu(it.toString())
        }

        binding.telefono.addTextChangedListener {
            model.actualitzaTelefon(it.toString())
        }


        model.errorNomUsuari.observe(this) { errorNomUsuari ->
            if(errorNomUsuari.isNullOrBlank()){
                binding.nombreUsuario.error= null
            } else {
                binding.nombreUsuario.setError(errorNomUsuari)
            }
        }

        model.errorContrasenya.observe(this) { errorContrasenya ->
            if(errorContrasenya.isNullOrBlank()){
                binding.contraseA1.error= null
            } else {
                binding.contraseA1.setError(errorContrasenya)
            }
        }

        model.errorContrasenyaConfirma.observe(this) { errorContrasenyaConfirma ->
            if(errorContrasenyaConfirma.isNullOrBlank()){
                binding.contraseA2.error= null
            } else {
                binding.contraseA2.setError(errorContrasenyaConfirma)
            }
        }

        model.errorCorreu.observe(this) { errorCorreu ->
            if(errorCorreu.isNullOrBlank()){
                binding.correo.error= null
            } else {
                binding.correo.setError(errorCorreu)
            }
        }

        model.errorTelefon.observe(this) { errorTelefon ->
            if(errorTelefon.isNullOrBlank()){
                binding.telefono.error= null
            } else {
                binding.telefono.setError(errorTelefon)
            }
        }

    }

    private fun registrarUsuari(view: View?){
        model.registraUsuari()
    }


    private fun validarCamps(): Boolean {
        var isValid = true

        if (model.errorNomUsuari.value != null) {
            isValid = false
        }
        if (model.errorCorreu.value != null) {
            isValid = false
        }
        if (model.errorTelefon.value != null) {
            isValid = false
        }

        return isValid
    }
}