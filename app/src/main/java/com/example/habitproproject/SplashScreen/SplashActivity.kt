package com.example.habitproproject.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.habitproproject.Activity.IniciarSesionActivity
import com.example.habitproproject.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.splash_logo)

        // Animación de entrada (slide-in o fade-in)
        val slideInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_slide_in)  // O usa logo_slide_in

        // Aparecerán primero el logo y el texto con la animación
        logo.startAnimation(slideInAnimation)

        // Después de que termine la animación de entrada, iniciamos la animación de "tiembla"
        logo.postDelayed({
            val shakeAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_shake)
            logo.startAnimation(shakeAnimation)
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}