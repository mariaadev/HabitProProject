package com.example.habitproproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modernmt.text.profanity.ProfanityFilter
import com.vdurmont.emoji.EmojiParser
import java.text.Normalizer

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

    // comprovacions de nom d'usuari
    fun actualitzaNomUsuari(nomUsuari: String){
        _nomUsuari = nomUsuari
        comprova_nomUsuari()
    }

    private fun comprova_nomUsuari(){
        comprova_nomUsuariBuit()
        comprova_nomUsuariMassaCurt()
        comprova_nomUsuariMassaLlarg()
        comprova_nomUsuariParaulesOfensives()
        comprova_nomUsuariEspais()
        comprova_nomUsuariNumeros()
        comprova_nomUsuariMinimTresLletres()
        comprova_nomUsuariSimbolsEspecials()
        comprova_nomUsuariEmojis()
        comprova_nomUsuariAccents()
        comprova_nomUsuariParaulesPerilloses()

    }

    private fun comprova_nomUsuariBuit(){
        if(_nomUsuari.isEmpty()){
            _validaciodades.value?.errorNomUsuari = "El nom d'usuari és obligatori"
        }
    }

    private fun comprova_nomUsuariMassaCurt(){
        if(_nomUsuari.length < 3){
            _validaciodades.value?.errorNomUsuari = "El nom d'usuari ha de tenir un mínim de 3 caràcters"
        }
    }

    private fun comprova_nomUsuariMassaLlarg(){
        if(_nomUsuari.length > 10){
            _validaciodades.value?.errorNomUsuari = "El nom d'usuari ha de tenir un màxim de 10 caràcters"
        }
    }

    private fun comprova_nomUsuariParaulesOfensives(){
        val profanityFilter = ProfanityFilter()

        if(profanityFilter.test("es", _nomUsuari) || profanityFilter.test("en", _nomUsuari)|| profanityFilter.test("ca", _nomUsuari)){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar paraules ofensives"
        }
    }

    private fun comprova_nomUsuariEspais(){
        if(_nomUsuari.isNotEmpty() && _nomUsuari.first().isWhitespace()){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar espais en blanc al inici"

        } else if(_nomUsuari.isNotEmpty() && _nomUsuari.last().isWhitespace()){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar espais en blanc al final"

        } else if(_nomUsuari.trim().contains(" ")){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar espais en blanc al mix"

        }
    }

    private fun comprova_nomUsuariNumeros(){
        if(_nomUsuari.all { it.isDigit() }){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar només números"
        }
    }

    private fun comprova_nomUsuariMinimTresLletres(){
        if(_nomUsuari.count { it.isLetter() } < 3){
            _validaciodades.value?.errorNomUsuari = "Es necesita almenys tres lletres"
        }
    }

    private fun comprova_nomUsuariSimbolsEspecials() {
        if(_nomUsuari.any{
            !it.isLetterOrDigit() &&
                    it != '_' &&
                    it != '.'

            }){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar símbols especials"
        }
    }

    private fun comprova_nomUsuariEmojis(){
        val emojis = EmojiParser.extractEmojis(_nomUsuari)
        if(emojis.isNotEmpty()){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar emojis"

        }
    }

    private fun comprova_nomUsuariAccents(){
        val textNormalitzat = Normalizer.normalize(_nomUsuari, Normalizer.Form.NFD)

        if(textNormalitzat.any { it in '\u0300'..'\u036F'}){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar caràcters diacrítics"

        }
    }

    private fun comprova_nomUsuariParaulesPerilloses(){
        val keywordsProhibides = listOf("select", "drop", "insert", "delete", "script", "eval", "<", ">", "--", "/*", "*/")

        if(keywordsProhibides.any { keyword -> _nomUsuari.lowercase().contains(keyword)}){
            _validaciodades.value?.errorNomUsuari = "No es permet utilitzar paraules reservades o perilloses"

        }
    }




}