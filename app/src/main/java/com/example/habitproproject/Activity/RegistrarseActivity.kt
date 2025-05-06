package com.example.habitproproject.Activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
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
            val intent = Intent(this, HabitosActivity::class.java)
            startActivity(intent)
        }

        textView.setOnClickListener {

            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        binding.nombreUsuario.editText?.addTextChangedListener {
            model.actualitzaNomUsuari(it.toString())
        }

        binding.contraseA1.editText?.addTextChangedListener {
            model.actualitzaContrasenya(it.toString())
        }

        binding.contraseA2.editText?.addTextChangedListener {
            model.actualitzaContrasenyaConfirma(it.toString())
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

    }

    private fun registrarUsuari(view: View?){
        model.registraUsuari()
    }



}