package com.example.habitproproject

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class IniciarSesionActivity : AppCompatActivity() {
    private lateinit var passwordEditText: com.google.android.material.textfield.TextInputEditText
    private lateinit var emailEditText: EditText
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)

        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        emailEditText = findViewById(R.id.etCorreo)

        emailEditText.addTextChangedListener(loginTextWatcher)
        passwordEditText.addTextChangedListener(loginTextWatcher)

        val forgotPasswordTextView: TextView = findViewById(R.id.tvForgotPassword)

        /*Subrayar texto de recuperar contraseña*/
        val text = "¿Has olvidado la contraseña?"
        val spannableStringPassword = SpannableString(text)
        spannableStringPassword.setSpan(UnderlineSpan(), 0, text.length, 0)

        forgotPasswordTextView.text = spannableStringPassword

        val signUpTextView: TextView = findViewById(R.id.tvSignUp)

        /*Subrayar texto de registrarse*/
        val fullText = "¿No tienes una cuenta? Regístrate"
        val spannableStringRegister = SpannableString(fullText)

        /*Subrayar solo Regístrate*/
        val startIndex = fullText.indexOf("Regístrate")
        val endIndex = startIndex + "Regístrate".length

        spannableStringRegister.setSpan(UnderlineSpan(), startIndex, endIndex, 0)
        /*Mostrar texto subrayado en el layout*/
        signUpTextView.text = spannableStringRegister

        /*Link a la actividad de registrarse*/
        signUpTextView.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            if (loginButton.isEnabled) {
                val intent = Intent(this, HabitosActivity::class.java)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val isEmailFilled = emailEditText.text.toString().trim().isNotEmpty()
            val isPasswordFilled = passwordEditText.text.toString().trim().isNotEmpty()

            /*Habilitar o deshabilitar el botón si el usuario ha rellenado los campos*/
            loginButton.isEnabled = isEmailFilled && isPasswordFilled

            /*Cambiar el color del botón para indicar que está habilitado o deshabilitado*/
            val color = if (loginButton.isEnabled) {
                getColor(R.color.button_enabled)
            } else {
                getColor(R.color.button_disabled)
            }
            loginButton.backgroundTintList = ColorStateList.valueOf(color)
        }

        override fun afterTextChanged(s: Editable?) {}
    }


}