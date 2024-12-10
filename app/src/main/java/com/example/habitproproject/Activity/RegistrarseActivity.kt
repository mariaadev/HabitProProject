package com.example.habitproproject.Activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.R

class RegistrarseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)


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



    }
}