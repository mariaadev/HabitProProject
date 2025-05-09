package com.example.habitproproject

import com.example.habitproproject.Activity.RegistrarseActivity
import org.junit.Rule
import org.junit.Test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not

@RunWith(AndroidJUnit4::class)
class RegistrarseUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegistrarseActivity::class.java)

    private fun escriureEmail(correu: String) {
        onView(withId(R.id.correo))
            .perform(clearText(), typeText(correu))
    }

    private fun escriureTelefon(telefon: String) {
        onView(withId(R.id.telefono))
            .perform(clearText(), typeText(telefon))
    }

    private fun escriureNomUsuari(nomUsuari: String) {
        onView(withId(R.id.nombreUsuario))
            .perform(clearText(), typeText(nomUsuari))
    }

    private fun escriureContrasenya(contrasenya: String) {
        onView(withId(R.id.contraseña1))
            .perform(clearText(), typeText(contrasenya))
    }

    private fun escriureContrasenyaConfirma(contrasenyaConfirm: String) {
        onView(withId(R.id.contraseña2))
            .perform(clearText(), typeText(contrasenyaConfirm))
    }

    private fun clicarRegistrar() {
        onView(withId(R.id.toHabitos))
            .perform(click())
    }

    private fun comprovaErrorText(errorEsperat: String) {
        onView(withText(errorEsperat)).check(matches(isDisplayed()))
    }

    private fun comprovaQueNoExisteixErrorText(error: String) {
        onView(withText(error)).check(matches(not(isDisplayed())))
    }

    /* Test del camp email*/

    @Test
    fun testEmailBuitMostraError() {
        escriureEmail("")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic és obligatori.")))
    }

    @Test
    fun testEmailNomésArrobaMostraError() {
        escriureEmail("@")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com.")))
    }

    @Test
    fun testEmailSenseArrobaAmbExtensioMostraError() {
        escriureEmail("usuari.com")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com.")))
    }

    @Test
    fun testEmailSenseArrobaNiDominiMostraError() {
        escriureEmail("usuari")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com.")))
    }

    @Test
    fun testEmailValidAmbCaractersEspecialsNoMostraError() {
        escriureEmail("usuari.lau@gmail.com")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(not(hasErrorText("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com."))))
    }

    @Test
    fun testEmailAmbEspaisMostraError() {
        escriureEmail("usu ari@gmail.com")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no pot contenir espais en blanc. Exemple: nom@gmail.com.")))
    }

    @Test
    fun testEmailAmbMajusculesNoMostraError() {
        escriureEmail("USUARI@GMAIL.COM")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com.")))

    }


    @Test
    fun testEmailMoltLlargMostraError() {
        val llarg = "usuari".repeat(17) + "@gmail.com"
        escriureEmail(llarg)
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no pot superar els 100 caràcters. Exemple: nom@gmail.com.")))
    }

    /* Test del camp del telèfon */

    @Test
    fun testTelefonBuitMostraError() {
        escriureTelefon("")
        clicarRegistrar()
        onView(withId(R.id.telefono))
            .check(matches(hasErrorText("El telèfon és obligatori.")))
    }

    @Test
    fun testTelefonMesDe9DigitsMostraError() {
        escriureTelefon("6543589409")
        clicarRegistrar()
        onView(withId(R.id.telefono))
            .check(matches(hasErrorText("El telèfon ha de tenir 9 dígits.")))
    }

    @Test
    fun testTelefonAmbLletresMostraError() {
        escriureTelefon("aaa678594")
        clicarRegistrar()
        onView(withId(R.id.telefono))
            .check(matches(hasErrorText("El telèfon ha de tenir 9 dígits i no pot contenir cap altre caràcter.")))
    }


    @Test
    fun testTelefonAmbSimbolsINouDigitsMostraError() {
        escriureTelefon("$67859$49")
        clicarRegistrar()
        onView(withId(R.id.telefono))
            .check(matches(hasErrorText("El telèfon ha de tenir 9 dígits i no pot contenir cap altre caràcter.")))
    }


    @Test
    fun testTelefonAmbEspaisMostraError() {
        escriureTelefon("678 59 489")
        clicarRegistrar()
        onView(withId(R.id.telefono))
            .check(matches(hasErrorText("El telèfon ha de tenir 9 dígits i no pot contenir cap altre caràcter ni espais.")))
    }

    // Test UI Nom Usuari
    @Test
    fun testNomUsuariBuitMostraError() {
        escriureNomUsuari("")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

    @Test
    fun testNomUsuariMassaCurtMostraError() {
        escriureNomUsuari("ab")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("El nom d'usuari ha de tenir un mínim de 3 caràcters")))
    }

    @Test
    fun testNomUsuariMassaLlargMostraError() {
        escriureNomUsuari("abcdefghijklmnop")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("El nom d'usuari ha de tenir un màxim de 10 caràcters")))
    }

    @Test
    fun testNomUsuariAmbParaulaOfensivaMostraError() {
        escriureNomUsuari("idiota")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar paraules ofensives")))
    }

    @Test
    fun testNomUsuariAmbEspaiAlIniciMostraError() {
        escriureNomUsuari(" usuari")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al inici")))
    }

    @Test
    fun testNomUsuariAmbEspaiAlFinalMostraError() {
        escriureNomUsuari("usuari ")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al final")))
    }

    @Test
    fun testNomUsuariAmbEspaiAlMigMostraError() {
        escriureNomUsuari("usu ari")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al mix")))
    }

    @Test
    fun testNomUsuariNomésNumerosMostraError() {
        escriureNomUsuari("123456")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar només números")))
    }

    @Test
    fun testNomUsuariMenysDeTresLletresMostraError() {
        escriureNomUsuari("1a2")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("Es necesita almenys tres lletres")))
    }

    @Test
    fun testNomUsuariAmbSimbolsEspecialsMostraError() {
        escriureNomUsuari("usu@ari!")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar símbols especials")))
    }

    @Test
    fun testNomUsuariAmbEmojisMostraError() {
        escriureNomUsuari("usuari\uD83D\uDE0A")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar emojis")))
    }

    @Test
    fun testNomUsuariAmbAccentsMostraError() {
        escriureNomUsuari("usúari")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar caràcters diacrítics")))
    }

    @Test
    fun testNomUsuariAmbParaulaPerillosaMostraError() {
        escriureNomUsuari("dropTable")
        clicarRegistrar()
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("No es permet utilitzar paraules reservades o perilloses")))
    }

    // Test Ui Contrasenya
    @Test
    fun testContrasenyaBuidaMostraError() {
        escriureContrasenya("")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya és obligatoria")))
    }

    @Test
    fun testContrasenyaMassaCurtaMostraError() {
        escriureContrasenya("Ab1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir un mínim de 8 caràcters")))
    }

    @Test
    fun testContrasenyaMassaLlargaMostraError() {
        escriureContrasenya("A".repeat(129) + "1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir un màxim de 128 caràcters")))
    }

    @Test
    fun testContrasenyaSenseMajusculaMostraError() {
        escriureContrasenya("contrasenya1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir almenys una lletra majúscula")))
    }

    @Test
    fun testContrasenyaSenseMinusculaMostraError() {
        escriureContrasenya("PASSWORD1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir almenys una lletra minúscula")))
    }

    @Test
    fun testContrasenyaSenseNumeroMostraError() {
        escriureContrasenya("Password$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir almenys un número")))
    }

    @Test
    fun testContrasenyaSenseCaracterEspecialMostraError() {
        escriureContrasenya("Password1")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("La contrasenya ha de tenir almenys un caràcter especial")))
    }

    @Test
    fun testContrasenyaAmbEspaiIniciMostraError() {
        escriureContrasenya(" Password1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al inici")))
    }

    @Test
    fun testContrasenyaAmbEspaiFinalMostraError() {
        escriureContrasenya("Password1$ ")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al final")))
    }

    @Test
    fun testContrasenyaAmbEspaiMigMostraError() {
        escriureContrasenya("Pass word1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar espais en blanc al mix")))
    }

    @Test
    fun testContrasenyaAmbEmojiMostraError() {
        escriureContrasenya("Password1$\uD83D\uDE00")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar emojis")))
    }

    @Test
    fun testContrasenyaAmbAccentsMostraError() {
        escriureContrasenya("Pássword1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar caràcters diacrítics")))
    }

    @Test
    fun testContrasenyaAmbParaulaPerillosaMostraError() {
        escriureContrasenya("select123$")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet utilitzar paraules reservades o perilloses")))
    }

    @Test
    fun testContrasenyaIgualANomUsuariMostraError() {
        escriureNomUsuari("Lamasloca_567")
        escriureContrasenya("Lamasloca_567")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet que la contrasenya sigui igual al nom d'usuari")))
    }

    @Test
    fun testContrasenyaIgualACorreuMostraError() {
        escriureEmail("usuari@email.com")
        escriureContrasenya("usuari@email.com")
        clicarRegistrar()
        onView(withId(R.id.contraseña1))
            .check(matches(hasErrorText("No es permet que la contrasenya sigui igual al correu")))
    }

    @Test
    fun testContrasenyaConfirmacioNoCoincideixMostraError() {
        escriureContrasenya("Password2$")
        escriureContrasenyaConfirma("Password1$")
        clicarRegistrar()
        onView(withId(R.id.contraseña2))
            .check(matches(hasErrorText("La contrasenya no coincideix")))
    }


}
