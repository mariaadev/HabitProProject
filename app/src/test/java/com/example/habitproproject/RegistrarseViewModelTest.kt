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
    fun `actualitzaCorreu retorna error quan el correu no conté arroba però sí extensió`() {
        viewModel.actualitzaCorreu("usuari.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu no conté ni arroba ni domini`() {
        viewModel.actualitzaCorreu("usuari")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha arroba i domini sense usuari`() {
        viewModel.actualitzaCorreu("@domini")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha usuari sense domini ni extensió`() {
        viewModel.actualitzaCorreu("pepe@")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha domini i extensió sense usuari`() {
        viewModel.actualitzaCorreu("@domini.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan només hi ha extensió sense nom ni arroba`() {
        viewModel.actualitzaCorreu(".com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha arroba i extensió però sense nom ni domini`() {
        viewModel.actualitzaCorreu("@.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha usuari i extensió però falta el domini`() {
        viewModel.actualitzaCorreu("usuari@.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna cap error quan el correu conté caràcters especials vàlids`() {
        viewModel.actualitzaCorreu("usuari.lau@gmail.com")
        viewModel.comprova_correu()
        assertEquals("", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan hi ha múltiples arrobes`() {
        viewModel.actualitzaCorreu("usuari@@domini.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no té el format correcte. Exemple: nom@gmail.com", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu comença amb espai`() {
        viewModel.actualitzaCorreu(" usuari@gmail.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu té espai al mig`() {
        viewModel.actualitzaCorreu("usua ri@gmail.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu acaba amb espai`() {
        viewModel.actualitzaCorreu("usuari@gmail.com ")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu no retorna error quan el correu està en majúscules`() {
        viewModel.actualitzaCorreu("USUARI@GMAIL.COM")
        viewModel.comprova_correu()
        assertEquals("", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu supera els 100 caràcters`() {
        viewModel.actualitzaCorreu("usuari".repeat(15) + "@gmail.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no pot superar els 100 caràcters. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    @Test
    fun `actualitzaCorreu retorna error quan el correu comença per un símbol`() {
        viewModel.actualitzaCorreu(".usuari@gmail.com")
        viewModel.comprova_correu()
        assertEquals("El correu electrònic no pot començar per un símbol. Exemple: nom@gmail.com.", viewModel.errorCorreu.value)
    }

    // Tests nom d'usuari
    @Test
    fun `actualitzaNomUsuari`(){
        viewModel.actualitzaNomUsuari("")
        viewModel.comprova_nomUsuariBuit()
        assertEquals("", viewModel.errorNomUsuari.value)
    }
}