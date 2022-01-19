package com.example.eveningswipe

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.eveningswipe.httpRequests.GetUserInfo
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.httpRequests.TokenDto
import com.example.eveningswipe.httpRequests.UserInfoDto
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private val BASE_URL_Login = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/login"
    private val BASE_URL_User = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/user"
    private var register: TextView? = null
    private var signIn: Button? = null
    private var email: TextInputLayout? = null
    private  var password: TextInputLayout? = null
    lateinit  private var userInfo: UserInfoDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register = findViewById<View>(R.id.register) as Button
        register!!.setOnClickListener(View.OnClickListener { startRegisterActivity() })
        email = findViewById<View>(R.id.email) as TextInputLayout
        password = findViewById<View>(R.id.password) as TextInputLayout
        signIn = findViewById<View>(R.id.signIn) as Button
        signIn!!.setOnClickListener(View.OnClickListener { allowLogin() })

        // Set cut corner background for API 23+
        var layout = findViewById(R.id.header_layout) as View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        }
    }

    /**
     * method to handle login
     */
    private fun allowLogin() {
        val token = TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI0MjIiLCJpYXQiOjE2NDI1MDAzMDIsImV4cCI6MTY0MjU4NjcwMn0.9Coew80TwhOZ_9_q3jcb1of_WIxdO0BR-N8RgPkpAog")
        val email = email?.getEditText()?.getText().toString().trim()
        val password = password?.getEditText()?.getText().toString().trim()
        println("Hallo Login !!!!!")
        HttpRequests.postLoginUser(BASE_URL_Login, email, password)
        //userInfo = HttpRequests.getUserInformation(url, token)

        HttpRequests.getUserInformation(BASE_URL_User, token)
        //TODO: check if login was successful
        //if user is allowed to login:
        startHomeActivity()
        //println("userinfo: " + userInfo)
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