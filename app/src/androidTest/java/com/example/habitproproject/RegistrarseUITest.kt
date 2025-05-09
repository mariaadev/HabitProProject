package com.example.habitproproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habitproproject.Activity.RegistrarseActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrarseUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(RegistrarseActivity::class.java)

    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.nombreUsuario)).perform(clearText())
        onView(withId(R.id.toHabitos)).perform(click())
        onView(withId(R.id.nombreUsuario))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }


}