package br.edu.infnet.meusgastos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.edu.infnet.meusgastos.main.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class _MainActivityTest {

    @JvmField
    @Rule
    val rule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)


    @Test
    fun rightPassword_should_startActivity(){
        onView(withId(R.id.btn_pular)).perform(click())

        onView(withId(R.id.input_email)).perform(
            typeText("teste@teste.com"),
            ViewActions.closeSoftKeyboard()
        )

        onView(withId(R.id.input_password)).perform(
            typeText("753951"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.btn_sign_in)).perform(click())
    }

    @Test
    fun inputRightRegisterData() {


        onView(withId(R.id.btn_pular)).perform(click())

        onView(withId(R.id.btn_sign_on)).perform(click())

        onView(withId(R.id.input_email2)).perform(typeText("teste@instumented2.com"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.input_password2)).perform(typeText("a11111"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.input_confirm_password2)).perform(typeText("a11111"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_cadastrar)).perform(click())
    }

}