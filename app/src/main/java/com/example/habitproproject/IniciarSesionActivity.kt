package com.example.habitproproject

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
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