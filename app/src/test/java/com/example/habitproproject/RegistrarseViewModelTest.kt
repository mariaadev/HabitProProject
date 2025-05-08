package com.example.habitproproject

import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.habitproproject.ViewModel.RegistrarseViewModel
import org.junit.Assert.*
import org.junit.Test

class RegistrarseViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = RegistrarseViewModel()

    @Test
    fun `actualitzaCorreu retorna error quan el correu és buit`() {
        viewModel.actualitzaCorreu("")
        viewModel.comprova_correuBuit()
        assertEquals("El correu electrònic és obligatori", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només conté arroba`() {
        viewModel.actualitzaCorreu("@")
        viewModel.comprova_correuNomesArroba()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu no conté arroba però sí extensió`() {
        viewModel.actualitzaCorreu("usuari.com")
        viewModel.comprova_correuSenseArrobaAmbExtensio()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu no conté ni arroba ni domini`() {
        viewModel.actualitzaCorreu("usuari")
        viewModel.comprova_correuSenseArrobaSenseDomini()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha arroba i domini sense usuari`() {
        viewModel.actualitzaCorreu("@domini")
        viewModel.comprova_correuArrobaSenseNomNiExtensio()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha usuari sense domini ni extensió`() {
        viewModel.actualitzaCorreu("pepe@")
        viewModel.comprova_correuNomSenseDominiIExtensio()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha domini i extensió sense usuari`() {
        viewModel.actualitzaCorreu("@domini.com")
        viewModel.comprova_correuSenseUsuari()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha extensió sense nom ni arroba`() {
        viewModel.actualitzaCorreu(".com")
        viewModel.comprova_correuNomesExtensio()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha arroba i extensió però sense nom ni domini`() {
        viewModel.actualitzaCorreu("@.com")
        viewModel.comprova_correuSenseNomAmbArrobaIExtensio()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha usuari i extensió però falta el domini`() {
        viewModel.actualitzaCorreu("usuari@.com")
        viewModel.comprova_correuSenseDomini()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna cap error quan el correu conté caràcters especials vàlids`() {
        viewModel.actualitzaCorreu("usuari.lau@gmail.com")
        viewModel.comprova_correuAmbCaractersEspecialsValids()
        assertEquals("", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha múltiples arrobes`() {
        viewModel.actualitzaCorreu("usuari@@domini.com")
        viewModel.comprova_correuMultiplesArrobas()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu comença amb espai`() {
        viewModel.actualitzaCorreu(" usuari@gmail.com")
        viewModel.comprova_correuEspaisInici()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu té espai al mig`() {
        viewModel.actualitzaCorreu("usua ri@gmail.com")
        viewModel.comprova_correuEspaisMig()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu acaba amb espai`() {
        viewModel.actualitzaCorreu("usuari@gmail.com ")
        viewModel.comprova_correuEspaisFinal()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu no retorna error quan el correu està en majúscules`() {
        viewModel.actualitzaCorreu("USUARI@GMAIL.COM")
        viewModel.comprova_correuMajuscules()
        assertEquals("", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu supera els 100 caràcters`() {
        viewModel.actualitzaCorreu("usuari".repeat(17) + "@gmail.com")
        viewModel.comprova_correuMesDe100Caracters()
        assertEquals("El correu electrònic no pot superar els 100 caràcters. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu comença per un símbol`() {
        viewModel.actualitzaCorreu(".usuari@gmail.com")
        viewModel.comprova_correuComencaPerSimbol()
        assertEquals("El correu electrònic no pot començar per un símbol. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    // Tests nom d'usuari
    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari està buit`(){
        viewModel.actualitzaNomUsuari("")
        viewModel.comprova_nomUsuariBuit()
        assertEquals("El nom d'usuari és obligatori", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari és massa curt`(){
        viewModel.actualitzaNomUsuari("ab")
        viewModel.comprova_nomUsuariMassaCurt()
        assertEquals("El nom d'usuari ha de tenir un mínim de 3 caràcters", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari és massa llarg`(){
        viewModel.actualitzaNomUsuari("abcdefghijklmnop")
        viewModel.comprova_nomUsuariMassaLlarg()
        assertEquals("El nom d'usuari ha de tenir un màxim de 10 caràcters", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té una paraula ofensiva`(){
        viewModel.actualitzaNomUsuari("idiota")
        viewModel.comprova_nomUsuariParaulesOfensives()
        assertEquals("No es permet utilitzar paraules ofensives", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té espai blanc al principi`(){
        viewModel.actualitzaNomUsuari(" usuari")
        viewModel.comprova_nomUsuariEspais()
        assertEquals("No es permet utilitzar espais en blanc al inici", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té espai blanc al final`(){
        viewModel.actualitzaNomUsuari("usuari ")
        viewModel.comprova_nomUsuariEspais()
        assertEquals("No es permet utilitzar espais en blanc al final", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té espai blanc al mig`(){
        viewModel.actualitzaNomUsuari("usu ari")
        viewModel.comprova_nomUsuariEspais()
        assertEquals("No es permet utilitzar espais en blanc al mix", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari és només números`(){
        viewModel.actualitzaNomUsuari("123456")
        viewModel.comprova_nomUsuariNumeros()
        assertEquals("No es permet utilitzar només números", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té menys de 3 lletres`(){
        viewModel.actualitzaNomUsuari("1a2")
        viewModel.comprova_nomUsuariMinimTresLletres()
        assertEquals("Es necesita almenys tres lletres", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té símbols especials`(){
        viewModel.actualitzaNomUsuari("usu@ari!")
        viewModel.comprova_nomUsuariSimbolsEspecials()
        assertEquals("No es permet utilitzar símbols especials", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té emojis`(){
        viewModel.actualitzaNomUsuari("usuari\uD83D\uDE0A")
        viewModel.comprova_nomUsuariEmojis()
        assertEquals("No es permet utilitzar emojis", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té accents`(){
        viewModel.actualitzaNomUsuari("usúari")
        viewModel.comprova_nomUsuariAccents()
        assertEquals("No es permet utilitzar caràcters diacrítics", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `actualitzaNomUsuari retorna error quan el nom d'usuari té paraules reservades o perilloses`(){
        viewModel.actualitzaNomUsuari("dropTable")
        viewModel.comprova_nomUsuariParaulesPerilloses()
        assertEquals("No es permet utilitzar paraules reservades o perilloses", viewModel.errorNomUsuari.value)
    }


}