package com.example.eveningswipe

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.httpRequests.TokenDto
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val BASE_URL_Login = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/login"
    private val BASE_URL_User = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/user"
    private var register: TextView? = null
    private var signIn: Button? = null
    private var email: TextInputLayout? = null
    private  var password: TextInputLayout? = null
    lateinit var token: TokenDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_EveningSwipe)
        setContentView(R.layout.activity_main)

        //for dark mode
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_DarkMode)
        } else {
            setTheme(R.style.Theme_EveningSwipe)
        }

        register = findViewById<View>(R.id.register) as Button
        register!!.setOnClickListener(View.OnClickListener { startRegisterActivity() })
        email = findViewById<View>(R.id.email) as TextInputLayout
        password = findViewById<View>(R.id.password) as TextInputLayout
        signIn = findViewById<View>(R.id.signIn) as Button
        signIn!!.setOnClickListener(View.OnClickListener { allowLogin() })


        // Set cut corner background for API 23+
        val layout = findViewById(R.id.header_layout) as View
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)

    }
    /**
     * method to handle login
     */
    private fun allowLogin() {
        val email = email?.editText?.text.toString().trim()
        val password = password?.editText?.text.toString().trim()

        val response = HttpRequests.postLoginUser(BASE_URL_Login, email, password)

        if(!response!!){
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT)
                .show()
        }else{
            login()
        }
    }

    /**
     * final login after token is initialized
     */
    private fun login() {
        val token = HttpRequests.responseToken
        var response: Boolean? =null
        if (token != null) {
            response = HttpRequests.getUserInformation(BASE_URL_User, token)
        }
        //if user can login:
        if(!response!!){
            //login not possible
        }else{
            startHomeActivity()
        }

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