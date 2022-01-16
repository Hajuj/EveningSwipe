package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.eveningswipe.httpRequests.HttpRequests
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private val BASE_URL_Login = "http://192.168.178.30:8080/login"
    private var register: TextView? = null
    private var signIn: Button? = null
    private var email: TextInputLayout? = null
    private  var password: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register = findViewById<View>(R.id.register) as Button
        register!!.setOnClickListener(View.OnClickListener { startRegisterActivity() })
        email = findViewById<View>(R.id.email) as TextInputLayout
        password = findViewById<View>(R.id.password) as TextInputLayout
        signIn = findViewById<View>(R.id.signIn) as Button
        signIn!!.setOnClickListener(View.OnClickListener { allowLogin() })
    }

    /**
     * method to handle login
     */
    private fun allowLogin() {
        val url = BASE_URL_Login
        val email = email?.getEditText()?.getText().toString().trim()
        val password = password?.getEditText()?.getText().toString().trim()
        println("Hallo Login !!!!!")
        HttpRequests.postLoginUser(url, email, password)
        //TODO: check if login was successful
        //if user is allowed to login:
        startHomeActivity()
    }

    /**
     * send user to HomeActivity after successful login
     */
    private fun startHomeActivity() {
        val profileIntent = Intent(this@MainActivity, NavigationDrawer::class.java)
        startActivity(profileIntent)
        finish()
    }

    /**
     * send user to RegisterActivity if user has no account
     */
    private fun startRegisterActivity() {
        val profileIntent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}