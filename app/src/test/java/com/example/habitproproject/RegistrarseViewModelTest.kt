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