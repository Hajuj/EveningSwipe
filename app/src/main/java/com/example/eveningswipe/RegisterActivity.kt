package com.example.eveningswipe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eveningswipe.httpRequests.HttpRequests
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private val BASE_URL_Register = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/register"
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
        if(name.isNullOrBlank()){
            Toast.makeText(this, getString(R.string.noName), Toast.LENGTH_SHORT).show()
        } else if (email.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.noEmail), Toast.LENGTH_SHORT).show()
        } else if (password.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.noPassword), Toast.LENGTH_SHORT).show()
        } else {
            HttpRequests.postRegisterUser(url, name, email, password)
            startLoginActivity()
            Toast.makeText(this, getString(R.string.pleaseLogin), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * send user to MainActivity if user already has an account
     */
    private fun startLoginActivity() {
        val profileIntent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}