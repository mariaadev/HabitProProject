package com.example.habitproproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class EstatRegistre(
    var esValid: Boolean, // Indica si los datos son correcto
    var errorNomUsuari: String?, // Mensaje de error por el nombre de usuario
    var errorEmail: String?, // Mensaje de error por el email
    var errorContrasenya: String?, // Mensaje de error por contrasenya
    var errorFechaNacimiento: String? // Mensaje de error por fecha de nacimiento
)

class RegistrarseViewModel: ViewModel() {

    private var _nomUsuari:String=""
    private var _contrasenya:String=""

    private val _validaciodades = MutableLiveData<EstatRegistre>()
    val validaciodades: LiveData<EstatRegistre> = _validaciodades
}