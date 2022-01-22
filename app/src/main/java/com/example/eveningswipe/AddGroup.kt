package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.eveningswipe.httpRequests.HttpRequests

class AddGroup : AppCompatActivity() {

    private val BASE_URL_createGroup = "http://msp-WS2122-6.mobile.ifi.lmu.de:80/api/group/create/"
    private var saveGroup: Button? = null
    private var addUser: Button? = null
    private var name: EditText? = null
    private  var description: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        name = findViewById<View>(R.id.fullName) as EditText
        description = findViewById<View>(R.id.description) as EditText
        saveGroup = findViewById<View>(R.id.saveGroup) as Button
        saveGroup!!.setOnClickListener(View.OnClickListener { saveGroupToDatabase() })
        addUser = findViewById<View>(R.id.addUser) as Button
        addUser!!.setOnClickListener(View.OnClickListener { startAddUserActivity() })
    }

    /**
     * method to save a group in the database
     */
    private fun saveGroupToDatabase() {
        val token = HttpRequests.responseToken!!.token
        val name = name.toString()
        val description = description.toString()
        println("Hallo !!!!!")
        //HttpRequests.postCreatedGroup2(BASE_URL_createGroup, token, name, description)
    }

    /**
     * send user to AddUserActivity if users should be added to group
     */
    private fun startAddUserActivity() {
        val profileIntent = Intent(this@AddGroup, AddUserActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}
