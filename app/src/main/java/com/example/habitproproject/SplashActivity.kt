package com.example.habitproproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.splash_logo)

        val slideInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_slide_in)

        logo.startAnimation(slideInAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}