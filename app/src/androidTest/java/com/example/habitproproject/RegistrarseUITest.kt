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
            .check(matches(hasErrorText("El correu electrònic és obligatori")))
    }

    @Test
    fun testEmailNomésArrobaMostraError() {
        escriureEmail("@")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com")))
    }

    @Test
    fun testEmailSenseArrobaAmbExtensioMostraError() {
        escriureEmail("usuari.com")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com")))
    }

    @Test
    fun testEmailSenseArrobaNiDominiMostraError() {
        escriureEmail("usuari")
        clicarRegistrar()
        onView(withId(R.id.correo))
            .check(matches(hasErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com")))
    }

    @Test
    fun testEmailValidAmbCaractersEspecialsNoMostraError() {
        escriureEmail("usuari.lau@gmail.com")
        clicarRegistrar()
        comprovaQueNoExisteixErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com")
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
        comprovaQueNoExisteixErrorText("El correu electrònic no té el format correcte. Exemple: nom@gmail.com")
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
}
