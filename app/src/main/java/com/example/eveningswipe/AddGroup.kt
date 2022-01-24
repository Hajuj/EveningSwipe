package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.eveningswipe.httpRequests.HttpRequests

class AddGroup : AppCompatActivity() {

    private val BASE_URL_createGroup = "http://msp-WS2122-6.mobile.ifi.lmu.de:80/api/group/create/"
    private var saveGroup: Button? = null
    private var addGroupFinished: TextView? = null
    private var textDescription: TextView? = null
    private var name: EditText? = null
    private var newGroupName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        name = findViewById<View>(R.id.fullName) as EditText
        saveGroup = findViewById<View>(R.id.saveGroup) as Button
        saveGroup!!.setOnClickListener(View.OnClickListener { saveGroupToDatabase() })
        textDescription = findViewById<View>(R.id.textDescription) as TextView
        addGroupFinished = findViewById<View>(R.id.addGroupFinished) as TextView
        addGroupFinished!!.visibility = View.INVISIBLE;
    }

    /**
     * method to save a group in the database
     */
    private fun saveGroupToDatabase() {
        val token = HttpRequests.responseToken!!.token
        newGroupName = name!!.text.toString()
        println("Hallo !!!!! name " + newGroupName)
        HttpRequests.postCreatedGroup2(BASE_URL_createGroup, token, newGroupName!!, "")
        saveGroup!!.visibility = View.INVISIBLE;
        name!!.visibility = View.INVISIBLE;
        textDescription!!.visibility = View.INVISIBLE;
        addGroupFinished!!.visibility = View.VISIBLE;
    }
}
