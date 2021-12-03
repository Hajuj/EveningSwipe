package com.example.eveningswipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var register: Button? = null
    private var login:TextView? = null
    private var editTextFullName: EditText? = null
    private var editTextAge:EditText? = null
    private var editTextEmail:EditText? = null
    private var editTextPassword:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register = findViewById<View>(R.id.registerUser) as Button
        (register as Button).setOnClickListener(View.OnClickListener { registerUser() })

        login = findViewById<View>(R.id.login) as TextView
        login!!.setOnClickListener(View.OnClickListener { startLoginActivity() })

        editTextFullName = findViewById<View>(R.id.fullName) as EditText
        editTextAge = findViewById<View>(R.id.age) as EditText
        editTextEmail = findViewById<View>(R.id.email) as EditText
        editTextPassword = findViewById<View>(R.id.password) as EditText
    }

    /**
     * method to handle registration of a user
     */
    private fun registerUser() {

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