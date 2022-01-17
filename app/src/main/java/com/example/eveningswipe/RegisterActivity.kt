package com.example.eveningswipe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eveningswipe.httpRequests.HttpRequests
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private val BASE_URL_Register = "http://192.168.178.30:8080/register"
    private var register: Button? = null
    private var login:TextView? = null
    private var editTextFullName: TextInputLayout? = null
    private var editTextEmail:TextInputLayout? = null
    private var editTextPassword:TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register = findViewById<View>(R.id.registerUser) as Button
        (register as Button).setOnClickListener(View.OnClickListener { registerUser() })

        login = findViewById<View>(R.id.login) as Button
        (login as Button).setOnClickListener(View.OnClickListener { startLoginActivity() })

        editTextFullName = findViewById<View>(R.id.fullName) as TextInputLayout
        editTextEmail = findViewById<View>(R.id.email) as TextInputLayout
        editTextPassword = findViewById<View>(R.id.password) as TextInputLayout
        // Set cut corner background for API 23+
        var layout = findViewById(R.id.header_layout) as View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        }
    }

    /**
     * method to handle registration of a user
     */
    private fun registerUser() {
            val url = BASE_URL_Register
            val name = editTextFullName?.getEditText()?.getText().toString().trim()
            val email = editTextEmail?.getEditText()?.getText().toString().trim()
            val password = editTextPassword?.getEditText()?.getText().toString().trim()
            println("Hallo Register !!!!!")
            HttpRequests.postRegisterUser(url, name, email, password)
            startHomeActivity()
        //TODO: check if registration was successful
    }

    /**
     * send user to MainActivity if user already has an account
     */
    private fun startLoginActivity() {
        val profileIntent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(profileIntent)
        finish()
    }

    /**
     * send user to HomeActivity after successful registration
     */
    private fun startHomeActivity() {
        val profileIntent = Intent(this@RegisterActivity, NavigationDrawer::class.java)
        startActivity(profileIntent)
        finish()
    }
}