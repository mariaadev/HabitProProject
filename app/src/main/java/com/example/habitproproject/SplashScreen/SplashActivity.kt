package com.example.habitproproject.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
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

        val slideInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_slide_in)

        logo.startAnimation(slideInAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}