package com.example.habitproproject.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modernmt.text.profanity.ProfanityFilter
import com.vdurmont.emoji.EmojiParser
import java.text.Normalizer

class RegistrarseViewModel: ViewModel() {

    private var _nomUsuari:String=""
    private var _contrasenya:String=""
    private var _contrasenyaConfirma:String=""
    private var _correu:String=""
    private var _telefon:String=""

    private val _formularivalid = MutableLiveData<Boolean>(false)
    val formularivalid: LiveData<Boolean> = _formularivalid

    private val _errorNomUsuari = MutableLiveData<String>("")
    val errorNomUsuari: LiveData<String> = _errorNomUsuari

    private val _errorContrasenya = MutableLiveData<String>("")
    val errorContrasenya: LiveData<String> = _errorContrasenya

    private val _errorContrasenyaConfirma = MutableLiveData<String>("")
    val errorContrasenyaConfirma: LiveData<String> = _errorContrasenyaConfirma

    private val _errorCorreu = MutableLiveData<String>("")
    val errorCorreu: LiveData<String> = _errorCorreu

    private val _errorTelefon = MutableLiveData<String>("")
    val errorTelefon: LiveData<String> = _errorTelefon


    fun actualitzaNomUsuari(nomUsuari: String){
        _nomUsuari = nomUsuari
        comprova_nomUsuari()
    }

    fun actualitzaContrasenya(contrasenya: String){
        _contrasenya = contrasenya
        comprova_contrasenya()
    }

    fun actualitzaContrasenyaConfirma(contrasenyaConfirm: String){
        _contrasenyaConfirma = contrasenyaConfirm
        comprova_contrasenya()
    }

    fun actualitzaCorreu(correu: String){
        _correu = correu
        comprova_correu()
    }
    fun actualitzaTelefon(telefon: String){
        _telefon = telefon
        comprova_telefon()
    }

    fun comprovadadesusuari(){
        comprova_nomUsuari()
        comprova_contrasenya()
    }

    fun registraUsuari(){
        comprovadadesusuari()
        if(_formularivalid.value!!){

        }
    }


    // comprovacions contrasenya
    public fun comprova_contrasenya(){
        comprova_contrasenyaBuit()
        comprova_contrasenyaEspais()
        comprova_contrasenyaMassaCurt()
        comprova_contrasenyaMassaLlarg()
        comprova_contrasenyaMajuscula()
        comprova_contrasenyaMinuscula()
        comprova_contrasenyaNumero()
        comprova_contrasenyaAmbCaractersEspecials()
        comprova_contrasenyaEmojis()
        comprova_contrasenyaAccents()
        comprova_contrasenyaParaulesPerilloses()
        comprova_contrasenyaIgualNomUsuari()
        comprova_contrasenyaIgualCorreu()

        if (_errorContrasenya.value.isNullOrEmpty()) {
            comprova_contrasenyaCoincideix()
        }
    }


    public fun comprova_correu() {
        comprova_correuBuit()
        comprova_correuNomesArroba()
        comprova_correuSenseArrobaAmbExtensio()
        comprova_correuSenseArrobaSenseDomini()
        comprova_correuArrobaSenseNomNiExtensio()
        comprova_correuNomSenseDominiIExtensio()
        comprova_correuSenseUsuari()
        comprova_correuNomesExtensio()
        comprova_correuSenseNomAmbArrobaIExtensio()
        comprova_correuSenseDomini()
        comprova_correuAmbCaractersEspecialsValids()
        comprova_correuMultiplesArrobas()
        comprova_correuEspaisInici()
        comprova_correuEspaisMig()
        comprova_correuEspaisFinal()
        comprova_correuMajuscules()
        comprova_correuMesDe100Caracters()
        comprova_correuComencaPerSimbol()
    }


    public fun comprova_telefon(){

    }

    public fun comprova_correuBuit(){
        if(_correu.isEmpty()){
            _errorCorreu.value = "El correu electrònic és obligatori"
        }
    }

    public fun comprova_correuNomesArroba(){
        if(_correu.equals("@")){
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuSenseArrobaAmbExtensio() {
        if (_correu.contains(".") && !_correu.contains("@")) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuSenseArrobaSenseDomini() {
        if (!_correu.contains("@") && !_correu.contains(".")) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuArrobaSenseNomNiExtensio() {
        if (_correu.startsWith("@") && !_correu.contains(".")) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuNomSenseDominiIExtensio() {
        if (_correu.endsWith("@")) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuSenseUsuari() {
        if (_correu.startsWith("@") && _correu.contains(".")) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuNomesExtensio() {
        if (_correu.matches(Regex("^\\.[a-zA-Z]{2,}$"))) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }


    public fun comprova_correuSenseNomAmbArrobaIExtensio() {
        val regex = Regex("^[^@]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        if (_correu.matches(Regex("^(?:@|@[a-zA-Z]{2,})$")) || !_correu.matches(regex)) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }


    public fun comprova_correuSenseDomini() {
        if (_correu.matches(Regex("^[^@]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }


    public fun comprova_correuAmbCaractersEspecialsValids() {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (regex.matches(_correu)) {
            _errorCorreu.value = ""
        }
    }

    public fun comprova_correuMultiplesArrobas() {
        if (_correu.count { it == '@' } > 1) {
            _errorCorreu.value = "El correu electrònic no té el format correcte. Exemple: nom@gmail.com"
        }
    }

    public fun comprova_correuEspaisInici() {
        if (_correu.startsWith(" ")) {
            _errorCorreu.value = "El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com."
        }
    }

    public fun comprova_correuEspaisMig() {
        if (_correu.contains(" ")) {
            _errorCorreu.value = "El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com."
        }
    }

    public fun comprova_correuEspaisFinal() {
        if (_correu.endsWith(" ")) {
            _errorCorreu.value = "El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com."
        }
    }

    public fun comprova_correuMajuscules() {
        val regex = Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", RegexOption.IGNORE_CASE)
        if (regex.matches(_correu)) {
            _errorCorreu.value = ""
        }
    }

    public fun comprova_correuMesDe100Caracters() {
        if (_correu.length > 100) {
            _errorCorreu.value = "El correu electrònic no pot superar els 100 caràcters. Exemple: nom@gmail.com."
        }
    }

    public fun comprova_correuComencaPerSimbol() {
        if (_correu.isNotEmpty() && !Character.isLetterOrDigit(_correu[0])) {
            _errorCorreu.value = "El correu electrònic no pot començar per un símbol. Exemple: nom@gmail.com."
        }
    }


    public fun comprova_contrasenyaBuit(){
        if(_contrasenya.isEmpty()){
            _errorContrasenya.value = "La contrasenya és obligatoria"
        }
        if(_contrasenyaConfirma.isEmpty()){
            _errorContrasenyaConfirma.value = "La contrasenya és obligatoria"
        }
    }

    public fun comprova_contrasenyaCoincideix(){
        if(_contrasenya != _contrasenyaConfirma && _contrasenya.isNotEmpty() && _contrasenyaConfirma.isNotEmpty()){
            _errorContrasenya.value = "La contrasenya no coincideix"
            _errorContrasenyaConfirma.value = "La contrasenya no coincideix"
        }
    }

    public fun comprova_contrasenyaEspais(){
        if(_contrasenya.isNotEmpty() && _contrasenya.first().isWhitespace()){
            _errorContrasenya.value = "No es permet utilitzar espais en blanc al inici"

        } else if(_contrasenya.isNotEmpty() && _contrasenya.last().isWhitespace()){
            _errorContrasenya.value = "No es permet utilitzar espais en blanc al final"

        } else if(_contrasenya.trim().contains(" ")){
            _errorContrasenya.value = "No es permet utilitzar espais en blanc al mix"

        }

    }

    public fun comprova_contrasenyaMassaCurt(){
        if(_contrasenya.length< 8){
            _errorContrasenya.value = "La contrasenya ha de tenir un mínim de 8 caràcters"
        }
    }

    public fun comprova_contrasenyaMassaLlarg(){
        if(_contrasenya.length > 64){
            _errorContrasenya.value = "La contrasenya ha de tenir un màxim de 128 caràcters"
        }
    }

    public fun comprova_contrasenyaMajuscula(){
        if(_contrasenya.none() { it.isUpperCase()}){
            _errorContrasenya.value = "La contrasenya ha de tenir almenys una lletra majúscula"

        }
    }

    public fun comprova_contrasenyaMinuscula(){
        if(_contrasenya.none() { it.isLowerCase()}){
            _errorContrasenya.value = "La contrasenya ha de tenir almenys una lletra minúscula"

        }
    }

    public fun comprova_contrasenyaNumero(){
        if(_contrasenya.none() { it.isDigit()}){
            _errorContrasenya.value = "La contrasenya ha de tenir almenys un número"
        }
    }

    public fun comprova_contrasenyaAmbCaractersEspecials(){
        if (_contrasenya.none { !it.isLetterOrDigit() && !it.isWhitespace() }) {
            _errorContrasenya.value = "La contrasenya ha de tenir almenys un caràcter especial"
        }
    }

    public fun comprova_contrasenyaEmojis(){
        val emojis = EmojiParser.extractEmojis(_contrasenya)
        if(emojis.isNotEmpty()){
            _errorContrasenya.value = "No es permet utilitzar emojis"
        }
    }

    public fun comprova_contrasenyaAccents(){
        val textNormalitzat = Normalizer.normalize(_contrasenya, Normalizer.Form.NFD)

        if(textNormalitzat.any { it in '\u0300'..'\u036F'}){
            _errorContrasenya.value = "No es permet utilitzar caràcters diacrítics"

        }
    }

    public fun comprova_contrasenyaParaulesPerilloses(){
        val keywordsProhibides = listOf("select", "drop", "insert", "delete", "script", "eval", "<", ">", "--", "/*", "*/")

        if(keywordsProhibides.any { keyword -> _contrasenya.lowercase().contains(keyword)}){
            _errorContrasenya.value = "No es permet utilitzar paraules reservades o perilloses"

        }
    }

    public fun comprova_contrasenyaIgualNomUsuari(){
        if(_nomUsuari.equals(_contrasenya)){
            _errorContrasenya.value = "No es permet que la contrasenya sigui igual al nom d'usuari"
        }
    }

    public fun comprova_contrasenyaIgualCorreu(){
        if(_correu.equals(_contrasenya)){
            _errorContrasenya.value = "No es permet que la contrasenya sigui igual al correu"
        }
    }


    // comprovacions de nom d'usuari
    public fun comprova_nomUsuari(){
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

    public fun comprova_nomUsuariBuit(){
        if(_nomUsuari.isEmpty()){
            _errorNomUsuari.value = "El nom d'usuari és obligatori"
        }
    }

    public fun comprova_nomUsuariMassaCurt(){
        if(_nomUsuari.length < 3){
            _errorNomUsuari.value = "El nom d'usuari ha de tenir un mínim de 3 caràcters"
        }
    }

    public fun comprova_nomUsuariMassaLlarg(){
        if(_nomUsuari.length > 10){
            _errorNomUsuari.value = "El nom d'usuari ha de tenir un màxim de 10 caràcters"
        }
    }

    public fun comprova_nomUsuariParaulesOfensives(){
        val profanityFilter = ProfanityFilter()

        if(profanityFilter.test("es", _nomUsuari) || profanityFilter.test("en", _nomUsuari)|| profanityFilter.test("ca", _nomUsuari)){
            _errorNomUsuari.value = "No es permet utilitzar paraules ofensives"
        }
    }

    public fun comprova_nomUsuariEspais(){
        if(_nomUsuari.isNotEmpty() && _nomUsuari.first().isWhitespace()){
            _errorNomUsuari.value = "No es permet utilitzar espais en blanc al inici"

        } else if(_nomUsuari.isNotEmpty() && _nomUsuari.last().isWhitespace()){
            _errorNomUsuari.value = "No es permet utilitzar espais en blanc al final"

        } else if(_nomUsuari.trim().contains(" ")){
            _errorNomUsuari.value = "No es permet utilitzar espais en blanc al mix"

        }
    }

    public fun comprova_nomUsuariNumeros(){
        if(_nomUsuari.all { it.isDigit() }){
            _errorNomUsuari.value = "No es permet utilitzar només números"
        }
    }

    public fun comprova_nomUsuariMinimTresLletres(){
        if(_nomUsuari.count { it.isLetter() } < 3){
            _errorNomUsuari.value = "Es necesita almenys tres lletres"
        }
    }

    public fun comprova_nomUsuariSimbolsEspecials() {
        if(_nomUsuari.any{
            !it.isLetterOrDigit() &&
                    it != '_' &&
                    it != '.'

            }){
            _errorNomUsuari.value = "No es permet utilitzar símbols especials"
        }
    }

    public fun comprova_nomUsuariEmojis(){
        val emojis = EmojiParser.extractEmojis(_nomUsuari)
        if(emojis.isNotEmpty()){
            _errorNomUsuari.value = "No es permet utilitzar emojis"
        }
    }

    public fun comprova_nomUsuariAccents(){
        val textNormalitzat = Normalizer.normalize(_nomUsuari, Normalizer.Form.NFD)

        if(textNormalitzat.any { it in '\u0300'..'\u036F'}){
            _errorNomUsuari.value = "No es permet utilitzar caràcters diacrítics"

        }
    }

    public fun comprova_nomUsuariParaulesPerilloses(){
        val keywordsProhibides = listOf("select", "drop", "insert", "delete", "script", "eval", "<", ">", "--", "/*", "*/")

        if(keywordsProhibides.any { keyword -> _nomUsuari.lowercase().contains(keyword)}){
            _errorNomUsuari.value = "No es permet utilitzar paraules reservades o perilloses"

        }
    }




}