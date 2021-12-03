package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var register: TextView? = null
    private var signIn: Button? = null
    private var email: EditText? = null
    private  var password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register = findViewById<View>(R.id.register) as TextView
        register!!.setOnClickListener(View.OnClickListener { startRegisterActivity() })
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.password) as EditText
        signIn = findViewById<View>(R.id.signIn) as Button
        signIn!!.setOnClickListener(View.OnClickListener { allowLogin() })
    }

    /**
     * method to handle login
     */
    private fun allowLogin() {
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